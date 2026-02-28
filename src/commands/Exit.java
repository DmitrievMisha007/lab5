package commands;

import core.App;
import interfases.Command;

public class Exit implements Command {
    @Override
    public void execute(){
        App.setRun(false);
    }
}
