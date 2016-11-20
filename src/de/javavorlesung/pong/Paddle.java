package de.javavorlesung.pong;

import com.sun.deploy.util.ArrayUtil;
import javafx.scene.shape.Polyline;

import java.math.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Arc2D;
import java.awt.Graphics;
import java.util.LinkedList;

public class Paddle extends GameObject {

    private double angle;
    private double deltaX;
    private double deltaY;

    private int maxLength;
    private int currentLength;


    private LinkedList<Coordinate> paddleSegments = new LinkedList<Coordinate>();

    private int[] xArray = new int[maxLength];
    private int[] yArray = new int[maxLength];

    public Paddle (Coordinate position, int maxPaddleLength) {
        super(position, 0, 0);
        maxLength=maxPaddleLength;
        currentLength=maxPaddleLength;
        for (int i = 0; i<maxLength; i++){
            paddleSegments.addLast(position);
        }
    }

    public int getCurrentLength(){
        return currentLength;
    }

    public LinkedList<Coordinate> getPaddle(){
        return paddleSegments;
    }

    public void shortenPaddle(int delta){
        for (int i = 0; i<delta; i++){
            paddleSegments.removeLast();
            currentLength--;

        }
    }

    public void lengthenPaddle(int delta){
        for (int i = 0; i<delta; i++){
            paddleSegments.add(paddleSegments.getLast());
            currentLength++;
        }
    }

    public void setPosition(MouseEvent e){
        super.setObjectPosition(new Coordinate(e.getX(), e.getY()));
        //System.out.println(e.getX() + " " + e.getY());
    }

    public void move(){
        paddleSegments.addLast(new Coordinate(getObjectPosition().getX(),getObjectPosition().getY()));
        paddleSegments.removeFirst();

    }


    @Override
    public void paintMe(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;


         /*
        g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
        g2d.setColor(Color.GRAY);


        String temp=String.valueOf(deltaX)+" "+String.valueOf(deltaY)+ " "+ angle;
        g2d.drawString(temp, (int)getObjectPosition().getX(), (int)getObjectPosition().getY());


        g2d.drawLine(1280/2, 720/2, (int)getObjectPosition().getX(), 720/2);
        g2d.drawLine(1280/2, (int)getObjectPosition().getY(), (int)getObjectPosition().getX(), (int)getObjectPosition().getY());
        g2d.drawLine(1280/2, 720/2, 1280/2, (int)getObjectPosition().getY());
        g2d.drawLine((int)getObjectPosition().getX(), 720/2, (int)getObjectPosition().getX(), (int)getObjectPosition().getY());
         */

        Coordinate temp=null;
        for (Coordinate s: paddleSegments) {
            if(temp == null){
                temp = s;
            }else{
                g2d.drawLine(temp.getX(), temp.getY(), s.getX(), s.getY());
                temp = s;
            }


        }







    }

}