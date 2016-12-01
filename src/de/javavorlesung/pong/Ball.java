package de.javavorlesung.pong;


import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;
import java.util.Random;

public class Ball extends GameObject {

    private Ellipse2D ballShape;


    public Ball(double speed) {

        super( new Coordinate(Constants.XRESOLUTION/2+10/2+(new Random().nextDouble()*100),
                              Constants.YRESOLUTION/2+10/2+(new Random().nextDouble()*100)),
                Constants.BALLSIZE,
                Constants.BALLSIZE,
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

    public boolean isStillInGame(Gamearea gamearea){
        Area area1 = new Area(gamearea.getGameareaShape());
        Area area2 = new Area(getBallShape());

        area2.intersect(area1);
        return area2.isEmpty();
    }

    public void move(){
        double newX;
        double newY;

        newX = Math.toDegrees(Math.cos(Math.toRadians(getMovingAngle())))*getMovingDistance()/Constants.SPEEDFACTOR;
        newX = newX + getObjectPosition().getX();
        newY = - Math.toDegrees(Math.sin(Math.toRadians(getMovingAngle())))*getMovingDistance()/Constants.SPEEDFACTOR;
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
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)getObjectPosition().getX(), (int)getObjectPosition().getY(), (int)getWidth(), (int)getHeight());
    }

}