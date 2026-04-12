package commands;

import core.Manager;
import core.CommandResponse;
import core.Ticket;
import interfases.Command;

import java.util.Map;
import java.util.Optional;

/**
 * Команда, которая обновляет элемент по id.
 */
public class UpdateId implements Command {
    /**
     * Вызывает команду
     * @return Ответ клиенту
     */
    @Override
    public CommandResponse execute(Manager manager, Map<String, Object> args){
        if (args == null) return new CommandResponse("Некорректные аргументы");
        try {
            long id = Long.parseLong((String) args.get("arg1"));
            Optional<Ticket> optionalTicket = manager.getCollection().stream().filter((ticket -> ticket.getId() == id)).findFirst();
            if (optionalTicket.isPresent()) {
                optionalTicket.get().fromRequest(args);
                return new CommandResponse("Элемент с id = "+id+" успешно обновлен");
            }
            else return new CommandResponse("Элемента с таким id нет в коллекции");
        } catch (NumberFormatException e) {
            return new CommandResponse("Некорректный аргумент");
        }
    }
}
