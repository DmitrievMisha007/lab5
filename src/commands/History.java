package commands;

import core.App;
import interfases.Command;

public class History implements Command {
    @Override
    public void execute(){
        for (var i : App.getHistory()){
            System.out.println(i);
        }
    }
}
