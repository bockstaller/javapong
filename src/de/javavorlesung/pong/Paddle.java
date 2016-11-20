package de.javavorlesung.pong;

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

    private LinkedList<Integer> paddleElements = new LinkedList<Integer>();
    private int paddleStart;
    private int paddleExtend;


    public Paddle (Coordinate position, int paddleLength) {
        super(position, 0, 0);
        for (int i = 0; i<paddleLength; i++){
            paddleElements.add(0);
        }
    }

    public void shortenPaddle(int delta){
        for (int i = 0; i<delta; i++){
            paddleElements.removeLast();
        }
    }

    public void lengthenPaddle(int delta){
        for (int i = 0; i<delta; i++){
            paddleElements.add(paddleElements.getLast());
        }
    }

    public void setPosition(MouseEvent e){
        super.setObjectPosition(new Coordinate(e.getX(), e.getY()));
        //System.out.println(e.getX() + " " + e.getY());
    }

    public void move(){

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


        paddleStart=(int)angle;
        for(int sumElement: paddleElements) {
            paddleExtend += sumElement;
        }

        int paddleEndMinusOne= paddleStart+paddleExtend;
        if((angle-paddleEndMinusOne)>340){
            paddleElements.addLast((360-paddleEndMinusOne)+(int)angle);
        }else{
            paddleElements.addLast(paddleEndMinusOne-(int)angle);
        }
        paddleElements.removeFirst();


        System.out.println(paddleElements.toString());
    }


    @Override
    public void paintMe(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;


        g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
        g2d.setColor(Color.GRAY);

        String temp=String.valueOf(deltaX)+" "+String.valueOf(deltaY)+ " "+ angle;
        g2d.drawString(temp, (int)getObjectPosition().getX(), (int)getObjectPosition().getY());

        g2d.drawLine(1280/2, 720/2, (int)getObjectPosition().getX(), 720/2);
        g2d.drawLine(1280/2, (int)getObjectPosition().getY(), (int)getObjectPosition().getX(), (int)getObjectPosition().getY());
        g2d.drawLine(1280/2, 720/2, 1280/2, (int)getObjectPosition().getY());
        g2d.drawLine((int)getObjectPosition().getX(), 720/2, (int)getObjectPosition().getX(), (int)getObjectPosition().getY());

        Arc2D arc = new Arc2D.Double(280, 30, 720, 720, paddleStart, paddleExtend, Arc2D.OPEN);

        g2d.draw(arc);
    }

}