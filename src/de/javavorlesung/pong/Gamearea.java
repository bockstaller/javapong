package de.javavorlesung.pong;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Gamearea extends GameObject {


    private Ellipse2D areaShape;

    public Gamearea(Coordinate position) {
        super(position, Constants.GAMEAREAWIDTH, Constants.GAMEAREAWIDTH, 0, 0);
        setGameareaShape();
    }

    private void setGameareaShape(){
        areaShape = new Ellipse2D.Double(getObjectPosition().getX(),
                                         getObjectPosition().getY(),
                                         getWidth(),
                                         getHeight());
    }

    public Ellipse2D getGameareaShape(){
        return areaShape;
    }



    @Override
    public void paintMe(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(200, 200, 200));
        g2d.fill(getGameareaShape());
    }

}