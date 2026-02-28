package core;

import java.util.ArrayDeque;
import java.util.Scanner;

public class App {
    static private String fileName;
    static private boolean isRun = true;
    static private Collection collection;
    static private ArrayDeque<String> history;
    static private Invoker invoker;

    static public void setRun(boolean run) {
        isRun = run;
    }

    static public void init(Collection collection, String fileName, Invoker invoker){
        App.collection = collection;
        App.fileName = fileName;
        App.invoker = invoker;
    }

    static public void addToHistory(Object object){
        history.add(object.getClass().getName());
        if (history.size() > 10){
            history.removeFirst();
        }
    }

    static public ArrayDeque<String> getHistory(){
        return history;
    }


    static public Collection getCollection(){
        return collection;
    }
    static public String getFileName(){
        return fileName;
    }

    static public void run(){
        Scanner scanner = new Scanner(System.in);
        while (isRun && scanner.hasNextLine()){
            String line = scanner.nextLine().trim();
            String[] words = line.split(" ");
            if (words.length == 1){
                switch (words[0]){
                    case "help" -> invoker.help.execute();
                    case "info" -> invoker.info.execute();
                    case "show" -> invoker.show.execute();
                    case "add" -> invoker.add.execute();


                    case "clear" -> invoker.clear.execute();
                    case "save" -> invoker.save.execute();

                    case "exit" -> invoker.exit.execute();
                    case "add_if_max" -> invoker.addIfMax.execute();
                    case "add_if_min" -> invoker.addIfMin.execute();
                    case "history" -> invoker.history.execute();

                    case "print_field_ascending_price" -> invoker.printFieldAscendingPrice.execute();
                    case "print_field_descending_refundable" -> invoker.printFieldDescendingRefundable.execute();

                }
            }
        }
    }

}
