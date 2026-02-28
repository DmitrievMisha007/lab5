package core;

import interfases.WritableToJson;

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
        return "\n\tid: "+id+"\n\t"+
                "name: "+name+"\n\t"+
                "ticketCount: "+ticketsCount+"\n\t"+
                "EventType: "+eventType.toString();
    }
}
