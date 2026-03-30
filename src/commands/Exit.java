package commands;

import core.App;
import interfases.Command;

/**
 * Команда завершения цикла программы (без сохранения файла).
 */
public class Exit implements Command {
    /**
     * Вызывает команду
     */
    @Override
    public void execute(){
        App.setRun(false);
    }
}
