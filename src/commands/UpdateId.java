package commands;

import core.App;
import interfases.CommandWithLong;

public class UpdateId implements CommandWithLong {
    @Override
    public void execute( long id){
        for (var i : App.getCollection().getCollection()){
            if (i.getId() == id){
                i.resetParameters();
            }
        }
    }
}
