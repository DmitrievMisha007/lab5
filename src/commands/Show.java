package commands;

import core.App;
import core.Collection;
import interfases.Command;

public class Show implements Command {
    @Override
    public void execute(){
        Collection collection = App.getCollection();
        if (collection.getCollection().isEmpty()) System.out.println("Коллекция пуста");
        else {
            for (var i : collection.getCollection()){
                System.out.println(i.toString());
            }
        }
    }
}
