package commands;


import core.App;
import interfases.Command;

/**
 * Команда сохранения коллекции в файл.
 */
public class Save implements Command {
    /**
     * Вызывает команду
     */
    @Override
    public void execute(){
        App.getCollection().writeCollection(App.getFileName());
    }
}
