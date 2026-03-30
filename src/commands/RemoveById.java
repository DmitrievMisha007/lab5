package commands;

import core.App;
import interfases.CommandWithLong;

/**
 * Команда, которая удаляет элемент по id.
 */
public class RemoveById implements CommandWithLong {
    /**
     * Вызывает команду
     * @param id Номер id элемента, которого нужно удалить
     */
    @Override
    public void execute(long id) {
        App.getCollection().removeById(id);
    }
}
