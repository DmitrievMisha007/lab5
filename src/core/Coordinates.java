package core;

import interfases.WritableToJson;

/**
 * Класс, описывающий координаты для класса Ticket.
 */
public class Coordinates implements WritableToJson {
    private Double x;
    private double y;
    public Coordinates(){

    }

    @Override
    public String toString(){
        return "x: "+x+"\t|\ty: "+y;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
