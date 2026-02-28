package core;

import java.util.ArrayDeque;

public class App {
    static private boolean isRun = true;
    static private Collection collection;
    static private ArrayDeque<String> history;

    static public boolean isRun() {
        return isRun;
    }

    static public void setRun(boolean run) {
        isRun = run;
    }

    static public void init(Collection collection){
        App.collection = collection;
    }

    static public void addToHistory(Object object){
        history.add(object.getClass().getName());
        if (history.size() > 10){
            history.removeFirst();
        }
    }

    static public ArrayDeque<String> getHistory(){
        return history;
    }
}
