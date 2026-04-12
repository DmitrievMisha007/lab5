package core;

import java.io.Serializable;
import java.util.Map;

public class CommandRequest implements Serializable {
    private String name;
    private Map<String, Object> args;

    public CommandRequest(String name, Map<String, Object> args){
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getArgs() {
        return args;
    }
}
