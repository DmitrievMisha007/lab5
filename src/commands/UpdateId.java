package commands;

import core.Collection;
import interfases.CommandWithCollectionAndLong;

public class UpdateId implements CommandWithCollectionAndLong {
    @Override
    public void execute(Collection collection, long id){
        for (var i : collection.getCollection()){
            if (i.getId() == id){
                i.resetParameters();
            }
        }
    }
}
