package commands;

import core.Manager;
import core.CommandResponse;
import interfases.Command;

import java.util.Map;

/**
 * Команда отображения общей информации о коллекции.
 */
public class Info implements Command {
    /**
     * Вызывает команду
     *
     * @return
     */
    @Override
    public CommandResponse execute(Manager manager, Map<String, Object> args){
        if (args != null) return new CommandResponse("Команда не принимает аргументов");
        return new CommandResponse(manager.info());
    }
}
