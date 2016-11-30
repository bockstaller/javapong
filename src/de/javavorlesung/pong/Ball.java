package de.javavorlesung.pong;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Ball extends GameObject {


    private Ellipse2D ballShape;
    private static final double size = 20;

    public Ball() {
        super(new Coordinate(Constants.XRESOLUTION/2+10/2, Constants.YRESOLUTION/2+10/2),
                size,
                size,
                new Random().nextDouble()*360,
                0.1);
        setBallShape();
    }

    private void setBallShape(){
        ballShape = new Ellipse2D.Double(getObjectPosition().getX(),
                                         getObjectPosition().getY(),
                                         getWidth(),
                                         getHeight());
    }

    public Ellipse2D getBallShape(){
        return ballShape;
    }

    public boolean isStillinGame(Gamearea gamearea){
        Area area1 = new Area(gamearea.getGameareaShape());
        Area area2 = new Area(getBallShape());
        area1.intersect(area2);
        if (area1.isEmpty()){
            return false;
        }else{
            return true;
        }
    }



    public void move(){
        double newX;
        double newY;

        newX = Math.toDegrees(Math.cos(Math.toRadians(getMovingAngle())))*getMovingDistance();
        newX = newX + getObjectPosition().getX();
        newY = Math.toDegrees(Math.sin(Math.toRadians(getMovingAngle())))*getMovingDistance();
        newY = newY + getObjectPosition().getY();

        setObjectPosition(new Coordinate(newX, newY));


    }




    @Override
    public void paintMe(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawOval((int)getObjectPosition().getX(), (int)getObjectPosition().getY(), (int)getWidth(), (int)getHeight());
    }

}