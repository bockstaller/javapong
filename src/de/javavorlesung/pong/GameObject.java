package de.javavorlesung.pong;


import java.awt.geom.Area;
import java.awt.Shape;

public abstract class GameObject {

    private Coordinate objectPosition;
    private double width;
    private double height;
    private double movingAngle;
    private double movingDistance;
    private Shape shape;

    public GameObject(Coordinate objectPosition, double width, double height, double angle, double speed) {
        this.objectPosition = objectPosition;
        this.width = width;
        this.height = height;
        this.movingAngle = angle;
        this.movingDistance = speed;
    }

    public Coordinate getObjectPosition() {
        return objectPosition;
    }

    public void setShape(Shape shape){
        this.shape = shape;
    }
    public Shape getShape(){
        return shape;
    }

    public void setObjectPosition(Coordinate objectPosition) {
        this.objectPosition = objectPosition;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }


    public double getMovingAngle() {
        return movingAngle;
    }

    public void setMovingAngle(double movingAngle) {
        this.movingAngle = movingAngle;
    }

    public double getMovingDistance() {
        return movingDistance;
    }

    public void setMovingDistance(double movingDistance) {
        this.movingDistance = movingDistance;
    }

    public boolean isLeftOf(GameObject that) {
        return this.getObjectPosition().getX() + this.getWidth() < that.getObjectPosition().getX();
    }

    public boolean isAbove(GameObject that) {
        return this.getObjectPosition().getY() + this.getHeight() < that.getObjectPosition().getY();
    }

    public boolean checkCollision(Shape shape1){
        Area tempArea1 = new Area(shape1);
        Area tempArea2 = new Area(this.shape);
        tempArea1.intersect(tempArea2);
        return !tempArea1.isEmpty();
    }





    protected abstract void paintMe(java.awt.Graphics g);

}