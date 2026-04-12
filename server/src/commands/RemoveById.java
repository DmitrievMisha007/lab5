package commands;

import core.Manager;
import core.CommandResponse;
import interfases.Command;

import java.util.Map;

/**
 * Команда, которая удаляет элемент по id.
 */
public class RemoveById implements Command {
    /**
     * Вызывает команду
     *
     * @return Ответ клиенту
     */
    @Override
    public CommandResponse execute(Manager manager, Map<String, Object> args) {
        if (args == null) return new CommandResponse("Некорректные аргументы");
        try {
            if (manager.removeById(Long.parseLong((String) args.get("arg1"))))
            return new CommandResponse("Элемент успешно удален");
            else return new CommandResponse("Элемент с таким id не найден");
        } catch (NumberFormatException e) {
            return new CommandResponse("Некорректный аргумент");
        }
    }
}