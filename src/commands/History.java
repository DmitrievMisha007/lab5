package commands;

import core.App;
import interfases.Command;

/**
 * Команда, которая отображает историю вызова последних 10 команд.
 */
public class History implements Command {
    /**
     * Вызывает команду
     */
    @Override
    public void execute(){
        for (var i : App.getHistory()){
            System.out.println(i);
        }
    }
}
