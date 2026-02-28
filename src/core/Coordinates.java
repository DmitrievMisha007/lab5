package core;

import interfases.WritableToJson;

public class Coordinates implements WritableToJson {
    private Double x; //Максимальное значение поля: 851, Поле не может быть null
    private double y; //Максимальное значение поля: 621
    Coordinates(Double x, double y){
        this.x = x;
        this.y = y;
    }
    public Coordinates(){

    }

    @Override
    public String toString(){
        return "x: "+x+"\ty: "+y;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
}
