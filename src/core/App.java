package core;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Scanner;

/**
 * Абстрактный класс для управления приложением
 */
abstract public class App {
    static private String fileName;
    static private boolean isRun = true;
    static private Collection collection;
    static private ArrayDeque<String> history;
    static private Invoker invoker;

    static public void setRun(boolean run) {
        isRun = run;
    }

    /**
     * Инициализирует приложение
     * @param collection Коллекция
     * @param fileName Имя файла, с которым будет производиться работа
     * @param invoker Объект класса, вызывающего команды
     */
    static public void init(Collection collection, String fileName, Invoker invoker){
        App.collection = collection;
        App.fileName = fileName;
        App.invoker = invoker;
        App.history  = new ArrayDeque<>();
    }

    static private void addToHistory(String commandName){
        history.add(commandName);
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
    static public Invoker getInvoker(){
        return invoker;
    }

    /**
     * Запускает основной цикл программы
     */
    static public void run(){
        Scanner scanner = new Scanner(System.in);
        while (isRun){
            System.out.print("Введите команду: ");
            String line = scanner.nextLine().trim();
            String[] words = line.split("\\s+");
            if (words.length == 1){
                switch (words[0]){
                    case "help" -> {
                        invoker.help.execute();
                        addToHistory("help");
                    }
                    case "info" -> {
                        invoker.info.execute();
                        addToHistory("info");
                    }
                    case "show" -> {
                        invoker.show.execute();
                        addToHistory("show");
                    }
                    case "add" -> {
                        invoker.add.execute();
                        addToHistory("add");
                    }


                    case "clear" -> {
                        invoker.clear.execute();
                        addToHistory("clear");
                    }
                    case "save" -> {
                        invoker.save.execute();
                        addToHistory("save");
                    }

                    case "exit" -> {
                        invoker.exit.execute();
                        addToHistory("exit");
                    }
                    case "add_if_max" -> {
                        invoker.addIfMax.execute();
                        addToHistory("add_if_max");
                    }
                    case "add_if_min" -> {
                        invoker.addIfMin.execute();
                        addToHistory("add_if_min");
                    }
                    case "history" -> {
                        invoker.history.execute();
                        addToHistory("history");
                    }

                    case "print_field_ascending_price" -> {
                        invoker.printFieldAscendingPrice.execute();
                        addToHistory("print_field_ascending_price");
                    }
                    case "print_field_descending_refundable" -> {
                        invoker.printFieldDescendingRefundable.execute();
                        addToHistory("print_field_descending_refundable");
                    }
                    default -> System.out.println("Ошибка ввода команды");
                }
            } else if (words.length == 2) {
                if (words[1].matches("[0-9]+")) {
                    switch (words[0]) {
                        case "update" -> {
                            invoker.updateId.execute(Long.parseLong(words[1]));
                            addToHistory("update");
                        }
                        case "remove_by_id" -> {
                            invoker.removeById.execute(Long.parseLong(words[1]));
                            addToHistory("remove_by_id");
                        }
                        default -> System.out.println("Ошибка ввода команды");
                    }
                } else if (words[1].matches("[0-9]+\\.[0-9]+")) {
                    switch (words[0]) {
                        case "filter_greater_than_price" -> {
                            invoker.filterGreaterThanPrice.execute(Double.parseDouble(words[1]));
                            addToHistory("filter_greater_than_price");
                        }
                        default -> System.out.println("Ошибка ввода команды");
                    }
                } else if (Objects.equals(words[0], "execute_script")) {
                    addToHistory("execute_script");
                    invoker.executeScript.execute(words[1]);
                }
                else {
                    System.out.println("Ошибка ввода команды");
                }
            }
            else {
                System.out.println("Ошибка ввода команды");
            }
        }
    }

}
