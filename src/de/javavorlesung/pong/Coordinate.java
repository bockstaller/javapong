package de.javavorlesung.pong;


public class Coordinate {

    private double x;
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }



    public int getX() {
        return (int)x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getY() {
        return (int)y;
    }

    public void setY(double y) {
        this.y = y;
    }

}