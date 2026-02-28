package commands;

import core.App;
import core.Ticket;
import interfases.Command;

public class AddIfMin implements Command {
    @Override
    public void execute(){
        Ticket ticket = new Ticket();
        ticket.resetParameters();
        if (ticket.compareTo(App.getCollection().getCollection().stream().min(Ticket::compareTo).get())<0){
            App.getCollection().getCollection().add(ticket);
        }
    }
}
