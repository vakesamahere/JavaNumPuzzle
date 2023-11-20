package puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
    private Integer n = 4;
    private Integer m = 4;
    private List<JButton> buttons;
    private List<Integer> panelData;
    private Lobby parent;
    private static final Color block = new Color(240, 240, 240);
    public GamePanel(Lobby lobby){
        parent=lobby;
        setLayout(new GridLayout(n,m));
        buttons=new ArrayList<>();
        for(int i=0;i<16;i++){
            buttons.add(new JButton());
        }
        for(JButton button:buttons){
            add(button);
            button.addActionListener(this);
            button.setEnabled(false);
            button.setBackground(Color.WHITE);
        }
        refreshFont();
    }
    public void initialize(){//生成随机排列
        panelData=new ArrayList<>();
        List<Integer> tempNums=new ArrayList<>();
        int size = n*m-1;
        for(int i=0;i<size;i++){
            tempNums.add(i+1);
        }
        Random ran = new Random();
        for(int i=0;i<size;i++){
            int x = ran.nextInt(tempNums.size());
            int num = tempNums.get(x);
            tempNums.remove((Integer)num);
            panelData.add(num);
        }
        int air = ran.nextInt(n*n);
        panelData.add(air, 0);
    }
    public boolean isSolvable(){//判断当前是否有解
        int size=panelData.size();
        int inv=0;
        for(int i=0;i<size;i++){//inv
            int xi=panelData.get(i);
            if(xi==0)continue;
            for(int j=i+1;j<size;j++){
                int xj=panelData.get(j);
                if(xj==0)continue;
                if(xi>xj)inv++;
            }
        }
        if(((m-1)%2==0)||(inv%2==(n-panelData.indexOf(0)/m+1)%2)){
            return true;
        }else{
            return false;
        }
    }
    public boolean isWin(){
        int size=panelData.size()-1;
        for(int i=0;i<size;i++){
            if(panelData.get(i)!=i+1)return false;
        }
        return true;
    }
    public void display(){
        int size=m*n;
        for(int i=0;i<size;i++){
            String displayString = Integer.toString(panelData.get(i));
            if(panelData.get(i)==0)displayString="";
            buttons.get(i).setText(displayString);
        }
    }
    public void click(int i){//move
        int airPos = panelData.indexOf(0);
        int rowDelta = Math.abs(i/m-airPos/m);
        int colDelta = Math.abs(i%m-airPos%m);
        if(!((rowDelta==1&&colDelta==0)||(rowDelta==0&&colDelta==1)))return;
        panelData.set(airPos,panelData.get(i));
        panelData.set(i,0);
        buttons.get(i).setEnabled(false);
        buttons.get(i).setBackground(Color.white);
        buttons.get(airPos).setEnabled(true);
        buttons.get(airPos).setBackground(block);
        display();
        parent.move();
        if(isWin())gameOver(true);
    }
    public void gameStart(){
        for(JButton button:buttons){
            if(button.getText()=="")continue;
            button.setEnabled(true);
            button.setBackground(block);
        }
    }
    public void gameOver(boolean isWin){
        parent.gameOver(isWin);
        for(JButton button:buttons){
            button.setEnabled(false);
            button.setBackground(Color.WHITE);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        click(buttons.indexOf(e.getSource()));
        display();
    }
    public void refreshFont() {
        int fontSize = Math.min(parent.getWidth(), parent.getHeight())/20;
        for(JButton button:buttons){
            button.setFont(new Font("Microsoft Yahei", Font.BOLD, fontSize));
        }
    }
}