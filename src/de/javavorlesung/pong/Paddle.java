package de.javavorlesung.pong;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.awt.geom.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.Graphics;


public class Paddle extends GameObject {


    private AffineTransform transformation;
    private double angletemp;


    public Paddle (Coordinate position) {
        super(position, 0, 0, 0, 0);

        RoundRectangle2D temp = new RoundRectangle2D.Double(0, 0, Constants.PADDLEWIDTH, Constants.PADDLEHEIGHT, 10, 10);

        // translation correction
        angletemp = getAngle();
        transformation = new AffineTransform();
        transformation.translate(   Constants.XRESOLUTION/2-Constants.PADDLEWIDTH/2,
                                    Constants.YRESOLUTION/2+Constants.GAMEAREAWIDTH/2-Constants.PADDLEHEIGHT/2);
        setShape(transformation.createTransformedShape(temp));

        // correcting the backrotation
        transformation = new AffineTransform();
        transformation.rotate(  Math.toRadians(-angletemp),
                                Constants.XRESOLUTION/2,
                                Constants.YRESOLUTION/2);

        setShape(transformation.createTransformedShape(getShape()));
    }


    public void setPosition(MouseEvent e){
        super.setObjectPosition(new Coordinate(e.getX(), e.getY()));
    }

    private double getAngle(){
        double deltaX = getObjectPosition().getX()-(Constants.XRESOLUTION/2);
        double deltaY = (Constants.YRESOLUTION/2)-getObjectPosition().getY();
        double angle = 0;
        if((deltaX >= 0)&&(deltaY >= 0)){
            angle = Math.toDegrees(Math.atan(deltaY/deltaX));
        }else if((deltaX >= 0)&&(deltaY < 0)){
            angle = 360+Math.toDegrees(Math.atan(deltaY/deltaX));
        }else if((deltaX < 0)&&(deltaY >= 0)) {
            angle = (90+Math.toDegrees(Math.atan(deltaY/deltaX)))+90;
        }else{
            angle = Math.toDegrees(Math.atan(deltaY/deltaX))+180;
        }
        return angle+90;
    }

    public void move(){
        //rotating back to start
        transformation = new AffineTransform();
        transformation.rotate(  Math.toRadians(+angletemp),
                Constants.XRESOLUTION/2,
                Constants.YRESOLUTION/2);
        setShape(transformation.createTransformedShape(getShape()));

        angletemp= getAngle();

        //rotating to position
        transformation = new AffineTransform();
        transformation.rotate(  Math.toRadians(-angletemp),
                Constants.XRESOLUTION/2,
                Constants.YRESOLUTION/2);
        setShape(transformation.createTransformedShape(getShape()));

    }



    private double extractAngle() {
        Point2D p0 = new Point();
        Point2D p1 = new Point(1,0);
        Point2D pp0 = transformation.transform(p0, null);
        Point2D pp1 = transformation.transform(p1, null);
        double dx = pp1.getX() - pp0.getX();
        double dy = pp1.getY() - pp0.getY();
        double extractedAngle = Math.toDegrees(Math.atan2(dx, dy));
        extractedAngle = extractedAngle + 180;
        return extractedAngle;
    }



    public void bounce(Ball ball, long delta){
        double newBallAngle;
        //Drehung gegen den Uhrzeigersinn um Winkel zu normalisieren
        double paddleAngle = 0;

        double ballAngle = (ball.getMovingAngle()-extractAngle());


        if(360<ballAngle){
            ballAngle = ballAngle-360;
        } else if (ballAngle<0){
            ballAngle = ballAngle +360;
        }


        if ((0<ballAngle)&&(ballAngle<90)){
            newBallAngle = 180 - ballAngle;
            //System.out.println("1 " +ball.getMovingAngle() + " - " + extractAngle() + " = "+ ballAngle + " => " + newBallAngle);
        } else if ((90<ballAngle)&&(ballAngle<180)){
            newBallAngle = 180 - ballAngle;
            //System.out.println("2 " +ball.getMovingAngle() + " - " + extractAngle() + " = "+ ballAngle + " => " + newBallAngle);
        } else if ((180<ballAngle)&&(ballAngle<270)){
            newBallAngle = 360-(ballAngle-180);
            //System.out.println("3 " +ball.getMovingAngle() + " - " + extractAngle() + " = "+ ballAngle + " => " + newBallAngle);
        } else if ((270<ballAngle)&&(ballAngle<360)){
            newBallAngle = (360 - ballAngle)+180;
            //System.out.println("4 " +ball.getMovingAngle() + " - " + extractAngle() + " = "+ ballAngle + " => " + newBallAngle);
        } else if (ballAngle==0){
            newBallAngle = 180;
            //System.out.println("5 " +ball.getMovingAngle() + " - " + extractAngle() + " = "+ ballAngle );
        } else if (ballAngle==90){
            newBallAngle = 270;
            //System.out.println("6 " +ball.getMovingAngle() + " - " + extractAngle() + " = "+ ballAngle );
        } else if (ballAngle==180) {
            newBallAngle = 0;
            //System.out.println("7 " +ball.getMovingAngle() + " - " + extractAngle() + " = "+ ballAngle );
        } else {
            newBallAngle = 90;
            //System.out.println("8 " +ball.getMovingAngle() + " - " + extractAngle() + " = "+ ballAngle );
        }

        //reverting the rotation
        newBallAngle = Math.abs(newBallAngle + extractAngle());
        ball.setMovingAngle(newBallAngle);

        //clearing ball from paddle
        while(checkCollision(ball.getShape())){
            ball.move(delta);
        }
        //increasing speed
        ball.setMovingDistance(ball.getMovingDistance()+Constants.COLLISIONACCELERATION);

    }


    @Override
    public void paintMe(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        //Puffer for transformation
        AffineTransform saveTransform = g2d.getTransform();

        g2d.setColor(new Color(100, 100, 100));
        g2d.fill(getShape());

        //Puffer for transformation
        g2d.setTransform(saveTransform);
    }

}