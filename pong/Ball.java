import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Ball extends GameObject {
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
        super.setShape(new Ellipse2D.Double(getObjectPosition().getX(),
                                         getObjectPosition().getY(),
                                         getWidth(),
                                         getHeight()));
    }


    public void move(long delta){
        double newX;
        double newY;

        newX = Math.toDegrees(Math.cos(Math.toRadians(getMovingAngle())))*(getMovingDistance()/(Constants.SPEEDFACTOR*delta));
        newX = newX + getObjectPosition().getX();
        newY = - Math.toDegrees(Math.sin(Math.toRadians(getMovingAngle())))*(getMovingDistance()/(Constants.SPEEDFACTOR*delta));
        newY = newY + getObjectPosition().getY();
        setBallShape(new Coordinate( newX, newY));
    }

    public void collide(Ball ball, long delta){
        double temp = ball.getMovingAngle();
        ball.setMovingAngle(getMovingAngle());
        setMovingAngle(temp);
        //clearing ball from ball
        while(checkCollision(ball.getShape())){
            ball.move(delta);
            move(delta);
        }
    }

    @Override
    public void paintMe(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)getObjectPosition().getX(), (int)getObjectPosition().getY(), (int)getWidth(), (int)getHeight());
    }

}
