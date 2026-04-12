package core;

import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Класс ClientState хранит состояние одного клиентского соединения для неблокирующего (NIO) сервера.
 * Так как данные могут приходить частями, необходимо сохранять промежуточные буферы,
 * ожидаемую длину сообщения и очередь на отправку.
 */
public class ClientState {

    // ----- Поля для чтения входящих сообщений -----

    /**
     * Буфер для чтения длины сообщения (4 байта).
     * Всегда имеет фиксированный размер 4 байта.
     * Используется для накопления байтов длины, которые могут прийти не за один раз.
     */
    private final ByteBuffer lengthBuffer = ByteBuffer.allocate(4);

    /**
     * Буфер для чтения тела сообщения.
     * Создаётся динамически после того, как стала известна ожидаемая длина сообщения.
     * Размер буфера равен ожидаемой длине сообщения.
     * Может быть null, если мы ещё не знаем длину или сообщение полностью прочитано.
     */
    private ByteBuffer dataBuffer = null;

    /**
     * Ожидаемая длина текущего сообщения.
     * Значение -1 означает, что длина ещё не прочитана (или сообщение завершено, и мы готовы к следующему).
     * После получения длины из lengthBuffer устанавливается в положительное значение.
     */
    private int expectedLength = -1;

    // ----- Поля для отправки исходящих сообщений -----

    /**
     * Очередь буферов для отправки клиенту.
     * Используется потокобезопасная ConcurrentLinkedQueue, так как буферы могут добавляться
     * из разных потоков (например, из потока обработки команд, если он отдельный).
     * Каждое сообщение представляется двумя буферами: сначала буфер с длиной (4 байта),
     * затем буфер с самими данными.
     */
    private final Queue<ByteBuffer> pendingWrites = new ConcurrentLinkedQueue<>();

    // ----- Геттеры и сеттеры (с необходимыми методами) -----

    public ByteBuffer getLengthBuffer() {
        return lengthBuffer;
    }

    public ByteBuffer getDataBuffer() {
        return dataBuffer;
    }

    public void setDataBuffer(ByteBuffer dataBuffer) {
        this.dataBuffer = dataBuffer;
    }

    public int getExpectedLength() {
        return expectedLength;
    }

    public void setExpectedLength(int expectedLength) {
        this.expectedLength = expectedLength;
    }

    public Queue<ByteBuffer> getPendingWrites() {
        return pendingWrites;
    }

    /**
     * Добавляет буфер в очередь на отправку.
     * Удобный метод-обёртка.
     * @param buffer буфер для отправки (обычно это буфер длины или данных)
     */
    public void enqueueWrite(ByteBuffer buffer) {
        pendingWrites.add(buffer);
    }

    /**
     * Сбрасывает состояние чтения после того, как сообщение полностью обработано.
     * Вызывается, когда dataBuffer полностью прочитан и команда выполнена.
     * Позволяет начать приём следующего сообщения.
     */
    public void resetReading() {
        expectedLength = -1;
        dataBuffer = null;
        // lengthBuffer очищается автоматически при следующем использовании,
        // но для уверенности можно вызвать lengthBuffer.clear().
        // Однако lengthBuffer уже был переведён в режим записи и очищен в коде чтения.
        // Оставим так.
    }

    /**
     * Подготавливает буфер для приёма данных после того, как длина стала известна.
     * @param messageLength длина сообщения (положительное число)
     * @throws IllegalArgumentException если длина <= 0 или слишком большая (можно добавить проверку)
     */
    public void prepareDataBuffer(int messageLength) {
        if (messageLength <= 0) {
            throw new IllegalArgumentException("Invalid message length: " + messageLength);
        }
        // Можно добавить ограничение максимального размера, например, 10 МБ
        if (messageLength > 10_000_000) {
            throw new IllegalArgumentException("Message too large: " + messageLength);
        }
        this.dataBuffer = ByteBuffer.allocate(messageLength);
        this.expectedLength = messageLength;
    }

    /**
     * Проверяет, завершён ли приём текущего сообщения.
     * @return true, если dataBuffer не null и в нём не осталось места для записи (т.е. он заполнен)
     */
    public boolean isMessageComplete() {
        return dataBuffer != null && dataBuffer.remaining() == 0;
    }

    /**
     * Возвращает массив байтов, прочитанных в dataBuffer, и переводит буфер в режим чтения.
     * После вызова этого метода следует сбросить состояние через resetReading().
     * @return массив байтов с данными сообщения
     */
    public byte[] getMessageBytes() {
        if (dataBuffer == null) {
            throw new IllegalStateException("No data buffer");
        }
        dataBuffer.flip();                  // переключаем в режим чтения
        byte[] bytes = new byte[dataBuffer.limit()];
        dataBuffer.get(bytes);              // копируем в массив
        // Не очищаем dataBuffer здесь, это сделает resetReading()
        return bytes;
    }

    /**
     * Проверяет, есть ли данные для отправки клиенту.
     * @return true, если очередь отправки не пуста
     */
    public boolean hasPendingWrites() {
        return !pendingWrites.isEmpty();
    }

    /**
     * Возвращает и удаляет первый буфер из очереди отправки.
     * @return следующий буфер для записи, или null если очередь пуста
     */
    public ByteBuffer nextWriteBuffer() {
        return pendingWrites.poll();
    }

    /**
     * Очищает все данные о состоянии (при закрытии соединения).
     */
    public void clear() {
        lengthBuffer.clear();
        dataBuffer = null;
        expectedLength = -1;
        pendingWrites.clear();
    }
}