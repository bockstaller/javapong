

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;



import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class GameWindow extends JFrame {

    private GamePanel pongGamePanel;

    public GameWindow() {

        this.pongGamePanel = new GamePanel();

    }

    public void startGame(){
        registerWindowListener();
        add(pongGamePanel);

        pack();

        setTitle("Pong");
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        pongGamePanel.start();

    }

    private void registerWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
            @Override
            public void windowDeactivated(WindowEvent e) {
                pongGamePanel.pauseGame();
            }
            @Override
            public void windowActivated(WindowEvent e) {
                pongGamePanel.continueGame();
            }
        });
    }






}
