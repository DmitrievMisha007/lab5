package core;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Collection {
    ArrayDeque<Ticket> collection = new ArrayDeque<>();

    public ArrayDeque<Ticket> getCollection() {
        return collection;
    }

    public void setCollection(ArrayDeque<Ticket> collection) {
        this.collection = collection;
    }

//    public void add(Ticket ticket){
//        collection.add(ticket);
//    }
    public void add(){
        Ticket ticket = new Ticket();
        ticket.resetParameters();
        collection.add(ticket);
        System.out.println("Элемент успешно добавлен!");
    }
    public void removeById(long id){
        for (var i : collection){
            if (i.getId() == id){
                collection.remove(i);
                break;
            }
        }
    }
    public void clear(){
        collection.clear();
    }

    public void readCollection(String fileName){
        StringBuilder fileString = new StringBuilder();
        ArrayDeque<Ticket> collection = new ArrayDeque<>();
        FileReader reader;
        try{
            reader = new FileReader(fileName);
            while (reader.ready()){
                fileString.append((char) reader.read());
            }
            reader.close();
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
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.collection.add(ticket);
        }
    }

    public void writeCollection(String fileName){
        System.out.println(Ticket.toJson(collection.getFirst(), 0));
    }
}
