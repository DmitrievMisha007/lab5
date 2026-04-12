package core;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

abstract public class App {
    static private int port;
    static private Manager manager;
    static private boolean isRun;
    static private CommandFactory commandFactory;
    static private Invoker invoker;

    static public void init(int port, Manager manager, CommandFactory commandFactory, Invoker invoker) {
        App.port = port;
        App.manager = manager;
        App.isRun = true;
        App.commandFactory = commandFactory;
        App.invoker = invoker;
    }

    static public void start() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (isRun){
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()){
                SelectionKey key = keys.next();
                keys.remove();
                if (!key.isValid()) continue;

                if (key.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel clientChannel = server.accept();
                    clientChannel.configureBlocking(false);
                    SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
                    clientKey.attach(new ClientState());
                    System.out.println("New client connected: " + clientChannel.getRemoteAddress());
                }
                else if (key.isReadable()){
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    readData(clientChannel, selector);
                }
                else if (key.isWritable()) {
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    writeData(clientChannel, selector);
                }
            }
        }
        selector.close();
        serverSocketChannel.close();
    }

    static public void stop() {
        isRun = false;
    }

    /**
     * Чтение данных из клиентского канала в неблокирующем режиме.
     * Используется протокол с префиксом длины: сначала 4 байта (int) – длина сообщения,
     * затем сами байты сериализованного объекта.
     * @param channel канал клиента
     * @param selector селектор (нужен для изменения интересов ключа)
     */
    static private void readData(SocketChannel channel, Selector selector) throws IOException {
        SelectionKey key = channel.keyFor(selector);
        if (key == null) return;
        ClientState state = (ClientState) key.attachment();
        if (state == null) return;
        // Получаем буфер для чтения длины (4 байта)
        ByteBuffer lengthBuffer = state.getLengthBuffer();

        // Если мы ещё не начали читать сообщение (ожидаем длину)
        if (state.getExpectedLength() == -1) {
            // Пытаемся прочитать данные в буфер длины
            int read = channel.read(lengthBuffer);
            // Если read == -1, клиент закрыл соединение
            if (read == -1) {
                closeConnection(key); // метод, который отменяет ключ и закрывает канал
                return;
            }
            // Если буфер длины заполнен (прочитаны все 4 байта)
            if (lengthBuffer.remaining() == 0) {
                lengthBuffer.flip();          // Переключаем буфер в режим чтения
                state.setExpectedLength(lengthBuffer.getInt()); // Извлекаем длину сообщения
                lengthBuffer.clear();          // Очищаем буфер для следующего сообщения
                // Выделяем новый буфер для данных сообщения
                state.setDataBuffer(ByteBuffer.allocate(state.getExpectedLength()));
            } else {
                // Не хватило данных для полной длины – выходим, ждём следующих READ-событий
                return;
            }
        }

        // Теперь читаем сами данные сообщения
        ByteBuffer dataBuffer = state.getDataBuffer();
        int read = channel.read(dataBuffer);
        if (read == -1) throw new IOException("Client disconnected");

        // Если буфер данных полностью заполнен
        if (dataBuffer.remaining() == 0) {
            dataBuffer.flip();               // Переключаем в режим чтения
            // Извлекаем массив байтов из буфера
            byte[] data = new byte[dataBuffer.limit()];
            dataBuffer.get(data);
            // Обрабатываем полученную команду (десериализация + выполнение)
            processCommand(channel, data, selector);
            // Сбрасываем состояние для следующего сообщения
            state.setExpectedLength(-1);
            state.setDataBuffer(null);
        }
    }

    /**
     * Обрабатывает полученное сообщение: десериализует CommandRequest,
     * выполняет команду, сериализует ответ и ставит в очередь на отправку.
     * Использует встроенную Java-сериализацию (ObjectInputStream/ObjectOutputStream).
     *
     * @param channel клиентский канал
     * @param data массив байтов, содержащий сериализованный объект CommandRequest
     * @param selector селектор для управления интересом к записи
     */
    static private void processCommand(SocketChannel channel, byte[] data, Selector selector) {
        try {
            // ----- 1. Десериализация запроса из массива байтов -----
            // Оборачиваем массив байтов в поток ввода
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            // Создаём ObjectInputStream для чтения объектов Java
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                // Читаем объект, который должен быть типа CommandRequest
                CommandRequest request = (CommandRequest) ois.readObject();

                // ----- 2. Выполнение команды -----
                CommandResponse response = commandFactory.executeCommandByRequest(request, manager, invoker);

                // ----- 3. Отправка ответа клиенту -----
                sendResponse(channel, response, selector);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Класс CommandRequest не найден (несовместимые версии)
            CommandResponse errorResponse = new CommandResponse("Invalid command format");
            sendResponse(channel, errorResponse, selector);
        } catch (IOException e) {
            e.printStackTrace();
            // Ошибка ввода-вывода при десериализации
            CommandResponse errorResponse = new CommandResponse("IO error during deserialization: " + e.getMessage());
            sendResponse(channel, errorResponse, selector);
        } catch (Exception e) {
            e.printStackTrace();
            // Любая другая ошибка (например, в execute)
            CommandResponse errorResponse = new CommandResponse("Server error: " + e.getMessage());
            sendResponse(channel, errorResponse, selector);
        }
    }

    /**
     * Вспомогательный метод для сериализации и отправки ответа.
     * Сериализует CommandResponse в массив байтов с помощью Java-сериализации,
     * затем отправляет длину и данные клиенту.
     *
     * @param channel клиентский канал
     * @param response объект ответа
     * @param selector селектор
     */
    static private void sendResponse(SocketChannel channel, CommandResponse response, Selector selector) {
        try {
            // ----- Сериализация ответа в массив байтов -----
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(response);
            }
            byte[] responseData = baos.toByteArray();

            // ----- Подготовка буферов для отправки (длина + данные) -----
            ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
            lengthBuffer.putInt(responseData.length);
            lengthBuffer.flip();
            ByteBuffer dataBuffer = ByteBuffer.wrap(responseData);

            // ----- Помещаем буферы в очередь отправки клиента -----
            // Получаем состояние клиента, прикреплённое к ключу
            SelectionKey key = channel.keyFor(selector);
            if (key == null) {
                // Канал больше не зарегистрирован в селекторе (закрыт)
                return;
            }
            ClientState state = (ClientState) key.attachment();
            if (state != null) {
                state.enqueueWrite(lengthBuffer);
                state.enqueueWrite(dataBuffer);

                // ----- Добавляем интерес к операции записи, если его ещё нет -----
                int ops = key.interestOps();
                if ((ops & SelectionKey.OP_WRITE) == 0) {
                    key.interestOps(ops | SelectionKey.OP_WRITE);
                }
                // Пробуждаем селектор, чтобы он сразу обработал запись
                selector.wakeup();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Если не удалось сериализовать ответ, закрываем соединение
            try {
                channel.close();
            } catch (IOException ex) {
                // ignore
            }
        }
    }

    /**
     * Отправка данных клиенту. Данные берутся из очереди pendingWrites.
     * Если отправить всё не удалось, оставляем остаток в очереди и сохраняем интерес к WRITE.
     * @param channel канал клиента
     * @param selector селектор
     */
    static private void writeData(SocketChannel channel, Selector selector) throws IOException {
        SelectionKey key = channel.keyFor(selector);
        if (key == null) return;
        ClientState state = (ClientState) key.attachment();
        if (state == null) return;
        boolean allSent = true;
        // Пытаемся отправить все буферы из очереди
        while (!state.getPendingWrites().isEmpty()) {
            ByteBuffer buffer = state.getPendingWrites().peek(); // смотрим первый буфер
            channel.write(buffer);                          // записываем в канал
            if (buffer.remaining() > 0) {                   // если буфер записан не полностью
                allSent = false;                            // отмечаем, что не всё ушло
                break;                                      // выходим из цикла, остальное отправим позже
            }
            state.getPendingWrites().poll();                     // буфер полностью отправлен – удаляем из очереди
        }
        if (allSent) {
            // Если все данные отправлены, убираем интерес к записи (оставляем только чтение)
            key.interestOps(SelectionKey.OP_READ);
        }
    }

    static private void closeConnection(SelectionKey key) throws IOException {
        key.cancel();
        key.channel().close();
        System.out.println("Клиент отключён");
    }


    public static Invoker getInvoker() {
        return invoker;
    }
}
