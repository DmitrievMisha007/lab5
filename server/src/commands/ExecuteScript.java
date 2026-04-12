package commands;

import core.App;
import core.Manager;
import core.CommandResponse;
import interfases.Command;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Команда, которая вызывает команды из файла.
 */
public class ExecuteScript implements Command {
    /**
     * Вызывает команду
     *
     * @return Ответ клиенту
     */
    @Override
    public CommandResponse execute(Manager manager, Map<String, Object> args) {
        if (args == null) return new CommandResponse("Некорректные аргументы");
        String fileName = (String) args.get("arg1");
        Path path = Paths.get(fileName);
        for (String i : manager.getStack()){
            if (i.equals(fileName)){
                return new CommandResponse("Возникла рекурсия, команды из файла не выполнены");
            }
        }
        manager.getStack().add(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] words = line.split("\\s+");

                Map<String, Object> argsToExe = new LinkedHashMap<>();
                for (int i = 1; i != words.length; i++){
                    argsToExe.put("arg"+i, words[i]);
                }

                if (words.length == 1){
                    switch (words[0]){
                        case "help" -> App.getInvoker().help.execute(manager, null);
                        case "info" -> App.getInvoker().info.execute(manager, null);
                        case "show" -> App.getInvoker().show.execute(manager, null);
                        case "add" -> App.getInvoker().add.execute(manager, null);
                        case "clear" -> App.getInvoker().clear.execute(manager, null);
                        case "save" -> App.getInvoker().save.execute(manager, null);
                        case "exit" -> App.getInvoker().exit.execute(manager, null);
                        case "add_if_max" -> App.getInvoker().addIfMax.execute(manager, null);
                        case "add_if_min" -> App.getInvoker().addIfMin.execute(manager, null);
                        case "history" -> App.getInvoker().history.execute(manager, null);
                        case "print_field_ascending_price" -> App.getInvoker().printFieldAscendingPrice.execute(manager, null);
                        case "print_field_descending_refundable" -> App.getInvoker().printFieldDescendingRefundable.execute(manager, null);
                    }
                } else if (words.length == 2) {
//                    if (words[1].matches("[0-9]+")) {
//                        switch (words[0]) {
//                            case "update" -> App.getInvoker().updateId.execute(manager, argsToExe);
//                            case "remove_by_id" -> App.getInvoker().removeById.execute(manager);
//                        }
//                    } else if (words[1].matches("[0-9]+\\.[0-9]+")) {
//                        switch (words[0]) {
//                            case "filter_greater_than_price" -> {
//                                App.getInvoker().filterGreaterThanPrice.execute(Double.parseDouble(words[1]));
//                            }
//                        }
//                    } else if (Objects.equals(words[0], "execute_script")) {
//                        App.getInvoker().executeScript.execute(words[1]);
//                    }
                }

            }

        } catch (AccessDeniedException exception) {
            return new CommandResponse("Недостаточно прав доступа для чтения коллекции");
        } catch (IOException e) {
            return new CommandResponse("Файл не найден");
        }
        return null;
    }
}
