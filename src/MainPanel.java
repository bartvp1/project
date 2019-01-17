import Control.ControlPanel;
import Garage.Garage;
import Graph.GraphController;
import Graph.GraphModel;
import Graph.GraphView;

import javax.swing.*;
import java.awt.*;

class MainPanel extends JPanel {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    void init() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);
        
        Garage garage = new Garage(3, 6, 30);
        garage.setBounds((screenSize.width / 3), 0, (screenSize.width / 3) * 2, ((screenSize.height / 4) * 3));
        add(garage);
        garage.init();

        GraphView graphView = new GraphView();
        GraphModel graphModel = new GraphModel(graphView, garage);
        GraphController graphController = new GraphController(graphModel, garage);
        graphController.init();
        graphView.init();
        add(graphView);


        ControlPanel controlPanel = new ControlPanel(garage, graphController);
        controlPanel.init();
        add(controlPanel);
        controlPanel.setLocation(0, 0);


    }

}
