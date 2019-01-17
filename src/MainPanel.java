import Control.ControlPanel;
import Garage.Garage;
import Graph.GraphMainPanel;
import Graph.GraphView;

import javax.swing.*;
import java.awt.*;

class MainPanel extends JPanel {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    JButton exitButton = new JButton("Exit");
    GraphMainPanel gp;

    void init() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);


        exitButton.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 100, 10, 90, 50);
        add(exitButton);
        exitButton.addActionListener(e -> System.exit(0));

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Garage garage = new Garage(3, 6, 30);
        garage.setBounds((screenSize.width / 3), 0, (screenSize.width / 3) * 2, ((screenSize.height / 4) * 3));
        add(garage);
        garage.init();


        gp = new GraphMainPanel(garage);
        gp.setBounds(0, ((screenSize.height / 4) * 3), screenSize.width, (screenSize.height / 4));
        add(gp);
        gp.init();


        ControlPanel controlPanel = new ControlPanel(garage, gp);
        controlPanel.init();
        add(controlPanel);
        controlPanel.setLocation(0, 0);


    }

}
