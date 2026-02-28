package commands;

import core.Collection;
import interfases.CommandWithCollection;

public class Show implements CommandWithCollection {
    @Override
    public void execute(Collection collection){
        for (var i : collection.getCollection()){
            System.out.println(i.toString());
        }
    }
}
