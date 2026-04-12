package commands;

import core.*;
import interfases.Command;

import java.util.Map;

/**
 * Команда добавления нового элемента в коллекцию.
 */
public class Add implements Command {
    /**
     * Вызывает команду.
     *
     * @return Ответ клиенту
     */
    @Override
    public CommandResponse execute(Manager manager, Map<String, Object> args){
        if (args == null) new CommandResponse("Команда не принимает аргументов");
        Ticket ticket = new Ticket();
        ticket.fromRequest(args);
        manager.add(ticket);
        return new CommandResponse("Элемент успешно добавлен");
    }
}
