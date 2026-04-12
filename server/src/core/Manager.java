package core;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.*;

/**
 * Класс, описывающий коллекцию для хранения элементов класса Ticket
 */
public class Manager {
    private Date initDate;

    private ArrayDeque<Ticket> collection = new ArrayDeque<>();
    private String fileName;

    private ArrayDeque<String> history;

    public Manager(String fileName){
        this.fileName = fileName;
        initDate = new Date();
        history = new ArrayDeque<>();
    }

    public ArrayDeque<String> getHistory() { return history; }
    public void updateHistory(String commandName) {
        history.add(commandName);
        if (history.size() > 10) history.removeFirst();
    }

    public ArrayDeque<Ticket> getCollection() {
        return collection;
    }
    public Date getInitDate() {
        return initDate;
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * Добавить элемент в коллекцию
     */
    public void add(Ticket ticket){
        collection.add(ticket);
    }

    public boolean isEmpty(){
        return collection.isEmpty();
    }

    /**
     * Удалить элемент из коллекции по id
     * @param id Номер элемента, предназначенного для удаления
     */
    public boolean removeById(long id){
        for (var i : collection){
            if (i.getId() == id){
                return collection.remove(i);
            }
        }
        return false;
    }

    /**
     * Очистить коллекцию
     */
    public void clear(){
        collection.clear();
    }

    public String info() {
        return "type: "+ collection.getClass().getName()+"\n"+
                "init date: "+initDate+"\n"+
                "amount of elements: "+collection.size();
    }
    public String getStringToShow() {
        if (collection.isEmpty()) return "Коллекция пуста";
        else {
            StringBuilder result = new StringBuilder();
            for (var i : collection){
                result.append(i.toString()).append("\n");
            }
            return result.toString();
        }
    }

    /**
     * Заполняет коллекцию элементами из файла
     * @param fileName Имя файла
     */
    public void readCollection(String fileName){
        StringBuilder fileString = new StringBuilder();

        try (FileReader reader = new FileReader(fileName)){

            while (reader.ready()){
                fileString.append((char) reader.read());
            }
        } catch (AccessDeniedException exception){
            System.out.println("Недостаточно прав доступа для чтения коллекции");
        } catch (IOException exception){
            System.out.println("Файл не найден");
        }


        ArrayList<String> listStrings = new ArrayList<>();


        int last = 0;
        int deep = 0;
        for (int i = 0; i != fileString.length(); ++i){
            if (fileString.charAt(i) == '{'){
                ++deep;
            }
            if (fileString.charAt(i) == '}'){
                if (--deep == 0){
                    listStrings.add(fileString.substring(last, i+2));
                    last = i+2;
                }
            }
        }

        for (var i : listStrings){
            Ticket ticket;
            try {
                ticket = Ticket.fromJson(i, 0, Ticket.class);
                this.collection.add(ticket);
                if (Ticket.getCurrentId() <= ticket.getId()) Ticket.setCurrentId(ticket.getId()+1);
            } catch (Exception e) {
                System.out.println("Файл поврежден");
            }

        }
    }

    /**
     * Записывает все элементы коллекции в файл
     */
    public void writeCollection(){
        StringBuilder toWrite = new StringBuilder("[");
        Iterator<Ticket> iterator = collection.iterator();
        while (iterator.hasNext()) {
            toWrite.append(Ticket.toJson(iterator.next(), 1));
            if (iterator.hasNext()) toWrite.append(",\n");
        }

        toWrite.append("]");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){

            writer.write(String.valueOf(toWrite));
        } catch (AccessDeniedException exception){
            System.out.println("Недостаточно прав доступа для чтения коллекции");
        } catch (IOException exception){
            System.out.println("Файл не найден");
        }
    }
}
