package puzzle;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel{
    private Integer n = 4;
    private Integer m = 4;
    private List<JButton> buttons = new ArrayList<>();
    private List<Integer> panelData;
    public GamePanel(){
        setLayout(new GridLayout(n,m));
        for(int i=0;i<16;i++){
            buttons.add(new JButton());
        }
        for(JButton button:buttons){
            add(button);
        }
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
            tempNums.remove(num);
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
        if((inv%2==(m-1)%2)||(inv%2==(n-panelData.indexOf(0)/m+1)%2)){
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
}