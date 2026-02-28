package commands;

import core.Collection;
import interfases.CommandWithCollection;

public class Clear implements CommandWithCollection {
    @Override
    public void execute(Collection collection) {
        collection.clear();
    }
}
