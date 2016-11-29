package de.javavorlesung.pong;

import java.math.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Arc2D;
import java.awt.Graphics;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.LinkedList;

public class Paddle extends GameObject {

    private double angle;
    private double deltaX;
    private double deltaY;

    private BufferedImage paddle = null;



    public Paddle (Coordinate position) {
        super(position, 0, 0);

        try {
            paddle = ImageIO.read(new File("src/images/paddle.png"));
        } catch (IOException e) {
        }
    }


    public void setPosition(MouseEvent e){
        super.setObjectPosition(new Coordinate(e.getX(), e.getY()));
    }

    private void getAngle(){
        deltaX = getObjectPosition().getX()-(1280/2);
        deltaY = (720/2)-getObjectPosition().getY();

        if((deltaX >= 0)&&(deltaY >= 0)){
            angle = Math.toDegrees(Math.atan(deltaY/deltaX));
        }else if((deltaX >= 0)&&(deltaY < 0)){
            angle = 360+Math.toDegrees(Math.atan(deltaY/deltaX));
        }else if((deltaX < 0)&&(deltaY >= 0)) {
            angle = (90+Math.toDegrees(Math.atan(deltaY/deltaX)))+90;
        }else{
            angle = Math.toDegrees(Math.atan(deltaY/deltaX))+180;
        }
        angle = angle + 90;
    }

    public void move(){
        getAngle();


    }


    @Override
    public void paintMe(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform transformation = new AffineTransform();
        transformation.translate(1280/2-75, 720+15);
        transformation.rotate(Math.toRadians(-angle), 75, -720/2+15);

        g2d.drawImage(paddle, transformation, null);


        g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
        g2d.setColor(Color.GRAY);

        String temp=String.valueOf(deltaX)+" "+String.valueOf(deltaY)+ " "+ angle;
        g2d.drawString(temp, (int)getObjectPosition().getX(), (int)getObjectPosition().getY());

        g2d.drawLine(1280/2, 720/2, (int)getObjectPosition().getX(), 720/2);
        g2d.drawLine(120/2, (int)getObjectPosition().getY(), (int)getObjectPosition().getX(), (int)getObjectPosition().getY());
        g2d.drawLine(1280/2, 720/2, 1280/2, (int)getObjectPosition().getY());
        g2d.drawLine((int)getObjectPosition().getX(), 720/2, (int)getObjectPosition().getX(), (int)getObjectPosition().getY());





    }

}