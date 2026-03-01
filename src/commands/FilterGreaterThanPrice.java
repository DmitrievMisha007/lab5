package commands;

import core.App;
import core.Ticket;
import interfases.CommandWithDouble;

import java.util.Iterator;

public class FilterGreaterThanPrice implements CommandWithDouble {
    @Override
    public void execute(double d){
        Iterator<Ticket> iterator = App.getCollection().getCollection().stream().filter((t)->t.getPrice()>d).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }
    }
}
