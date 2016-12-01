package de.javavorlesung.pong;

import com.sun.javafx.scene.control.skin.VirtualFlow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.LinkedList;
import java.awt.FontMetrics;

public class GamePanel extends JPanel implements MouseMotionListener{

    private Integer highscore;
    private int basespeed;

    private Paddle paddle;
    private Gamearea gamearea;

    private List<Ball> gameBalls = new LinkedList<Ball>();
    private List<Ball> displayBalls = new LinkedList<Ball>();

    private final Dimension prefSize = new Dimension(Constants.XRESOLUTION, Constants.YRESOLUTION+60);

    private boolean gameOver = false;


    private Timer t;
    private Timer t1;


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
        basespeed = 10;
        highscore = 0;
        createGameObjects();


        t = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doOnTick();
            }
        });

        t1 = new Timer(9000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                newBall();
            }
        });

    }

    private void createGameObjects() {
        paddle = new Paddle(new Coordinate(100,100));
        gamearea = new Gamearea(new Coordinate(Constants.XRESOLUTION/2-720/2,
                                Constants.YRESOLUTION/2-720/2+Constants.PADDLEHEIGHT));
        gameBalls.clear();
        gameBalls.add(new Ball(basespeed));
        displayBalls.clear();

    }

    private void newBall(){
        if (gameOver == false) {
            basespeed = basespeed + 1;
            gameBalls.add(new Ball(basespeed));
        }
    }


    private void doOnTick(){
        paddle.move();
        moveBalls();

        System.out.println(gameBalls.toString());
        System.out.println(displayBalls.toString());
        repaint();
    }



    private void moveBalls(){
        //move Balls
        for (Ball s : gameBalls) {
            s.move();

            if(paddle.checkCollision(s)){
                paddle.bounce(s);
                calculateHighscore(s);
            }

            moveToDisplayBall(s);
        }

        if (gameBalls.isEmpty()){
            endGame();
        }

        for (Ball s : displayBalls) {
            s.move();
            removeBall(s);
        }
    }

    private void moveToDisplayBall(Ball s){
        if(!s.isStillinGame(gamearea)){
            Ball temp = gameBalls.remove(gameBalls.indexOf(s));
            displayBalls.add(temp);
        }
    }

    private void removeBall(Ball s){
        if ((s.getObjectPosition().getX() < 0) || (s.getObjectPosition().getX() > Constants.XRESOLUTION)) {
            displayBalls.remove(displayBalls.indexOf(s));
        }
        if ((s.getObjectPosition().getY() < 0) || (s.getObjectPosition().getY() > Constants.YRESOLUTION)) {
            displayBalls.remove(displayBalls.indexOf(s));
        }
    }

    private void calculateHighscore(Ball s){
        //calculate highscore
        Double temp = (highscore + gameBalls.size() * s.getMovingDistance());
        highscore = temp.intValue();

    }

    private void startGame() {
        t.start();
        t1.start();
    }

    public void pauseGame() {
        t.stop();
        t1.stop();
    }

    public void continueGame() {
        if (!isGameOver()){
            t.start();
            t1.start();
        }
    }

    public void restartGame() {
        pauseGame();
        setGameOver(false);
        createGameObjects();
        startGame();
    }

    private void endGame() {
        setGameOver(true);
    }




    public void mouseMoved(MouseEvent e) {
        paddle.setPosition(e);
    }

    public void mouseDragged(MouseEvent e){
        paddle.setPosition(e);
    }

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
        // Dispose the Graphics
        g.dispose();
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        gamearea.paintMe(g);
        paddle.paintMe(g);

        for (Ball s : gameBalls) {
            s.paintMe(g);
        }
        for (Ball s : displayBalls) {
            s.paintMe(g);
        }

        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        g.setColor(Color.GRAY);
        g.drawString(highscore.toString(),800,30);


        if (isGameOver()) {
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
            g.setColor(Color.BLACK);
            g.drawString("GAME OVER!", 500, 300);
            g.drawString(highscore.toString(), 500, 400);
        }

    }

}