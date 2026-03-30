package commands;

import core.App;
import core.Ticket;
import interfases.Command;

/**
 * Команда добавления нового элемента в коллекцию. Элемент добавляется, если он больше максимального из коллекции.
 */
public class AddIfMax implements Command {
    /**
     * Вызывает команду.
     */
    @Override
    public void execute(){
        Ticket ticket = new Ticket();
        ticket.resetParameters();
        if (App.getCollection().getCollection().isEmpty()) App.getCollection().getCollection().add(ticket);
        if (ticket.compareTo(App.getCollection().getCollection().stream().max(Ticket::compareTo).get())>0){
            App.getCollection().getCollection().add(ticket);
        }
    }
}
