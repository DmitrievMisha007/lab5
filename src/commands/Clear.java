package commands;

import core.App;
import interfases.Command;

public class Clear implements Command {
    @Override
    public void execute() {
        App.getCollection().clear();
    }
}
