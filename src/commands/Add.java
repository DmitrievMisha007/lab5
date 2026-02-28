package commands;

import core.App;
import interfases.Command;

public class Add implements Command {
    @Override
    public void execute(){
        App.getCollection().add();
    }
}
