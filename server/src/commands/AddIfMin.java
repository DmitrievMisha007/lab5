package commands;

import core.Manager;
import core.CommandResponse;
import core.Ticket;
import interfases.Command;

import java.util.Map;

/**
 * Команда добавления нового элемента в коллекцию. Элемент добавляется, если он меньше минимального из коллекции.
 */
public class AddIfMin implements Command {
    /**
     * Вызывает команду.
     *
     * @return Ответ клиенту
     */
    @Override
    public CommandResponse execute(Manager manager, Map<String, Object> args){
        if (args == null) new CommandResponse("Команда не принимает аргументов");
        Ticket ticket = new Ticket();
        assert args != null;
        ticket.fromRequest(args);
        if (manager.isEmpty()) {
            manager.add(ticket);
            return new CommandResponse("Элемент добавлен");
        }
        if (ticket.compareTo(manager.getCollection().stream().max(Ticket::compareTo).get())<0){
            manager.add(ticket);
            return new CommandResponse("Элемент добавлен");
        }
        return new CommandResponse("Элемент не добавлен");
    }
}
