package commands;

import core.Collection;
import interfases.CommandWithCollectionAndLong;

public class RemoveById implements CommandWithCollectionAndLong {
    @Override
    public void execute(Collection collection, long id) {
        collection.removeById(id);
    }
}
