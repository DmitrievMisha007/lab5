package commands;

import core.App;
import core.Ticket;
import interfases.CommandWithDouble;

import java.util.Iterator;

/**
 * Команда выводит все элементы коллекции, цена которых больше данной.
 */
public class FilterGreaterThanPrice implements CommandWithDouble {
    /**
     * Запускает команду
     * @param d Цена для сравнения
     */
    @Override
    public void execute(double d){
        Iterator<Ticket> iterator = App.getCollection().getCollection().stream().filter((t)->t.getPrice()>d).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }
    }
}
