package commands;

import core.Manager;
import core.CommandResponse;
import interfases.Command;

import java.util.Map;

/**
 * Команда завершения цикла программы (без сохранения файла).
 */
public class Exit implements Command {
    /**
     * Вызывает команду
     *
     * @return
     */
    @Override
    public CommandResponse execute(Manager manager, Map<String, Object> args){
        return null;
    }
}
