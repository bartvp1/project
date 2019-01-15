package Control;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ControlPanel extends JPanel {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JLabel titleLabel = new JLabel("Control Panel", JLabel.CENTER);

    public void init(){
        setPreferredSize(new Dimension(200,500));
        setSize((screenSize.width/3),((screenSize.height / 4)*3));
        setBorder(new LineBorder(Color.BLACK, 3, true));
        setBackground(Color.DARK_GRAY);

        titleLabel.setBounds(0,0,getWidth(), 50);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(titleLabel);
    }

}
