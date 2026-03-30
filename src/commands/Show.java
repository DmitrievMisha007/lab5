package commands;

import core.App;
import core.Collection;
import interfases.Command;

/**
 * Команда, показывающая все элементы коллекции.
 */
public class Show implements Command {
    /**
     * Вызывает команду
     */
    @Override
    public void execute(){
        Collection collection = App.getCollection();
        if (collection.getCollection().isEmpty()) System.out.println("Коллекция пуста");
        else {
            for (var i : collection.getCollection()){
                System.out.println(i.toString());
            }
        }
    }
}
