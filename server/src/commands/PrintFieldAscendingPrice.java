package commands;

import core.App;
import core.Manager;
import core.CommandResponse;
import core.Ticket;
import interfases.Command;

import java.util.Iterator;
import java.util.Map;

/**
 * Команда, которая выводит все элементы, отсортированные по возрастанию цены.
 */
public class PrintFieldAscendingPrice implements Command {
    /**
     * Вызывает команду
     *
     * @return Ответ клиенту
     */
    @Override
    public CommandResponse execute(Manager manager, Map<String, Object> args){
        if (args != null) return new CommandResponse("Команда не принимает аргументов");
        StringBuilder result = new StringBuilder();
        Iterator<Ticket> iterator = manager.getCollection().stream().sorted(Ticket::compareTo).iterator();
        while (iterator.hasNext()){
            result.append(iterator.next().getPrice()).append("\n");
        }
        return new CommandResponse(result.toString());
    }
}
