package puzzle;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class ScoreBoard extends JPanel {
    private JLabel title;
    private JTextArea records;
    private JScrollPane pane;
    private List<Double> recordList;
    private int minMoveCount;
    private Lobby parent;
    private JDialog winDialog;
    private JLabel winLabel;
    private Cheat cheat;
    public ScoreBoard(Lobby lobby){
        parent = lobby;
        setLayout(new BorderLayout());
        title = new JLabel("-----ScoreBoard-----");
        this.add(title,BorderLayout.NORTH);
        records = new JTextArea();
        records.setEditable(false);
        records.setLineWrap(true);
        pane = new JScrollPane(records);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(pane,BorderLayout.CENTER);

        winDialog = new JDialog();
        winLabel = new JLabel();
        winLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winDialog.setLayout(new GridLayout(1,1));
        winDialog.add(winLabel);

        cheat=new Cheat(this);

        records.addMouseListener(cheat);
        recordList=new ArrayList<>();
        refreshFont();
    }
    public JTextArea getTextArea(){
        return records;
    }
    public Lobby getPar(){
        return parent;
    }
    public void refreshFont() {
        int fontSize = Math.min(parent.getWidth(), parent.getHeight())/30;
        title.setFont(new Font("Microsoft Yahei", Font.BOLD, fontSize));
        records.setFont(new Font("Microsoft Yahei", Font.BOLD, fontSize));
    }
    public Cheat getCheat(){
        return cheat;
    }

    public void win(int moveCount) {
        
        if(minMoveCount==-1){
            parent.waitStart();
            return;
        }
        recordAdd(minMoveCount*100.0/moveCount);
        
        winLabel.setText(String.format("YOU WIN! With A MoveCount Of %s!", moveCount));
        winLabel.setFont(new Font("Microsoft Yahei", Font.BOLD, Math.min(parent.getWidth(), parent.getHeight())/20));
        winDialog.setSize(parent.getWidth(),parent.getHeight()*2/9);
        winDialog.setLocationRelativeTo(parent);
        winDialog.setVisible(true);
    }
    public void recordAdd(Double score){
        int i=0;
        for(i=0;i<recordList.size();i++){
            if(score>recordList.get(i))break;
        }
        recordList.add(i,score);
        display();
    }
    public void display(){
        title.setForeground(Color.BLACK);
        int size = recordList.size();
        String[] strs = new String[size];
        for(int i=0;i<size;i++){
            strs[i]=String.format("%.4f",recordList.get(i));
        }
        records.setText(String.join("\n",strs));
    }

    public void setRecord(int solve) {
        minMoveCount=solve;
    }
    public JLabel getTitle() {
        return title;
    }
}
