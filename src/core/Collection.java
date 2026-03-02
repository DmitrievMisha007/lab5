package core;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Collection {
    private Date initDate;

    private ArrayDeque<Ticket> collection = new ArrayDeque<>();

    public Collection(){
        initDate = new Date();
    }

    public ArrayDeque<Ticket> getCollection() {
        return collection;
    }
    public Date getInitDate() {
        return initDate;
    }

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
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.collection.add(ticket);
            if (Ticket.getCurrentId() <= ticket.getId()) Ticket.setCurrentId(ticket.getId()+1);
        }
    }

    public void writeCollection(String fileName){
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
