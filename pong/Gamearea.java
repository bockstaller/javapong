
import java.awt.*;

import java.awt.geom.Ellipse2D;

public class Gamearea extends GameObject {

    public Gamearea(Coordinate position) {
        super(position, Constants.GAMEAREAWIDTH, Constants.GAMEAREAWIDTH, 0, 0);
        setGameareaShape();
    }

    private void setGameareaShape(){
        super.setShape(new Ellipse2D.Double(getObjectPosition().getX(),
                                         getObjectPosition().getY(),
                                         getWidth(),
                                         getHeight()));
    }

    @Override
    public void paintMe(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(200, 200, 200));
        g2d.fill(getShape());
    }

}
