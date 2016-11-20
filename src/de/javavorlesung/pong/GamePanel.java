package de.javavorlesung.pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel implements MouseMotionListener{

    private Paddle testPaddle;

    public static final String IMAGE_DIR = "images/";

    private final Dimension prefSize = new Dimension(1180, 780);

    private ImageIcon backgroundImage;
    //private final String[] backgroundImages= new String [] {"bg_mud.jpg",
    //        "bg_snow.jpg",
    //       "bg_sand.jpg"};

    private boolean gameOver = false;


    private Timer t;


    public GamePanel() {
        setFocusable(true);
        setPreferredSize(prefSize);

        initGame();
        startGame();

        addMouseMotionListener(this);
    }


    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    private void initGame () {
        //setBackgroundImage(1);
        createGameObjects();

        t = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doOnTick();
            }
        });
    }

    private void createGameObjects() {
        testPaddle = new Paddle(new Coordinate(100,100), 20);
    }

    private void initPlayersTank() {
        // hier werden wir den Panzer des Spielers initialisieren
    }

    /**public void setBackgroundImage(int imageNumber) {
        String imagePath = IMAGE_DIR + backgroundImages[imageNumber];
        URL imageURL = getClass().getResource(imagePath);
        backgroundImage = new ImageIcon(imageURL);
    }**/

    private void startGame() {
        t.start();
    }

    public void pauseGame() {
        t.stop();
    }

    public void continueGame() {
        if (!isGameOver()) t.start();
    }

    public void restartGame() {
        //tanksDestroyedCounter = 0;
        setGameOver(false);
        createGameObjects();
        startGame();
    }

    private void endGame() {
        setGameOver(true);
        pauseGame();
    }

    private void doOnTick(){
        testPaddle.move();
        repaint();
    }


    public void mouseMoved(MouseEvent e) {
        testPaddle.setPosition(e);
    }

    public void mouseDragged(MouseEvent e) {
        testPaddle.setPosition(e);
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //backgroundImage.paintIcon(null, g, 0, 0);

        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 19));
        g.setColor(Color.BLUE);
        //g.drawString("Tanks destroyed: " + tanksDestroyedCounter, 22, prefSize.height-5);



        if (isGameOver()) {
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
            g.setColor(Color.RED);
            g.drawString("GAME OVER!", 12, 12);
        }
        testPaddle.paintMe(g);
    }

}