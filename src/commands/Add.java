package commands;

import core.App;
import interfases.Command;

/**
 * Команда добавления нового элемента в коллекцию.
 */
public class Add implements Command {
    /**
     * Вызывает команду.
     */
    @Override
    public void execute(){
        App.getCollection().add();
    }
}
