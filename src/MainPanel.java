import Control.ControlPanel;
import Garage.Garage;
import Graph.GraphController;
import Graph.GraphModel;
import Graph.GraphView;
import Summary.SummaryController;
import Summary.SummaryModel;
import Summary.SummaryView;

import javax.swing.*;
import java.awt.*;

class MainPanel extends JPanel {
    private Dimension screenSize;
    ControlPanel controlPanel;

    void init() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);

        screenSize = SwingUtilities.getWindowAncestor(this).getSize();
//        System.out.println(dim);

        Garage garage = new Garage(3, 6, 30);
        garage.setBounds((screenSize.width / 3), 0, (screenSize.width / 3) * 2, ((screenSize.height / 4) * 2));
        add(garage);
        garage.init();

        GraphView graphView = new GraphView();
        GraphModel graphModel = new GraphModel(graphView, garage);
        GraphController graphController = new GraphController(graphModel, garage);
        graphController.init();
        graphView.init();
        add(graphView);


        controlPanel = new ControlPanel(garage, graphController);
        controlPanel.setLocation(0, 0);
        controlPanel.setSize((screenSize.width / 3), ((screenSize.height / 4) * 3));
        controlPanel.init();
        add(controlPanel);


        SummaryView summaryView = new SummaryView();
        SummaryModel summaryModel = new SummaryModel(summaryView);
        SummaryController summaryController = new SummaryController(summaryModel, garage);
        summaryView.setSize(screenSize.width - controlPanel.getWidth(), screenSize.height - garage.getHeight() - graphView.getHeight());
        summaryView.setLocation(controlPanel.getWidth(), garage.getHeight());
        add(summaryView);
        summaryView.init();
        summaryController.init();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        screenSize = SwingUtilities.getWindowAncestor(this).getSize();
        controlPanel.setSize((screenSize.width / 3), ((screenSize.height / 4) * 3));
    }

    @Override
    public void validate() {

        screenSize = SwingUtilities.getWindowAncestor(this).getSize();
        System.out.println(screenSize);
        super.validate();
    }
}
