package commands;


import core.App;
import interfases.Command;

public class Save implements Command {
    @Override
    public void execute(){
        App.getCollection().writeCollection(App.getFileName());
    }
}
