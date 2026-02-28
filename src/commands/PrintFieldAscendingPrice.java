package commands;

import core.App;
import core.Ticket;
import interfases.Command;

import java.util.Iterator;

public class PrintFieldAscendingPrice implements Command {
    @Override
    public void execute(){
        Iterator<Ticket> iterator = App.getCollection().getCollection().stream().sorted(Ticket::compareTo).iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().getPrice());
        }
    }
}
