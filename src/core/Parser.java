package core;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Parser {
    private FileReader reader;
    private BufferedWriter writer;



    private static String getString(String json, String key){
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern);
        if (start == -1) return null;

        start += pattern.length();

        while (json.charAt(start) == ' ' || json.charAt(start) == '\"') start++;

        int end = start;
        while (json.charAt(end) != '\"') end++;

        return json.substring(start, end);
    }

    private static double getDouble(String json, String key){
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern);
        if (start == -1) return 0;

        start += pattern.length();

        while (json.charAt(start) == ' ') start++;

        int end = start;
        while (end < json.length() && "0123456789.".indexOf(json.charAt(end)) != -1){
            end++;
        }

        return Double.parseDouble(json.substring(start, end));
    }

    private static long getLong(String json, String key){
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern);
        if (start == -1) return 0;

        start += pattern.length();

        while (json.charAt(start) == ' ') start++;

        int end = start;
        while (end < json.length() && "0123456789".indexOf(json.charAt(end)) != -1){
            end++;
        }

        return Long.parseLong(json.substring(start, end));
    }

    private static boolean getBoolean(String json, String key){
        String value = getString(json, key);
        return Boolean.parseBoolean(value);
    }

    private void parseTicket(){

    }

    public ArrayDeque<Ticket> readCollection(String fileName){
        StringBuilder fileString = new StringBuilder();
        ArrayDeque<Ticket> collection = new ArrayDeque<>();
        try{
            reader = new FileReader(fileName);
            while (reader.ready()){
                fileString.append((char) reader.read());
            }
            reader.close();
        } catch (AccessDeniedException exception){
            System.out.println("Недостаточно прав доступа для чтения коллекции");
            return collection;
        } catch (IOException exception){
            System.out.println("Файл не найден");
            return collection;
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

        for (String i : listStrings){
            Ticket ticket = new Ticket();

            long ticketId = getLong(i, "id");
            String ticketName = getString(i, "name");
            Coordinates coordinates = new Coordinates(getDouble(i, "x"), getDouble(i, "y"));
            Date date = new Date();
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                date = formatter.parse(getString(i, "creationDate"));
            } catch (Exception exception){
                date = new Date();
            }


            double price = getDouble(i, "price");
            String comment = getString(i, "comment");
            boolean refundable = getBoolean(i, "refundable");
            TicketType type = TicketType.valueOf(getString(i, "type"));
            Event event = new Event();
            event.setId(getLong(i.substring(i.indexOf("event")), "id"));
            event.setName(getString(i.substring(i.lastIndexOf("name")-1), "name"));
            event.setTicketsCount((long)getDouble(i, "ticketsCount"));
            event.setEventType(EventType.valueOf(getString(i, "eventType")));

            ticket.setId(ticketId);
            ticket.setName(ticketName);
            ticket.setCoordinates(coordinates);
            ticket.setCreationDate(date);
            ticket.setPrice(price);
            ticket.setComment(comment);
            ticket.setRefundable(refundable);
            ticket.setType(type);
            ticket.setEvent(event);
            collection.add(ticket);
        }

        return collection;
    }


//    public void writeCollection(ArrayDeque<Ticket> collection){
//        try {
//            writer = new BufferedWriter(new FileWriter("data.json"));
//            if (!collection.isEmpty()){
//                StringBuilder toWrite = new StringBuilder("[\n\t{\n\t\t");
//                for (var i : collection){
//                    toWrite.append("id: ").append(i.getId()).append(",\n\t\t");
//                    toWrite.append("name: ").append(i.getName()).append(",\n\t\t");
//                    toWrite.append("coordinates: {\n\t\t\t");
//                    toWrite.append("x: ").append(i.getCoordinates().getX()).append(",\n\t\t\t");
//                    toWrite.append()
//
//                }
//// написать сохранение ticket в файл
//                toWrite.toString().append("\n}");
//                writer.write(String.valueOf(toWrite.toString()));
//                writer.close();
//            }
//        } catch (AccessDeniedException exception){
//            System.out.println("Недостаточно прав доступа для записи файла");
//        } catch (IOException exception){
//            System.out.println("Файл не найден");
//        }
//    }
}
