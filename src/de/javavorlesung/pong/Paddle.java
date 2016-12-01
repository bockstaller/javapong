package de.javavorlesung.pong;

import java.awt.geom.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.Graphics;


public class Paddle extends GameObject {

    private double angle;
    private RoundRectangle2D paddle;
    private AffineTransform transformation = new AffineTransform();



    public Paddle (Coordinate position) {
        super(position, 0, 0, 0, 0);
        paddle = new RoundRectangle2D.Double(0, 0, Constants.PADDLEWIDTH, Constants.PADDLEHEIGHT, 10, 10);
    }

    public Shape getPaddle(){
        return transformation.createTransformedShape(paddle);
    }

    public void setPosition(MouseEvent e){
        super.setObjectPosition(new Coordinate(e.getX(), e.getY()));
    }

    private void getAngle(){
        double deltaX = getObjectPosition().getX()-(Constants.XRESOLUTION/2);
        double deltaY = (Constants.YRESOLUTION/2)-getObjectPosition().getY();

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
        transformation = new AffineTransform();
        // translation correction
        transformation.translate(Constants.XRESOLUTION/2-Constants.PADDLEWIDTH/2,
                                 Constants.YRESOLUTION/2+Constants.GAMEAREAWIDTH/2-Constants.PADDLEHEIGHT/2);
        // rotation
        transformation.rotate(  Math.toRadians(-angle),
                                Constants.PADDLEWIDTH/2,
                                -Constants.GAMEAREAWIDTH/2+Constants.PADDLEHEIGHT/2);

    }

    private double extractAngle()
    {
        Point2D p0 = new Point();
        Point2D p1 = new Point(1,0);
        Point2D pp0 = transformation.transform(p0, null);
        Point2D pp1 = transformation.transform(p1, null);
        double dx = pp1.getX() - pp0.getX();
        double dy = pp1.getY() - pp0.getY();
        double angle = Math.toDegrees(Math.atan2(dx, dy));
        angle = angle + 180;
        return angle;
    }


    public boolean checkCollision(Ball ball){
        Area tempArea1 = new Area(ball.getBallShape());
        Area tempArea2 = new Area(getPaddle());

        tempArea1.intersect(tempArea2);

        return !tempArea1.isEmpty();
    }




    public void bounce(Ball ball){
        double newBallAngle;
        //Drehung gegen den Uhrzeigersinn um Winkel zu normalisieren
        double paddleAngle = 0;
        //# sind beide Winkel gleich normiert?
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
        while(checkCollision(ball)){
            ball.move();
        }
        //increasing speed
        ball.setMovingDistance(ball.getMovingDistance()+Constants.COLLISIONACCELERATION);

    }


    @Override
    public void paintMe(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform saveTransform = g2d.getTransform();
        g2d.setColor(new Color(100, 100, 100));
        g2d.transform(transformation);
        g2d.fill(paddle);
        g2d.setTransform(saveTransform);
    }

}