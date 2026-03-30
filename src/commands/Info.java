package commands;

import core.App;
import interfases.Command;

/**
 * Команда отображения общей информации о коллекции.
 */
public class Info implements Command {
    /**
     * Вызывает команду
     */
    @Override
    public void execute(){
        String result = "type: "+ App.getCollection().getClass().getName()+"\n"+
                "init date: "+App.getCollection().getInitDate()+"\n"+
                "amount of elements: "+App.getCollection().getCollection().size();
        System.out.println(result);
    }
}
