package de.javavorlesung.pong;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Ball extends GameObject {


    private Ellipse2D ballShape;


    public Ball(Coordinate position) {
        super(new Coordinate(Constants.XRESOLUTION/2+10/2, Constants.YRESOLUTION/2+10/2),
                             10,
                             10,
                             10,
                             new Random().nextDouble()*360);

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

    public boolean isStillinGame(Area area){

    return false;
    }



    public void move(){



    }




    @Override
    public void paintMe(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawOval((int)getObjectPosition().getX(), (int)getObjectPosition().getY(), (int)getWidth(), (int)getHeight());
    }

}