package core;

import commands.*;
import interfases.*;

public class Invoker {
    public Command help;
    public Command info;
    public Command show;
    public Command add;
    public CommandWithLong updateId;
    public CommandWithLong removeById;
    public Command clear;
    public Command save;
    public CommandWithString executeScript;
    public Command exit;
    public Command addIfMax;
    public Command addIfMin;
    public Command history;
    public CommandWithDouble filterGreaterThanPrice;
    public Command printFieldAscendingPrice;
    public Command printFieldDescendingRefundable;

    public void init(){
        help = new Help();
        info = new Info();
        show = new Show();
        add = new Add();
        updateId = new UpdateId();
        removeById = new RemoveById();
        clear = new Clear();
        save = new Save();

        exit = new Exit();
        addIfMax = new AddIfMax();
        addIfMin = new AddIfMin();
        history = new History();
        filterGreaterThanPrice = new FilterGreaterThanPrice();
        printFieldAscendingPrice = new PrintFieldAscendingPrice();
        printFieldDescendingRefundable = new PrintFieldDescendingRefundable();
    }
}
