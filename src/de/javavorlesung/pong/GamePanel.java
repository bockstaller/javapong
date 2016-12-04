package de.javavorlesung.pong;


import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.List;
import java.util.LinkedList;

public class GamePanel extends JPanel implements KeyListener, MouseMotionListener{

    //Gameproperties
    private Integer highscore;
    private double basespeed;
    private boolean gameOver = false;
    private long time=System.currentTimeMillis();
    private long delta;

    //Gameobjects
    private Paddle paddle;
    private Gamearea gamearea;
    private List<Ball> gameBalls = new LinkedList<>();
    private List<Ball> displayBalls = new LinkedList<>();
    private List<Ball> temp = new LinkedList<>();

    private final Dimension prefSize = new Dimension(Constants.XRESOLUTION, Constants.YRESOLUTION);

    private Timer t;
    private Timer t1;



    public GamePanel(){
        setFocusable(true);
        setPreferredSize(prefSize);
        addMouseMotionListener(this);
        addKeyListener(this);
    }

    public void start(){
        initGame();
        startGame();
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


        t1 = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                newBall();
            }
        });

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
        if (!getGameOver()){
            startGame();
        }
    }

    private void endGame() {
        gameOver=true;
    }

    private boolean getGameOver(){
        return gameOver;
    }

    private void setGameOver(boolean gameOver){
        this.gameOver=gameOver;
    }

    public void restartGame(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }else {
            pauseGame();
            setGameOver(false);
            createGameObjects();
            start();
        }
    }

    private void createGameObjects() {
        paddle = new Paddle(new Coordinate(100,100));
        gamearea = new Gamearea(new Coordinate(Constants.XRESOLUTION/2-Constants.GAMEAREAWIDTH/2,
                                                Constants.YRESOLUTION/2-Constants.GAMEAREAWIDTH/2));
        gameBalls.clear();
        gameBalls.add(new Ball(basespeed));
        displayBalls.clear();

    }

    private void newBall(){
        if (!getGameOver()) {
            basespeed = basespeed + Constants.NEWBALLACCELERATION;
            gameBalls.add(new Ball(basespeed));
        }
    }

    private void determineTime(){
        delta = System.currentTimeMillis()-time;
        time = System.currentTimeMillis();
    }

    private void doOnTick(){
        determineTime();
        paddle.move();
        moveBalls();
        repaint();
    }


    private void moveBalls(){
        //move game balls
        for (Ball s : gameBalls) {
            s.move(delta);
            if(paddle.checkCollision(s.getShape())){
                paddle.bounce(s, delta);
                calculateHighscore(s);
            }

            //collide balls
            List<Ball> tempList = new LinkedList<>(gameBalls);
            tempList.remove(s);
            if (!(tempList.isEmpty())) {
                for (Ball t : tempList) {
                    if (s.checkCollision(t.getShape())) {
                        s.collide(t, delta);
                    }
                }
            }
        }

        for (Ball s : gameBalls) {
            moveToDisplayBall(s);
        }
        gameBalls.removeAll(temp);
        temp.clear();


        if (gameBalls.isEmpty()){
            endGame();
        }

        for (Ball s : displayBalls) {
            s.move(delta);
        }


        if (!displayBalls.isEmpty()) {
            temp.clear();
            for (Ball s : displayBalls) {
                removeBall(s);
            }
            displayBalls.removeAll(temp);
            temp.clear();
        }

    }

    private void moveToDisplayBall(Ball s){
        if(!s.checkCollision(gamearea.getShape())){
            temp.add(s);
            displayBalls.add(s);
        }
    }

    private void removeBall(Ball s){
        if ((s.getObjectPosition().getX() < 0) || (s.getObjectPosition().getX() > Constants.XRESOLUTION)) {
            temp.add(s);
        }
        if ((s.getObjectPosition().getY() < 0) || (s.getObjectPosition().getY() > Constants.YRESOLUTION)) {
            temp.add(s);
        }
    }

    private void calculateHighscore(Ball s){
        //calculate highscore
        Double temp = (highscore + gameBalls.size() * s.getMovingDistance());
        highscore = temp.intValue();

    }



    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font, int max) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        int width = metrics.stringWidth(text);

        while (max < width) {
            Font font1 = font.deriveFont((float)font.getSize() - 10);
            font = font1;
            g.setFont(font);
            metrics = g.getFontMetrics(font);
            width = metrics.stringWidth(text);
        }

        // Determine the X coordinate for the text
        int x = (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);

    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        gamearea.paintMe(g);


        if (getGameOver()) {
            g.setFont(  new Font(Font.MONOSPACED,
                    Font.BOLD,
                    80));
            g.setColor(Color.BLACK);
            drawCenteredString( g,
                    "GAME OVER",
                    new Rectangle(0, 0, Constants.XRESOLUTION, Constants.YRESOLUTION-200),
                    g.getFont(),
                    Constants.GAMEAREAWIDTH);
            drawCenteredString( g,
                    "Highscore: " + highscore.toString(),
                    new Rectangle(0, 0, Constants.XRESOLUTION, Constants.YRESOLUTION),
                    g.getFont(),
                    Constants.GAMEAREAWIDTH);
            drawCenteredString( g,
                    "Press a Key to restart",
                    new Rectangle(0, 0, Constants.XRESOLUTION, Constants.YRESOLUTION+200),
                    g.getFont(),
                    Constants.GAMEAREAWIDTH);
            drawCenteredString( g,
                    "or ESC to leave",
                    new Rectangle(0, 0, Constants.XRESOLUTION, Constants.YRESOLUTION+300),
                    g.getFont(),
                    Constants.GAMEAREAWIDTH);
        } else {
            g.setFont(  new Font(Font.MONOSPACED,
                    Font.BOLD,
                    300));
            g.setColor(new Color(230,230,230));
            drawCenteredString( g,
                    highscore.toString(),
                    new Rectangle(0, 0, Constants.XRESOLUTION, Constants.YRESOLUTION),
                    g.getFont(),
                    Constants.GAMEAREAWIDTH);
        }

        paddle.paintMe(g);
        for (Ball s : gameBalls) {
            s.paintMe(g);
        }
        for (Ball s : displayBalls) {
            s.paintMe(g);
        }

    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            restartGame(e);
        }
        if (t1.isRunning()){
            pauseGame();
        } else {
            continueGame();
        }
    }

    @Override
    public  void keyReleased(KeyEvent e){}

    @Override
    public  void keyTyped(KeyEvent e){}

    public void mouseMoved(MouseEvent e) {
        paddle.setPosition(e);
    }

    public void mouseDragged(MouseEvent e){
        paddle.setPosition(e);
    }
}