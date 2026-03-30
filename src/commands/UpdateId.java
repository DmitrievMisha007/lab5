package commands;

import core.App;
import interfases.CommandWithLong;

/**
 * Команда, которая обновляет элемент по id.
 */
public class UpdateId implements CommandWithLong {
    /**
     * Вызывает команду
     * @param id Номер id элемента коллекции, значение которого нужно обновить
     */
    @Override
    public void execute( long id){
        for (var i : App.getCollection().getCollection()){
            if (i.getId() == id){
                i.resetParameters();
            }
        }
    }
}
