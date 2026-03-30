package commands;

import core.App;
import interfases.CommandWithString;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

/**
 * Команда, которая вызывает команды из файла.
 */
public class ExecuteScript implements CommandWithString {
    /**
     * Вызывает команду
     * @param fileName Имя файла
     */
    @Override
    public void execute(String fileName) {

        try (FileReader reader = new FileReader(fileName)) {
            Scanner scanner = new Scanner(reader);
            for (String i : App.getStack()){
                if (i.equals(fileName)){
                    return;
                }
            }
            App.getStack().add(fileName);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] words = line.split("\\s+");
                if (words.length == 1){
                    switch (words[0]){
                        case "help" -> App.getInvoker().help.execute();
                        case "info" -> App.getInvoker().info.execute();
                        case "show" -> App.getInvoker().show.execute();
                        case "add" -> App.getInvoker().add.execute();
                        case "clear" -> App.getInvoker().clear.execute();
                        case "save" -> App.getInvoker().save.execute();
                        case "exit" -> App.getInvoker().exit.execute();
                        case "add_if_max" -> App.getInvoker().addIfMax.execute();
                        case "add_if_min" -> App.getInvoker().addIfMin.execute();
                        case "history" -> App.getInvoker().history.execute();
                        case "print_field_ascending_price" -> App.getInvoker().printFieldAscendingPrice.execute();
                        case "print_field_descending_refundable" -> App.getInvoker().printFieldDescendingRefundable.execute();
                    }
                } else if (words.length == 2) {
                    if (words[1].matches("[0-9]+")) {
                        switch (words[0]) {
                            case "update" -> App.getInvoker().updateId.execute(Long.parseLong(words[1]));
                            case "remove_by_id" -> App.getInvoker().removeById.execute(Long.parseLong(words[1]));
                        }
                    } else if (words[1].matches("[0-9]+\\.[0-9]+")) {
                        switch (words[0]) {
                            case "filter_greater_than_price" -> {
                                App.getInvoker().filterGreaterThanPrice.execute(Double.parseDouble(words[1]));
                            }
                        }
                    } else if (Objects.equals(words[0], "execute_script")) {
                        App.getInvoker().executeScript.execute(words[1]);
                    }
                }
            }
            App.getStack().pop();
        } catch (AccessDeniedException exception){
            System.out.println("Недостаточно прав доступа для чтения коллекции");
        } catch (IOException exception){
            System.out.println("Файл не найден");
        }
    }
}
