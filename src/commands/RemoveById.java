package commands;

import core.App;
import interfases.CommandWithLong;

public class RemoveById implements CommandWithLong {
    @Override
    public void execute(long id) {
        App.getCollection().removeById(id);
    }
}
