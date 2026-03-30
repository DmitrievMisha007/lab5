package commands;

import core.App;
import interfases.Command;

/**
 * Команда очистки коллекции.
 */
public class Clear implements Command {
    /**
     * Вызывает команду.
     */
    @Override
    public void execute() {
        App.getCollection().clear();
    }
}
