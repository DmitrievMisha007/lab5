package commands;

import core.App;
import core.Ticket;
import interfases.Command;

import java.util.Comparator;
import java.util.Iterator;

public class PrintFieldDescendingRefundable implements Command {
    @Override
    public void execute(){
        Iterator<Ticket> iterator = App.getCollection().getCollection().stream().sorted(Comparator.reverseOrder()).iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().isRefundable());
        }
    }
}
