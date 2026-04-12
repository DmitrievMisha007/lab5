package commands;

import core.Manager;
import core.CommandResponse;
import interfases.Command;

import java.util.Map;

/**
 * Команда очистки коллекции.
 */
public class Clear implements Command {
    /**
     * Вызывает команду.
     *
     * @return Ответ клиенту
     */
    @Override
    public CommandResponse execute(Manager manager, Map<String, Object> args) {
        if (args != null) return new CommandResponse("Команда не принимает аргументов");
        manager.clear();
        return new CommandResponse("Коллекция очищена");
    }
}
