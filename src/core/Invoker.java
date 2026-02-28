package core;

import commands.*;
import interfases.*;

public class Invoker {
    public Command help;
    public CommandWithCollection info;
    public CommandWithCollection show;
    public CommandWithCollection add;
    public CommandWithCollectionAndLong updateId;
    public CommandWithCollectionAndLong removeById;
    public CommandWithCollection clear;
    public CommandWithCollection save;
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

        show = new Show();
        add = new Add();
        updateId = new UpdateId();
        removeById = new RemoveById();
        clear = new Clear();
        save = new Save();

        exit = new Exit();
    }
}
