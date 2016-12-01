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

    public Ball(double speed) {

        super( new Coordinate(Constants.XRESOLUTION/2+10/2+(new Random().nextDouble()*100),
                              Constants.YRESOLUTION/2+10/2+(new Random().nextDouble()*100)),
                size,
                size,
                new Random().nextDouble()*360,
                speed);
        setBallShape(getObjectPosition());
    }

    private void setBallShape(Coordinate position){
        setObjectPosition(position);
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

        area2.intersect(area1);
        if (area2.isEmpty()){
            return false;
        }else{
            return true;
        }
    }



    public void move(){
        double newX;
        double newY;

        newX = Math.toDegrees(Math.cos(Math.toRadians(getMovingAngle())))*getMovingDistance()/100;
        newX = newX + getObjectPosition().getX();
        newY = - Math.toDegrees(Math.sin(Math.toRadians(getMovingAngle())))*getMovingDistance()/100;
        newY = newY + getObjectPosition().getY();

        setBallShape(new Coordinate( newX, newY));
    }

    public void collide(Ball ball){
        setMovingAngle(getMovingAngle()+180);
        ball.setMovingAngle(getMovingAngle()+180);
    }




    @Override
    public void paintMe(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawOval((int)getObjectPosition().getX(), (int)getObjectPosition().getY(), (int)getWidth(), (int)getHeight());
    }

}