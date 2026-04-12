package commands;

import core.App;
import core.Manager;
import core.CommandResponse;
import core.Ticket;
import interfases.Command;

import java.util.Iterator;
import java.util.Map;

/**
 * Команда выводит все элементы коллекции, цена которых больше данной.
 */
public class FilterGreaterThanPrice implements Command {
    /**
     * Запускает команду
     *
     * @return Ответ клиенту
     */
    @Override
    public CommandResponse execute(Manager manager, Map<String, Object> args){
        if (args == null) return new CommandResponse("Некорректные аргументы");
        try {
            double price = Double.parseDouble((String) args.get("arg1"));
            StringBuilder result = new StringBuilder();
            Iterator<Ticket> iterator = manager.getCollection().stream().filter((t)->t.getPrice()>price).iterator();
            while (iterator.hasNext()) {
                result.append(iterator.next().toString()).append("\n");
            }
            return new CommandResponse(result.toString());
        } catch (NumberFormatException e) {
            return new CommandResponse("Некорректный аргумент");
        }
    }
}
