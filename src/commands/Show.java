package commands;

import core.App;
import interfases.Command;

public class Show implements Command {
    @Override
    public void execute(){
        for (var i : App.getCollection().getCollection()){
            System.out.println(i.toString());
        }
    }
}
