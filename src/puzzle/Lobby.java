package puzzle;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

public class Lobby extends JFrame implements ActionListener {
    private final static String resetText= "Reset";
    private final static String iniText= "Initialize";
    private final static String title= "NumPuzzle";
    private GamePanel gamePanel;
    private ScoreBoard scoreBoard;
    private JPanel buttonPanel;
    private JButton iniButton;
    private JButton startButton;
    private JDialog waitingDialog;
    private JLabel waitingLabel;

    private int moveCount;
    private boolean waiting;

    private boolean cheating=false;

    public Lobby(){
        setLayout(new BorderLayout());
        setTitle(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,600);
        this.setLocationRelativeTo(null);
        gamePanel=new GamePanel(this);
        add(gamePanel,BorderLayout.CENTER);
        scoreBoard=new ScoreBoard(this);
        add(scoreBoard,BorderLayout.WEST);

        buttonPanel=new JPanel(new GridLayout(2,1));
        add(buttonPanel,BorderLayout.EAST);
        iniButton=new JButton(iniText);
        buttonPanel.add(iniButton);
        startButton=new JButton("Start");
        buttonPanel.add(startButton);
        startButton.setEnabled(false);

        iniButton.addActionListener(this);
        startButton.addActionListener(this);

        waitingDialog = new JDialog();
        waitingLabel = new JLabel();
        waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        waitingDialog.setLayout(new GridLayout(1,1));
        waitingDialog.add(waitingLabel);

        refreshFont();

        this.setVisible(true);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refreshFont();
                gamePanel.refreshFont();
                scoreBoard.refreshFont();
            }
        });
    }
    public ScoreBoard getScoreBoard(){
        return scoreBoard;
    }
    public void refreshFont() {
        int fontSize = Math.min(getWidth(), getHeight())/40;
        iniButton.setFont(new Font("Microsoft Yahei", Font.BOLD, fontSize));
        startButton.setFont(new Font("Microsoft Yahei", Font.BOLD, fontSize));
    }
    public int getMoveCount(){
        return moveCount;
    }
    public void waitStart(){
        waiting=true;
        waitingLabel.setText(String.format("YOU WIN! With A MoveCount Of %s!\nBUT WAITTING FOR THE SCORE CALCULATION...", moveCount));
        waitingLabel.setFont(new Font("Microsoft Yahei", Font.BOLD, Math.min(getWidth(), getHeight())/25));
        waitingDialog.setSize(getWidth(),getHeight()*2/9);
        waitingDialog.setLocationRelativeTo(this);
        waitingDialog.setVisible(true);
    }
    public void waitEnd(){
        waiting=false;
        waitingDialog.setVisible(false);
    }
    public boolean isWaiting(){
        return waiting;
    }
    public void gameStart(){
        waiting=false;
        moveCount=0;
        startButton.setEnabled(false);
        gamePanel.gameStart();
        iniButton.setText(resetText);
        setTitle(String.format("%s||MoveCount:%s", title,moveCount));
    }
    public void move(){
        moveCount++;
        setTitle(String.format("%s||MoveCount:%s", title,moveCount));
        if(cheating){
            scoreBoard.getTextArea().setSelectionStart(0);
            scoreBoard.getTextArea().setSelectionEnd(moveCount*2);
        }
    }
    public void gameOver(boolean isWin){
        cheating=false;
        if(isWin)scoreBoard.win(moveCount);
        iniButton.setText(iniText);
        setTitle(title);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==iniButton){
            scoreBoard.getCheat().setText(null);
            gamePanel.gameOver(false);
            gamePanel.initialize();
            gamePanel.display();
            if(gamePanel.isSolvable()){
                startButton.setEnabled(true);
            }else{
                startButton.setEnabled(false);
            }
        }
        if(e.getSource()==startButton){
            gameStart();
        }
    }
    public void cheatStart() {
        cheating=true;
    }
}
