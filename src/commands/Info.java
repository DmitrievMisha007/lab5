package commands;

import core.App;
import interfases.Command;

public class Info implements Command {
    @Override
    public void execute(){
        String result = "type: "+ App.getCollection().getClass().getName()+"\n"+
                "init date: "+App.getCollection().getInitDate()+"\n"+
                "amount of elements: "+App.getCollection().getCollection().size();
        System.out.println(result);
    }
}
