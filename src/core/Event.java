package core;

import interfases.WritableToJson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Event implements WritableToJson {
    static private Long currentId = new Long(1);
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private long ticketsCount; //Значение поля должно быть больше 0
    private EventType eventType; //Поле не может быть null
    public Event(){
        id = currentId;
        currentId += 1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTicketsCount(long ticketsCount) {
        this.ticketsCount = ticketsCount;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString(){

        Field[] fields = this.getClass().getDeclaredFields();
        StringBuilder result = new StringBuilder();
        for (Field f : fields){
            try {
                if (Modifier.isStatic(f.getModifiers())) {
                    continue;
                }
                Object value = f.get(this);
                if (value != null){
                    result.append(f.getName()).append(": ").append(value).append("\n");
                }
                else {
                    result.append(f.getName()).append(": null\n");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return result.toString();
    }
}
