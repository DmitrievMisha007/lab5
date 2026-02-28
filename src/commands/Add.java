package commands;

import core.Collection;
import interfases.CommandWithCollection;

public class Add implements CommandWithCollection {
    @Override
    public void execute(Collection collection){
        collection.add();
    }
}
