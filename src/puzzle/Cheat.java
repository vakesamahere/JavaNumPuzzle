package puzzle;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
public class Cheat extends MouseAdapter{
    private ScoreBoard parent;
    private JDialog cheatDialog;
    private JButton cheatButton;
    private String cheatText;
    public Cheat(ScoreBoard s){
        parent=s;
        cheatDialog = new JDialog();
        cheatButton = new JButton();
        cheatButton.setHorizontalAlignment(SwingConstants.CENTER);
        cheatDialog.setLayout(new GridLayout(1,1));
        cheatDialog.add(cheatButton);
        cheatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cheatText==null)return;
                cheatStart();
            }
        });
    }
    private void cheatStart() {
        JTextArea t = parent.getTextArea();
        t.setText(cheatText);
        t.setSelectionStart(0);
        parent.getPar().cheatStart();
    }
    @Override
    public void mouseClicked(MouseEvent e) {    
        if(parent.getPar().getMoveCount()!=0)return;
        cheatButton.setText("CHEAT");
        cheatButton.setFont(new Font("Microsoft Yahei", Font.BOLD, Math.min(parent.getWidth(), parent.getHeight())/8));
        cheatDialog.setSize(parent.getWidth(),parent.getHeight()*2/9);
        cheatDialog.setLocationRelativeTo(parent);
        cheatDialog.setVisible(true);
        
    }

    public void setText(String text) {
        cheatText=text;
    }
}
