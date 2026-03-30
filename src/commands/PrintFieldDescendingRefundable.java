package commands;

import core.App;
import core.Ticket;
import interfases.Command;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Команда, которая выводит все элементы, отсортированные по убыванию цены.
 */
public class PrintFieldDescendingRefundable implements Command {
    /**
     * Вызывает команду
     */
    @Override
    public void execute(){
        Iterator<Ticket> iterator = App.getCollection().getCollection().stream().sorted(Comparator.reverseOrder()).iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().isRefundable());
        }
    }
}
