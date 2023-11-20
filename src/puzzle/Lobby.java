package puzzle;

import java.awt.BorderLayout;

import javax.swing.*;

public class Lobby extends JFrame{
    private GamePanel gamePanel;
    public Lobby(){
        setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        gamePanel=new GamePanel();
        add(gamePanel);
        this.setVisible(true);
    }
    public static void main(String[] args){
        
    }
}
