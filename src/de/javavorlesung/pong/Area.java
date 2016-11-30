package de.javavorlesung.pong;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class Area extends GameObject {


    private Ellipse2D areaShape;

    public Area(Coordinate position) {
        super(position, 720, 720, 0, 0);
        setAreaShape();
    }

    private void setAreaShape(){
        areaShape = new Ellipse2D.Double(getObjectPosition().getX(),
                                         getObjectPosition().getY(),
                                         getWidth(),
                                         getHeight());
    }

    public Ellipse2D getAreaShape(){
        return areaShape;
    }



    @Override
    public void paintMe(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(200, 200, 200));
        g2d.fill(getAreaShape());
    }

}