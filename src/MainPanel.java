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
import java.util.ArrayList;

class MainPanel extends JPanel {
    private Dimension screenSize = getToolkit().getScreenSize();
    ControlPanel controlPanel;

    ArrayList<JPanel> panels = new ArrayList<>();

    void init() {
        setLayout(null);
        setBackground(Color.DARK_GRAY);

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
        controlPanel.setSize(screenSize);
        controlPanel.init();
        add(controlPanel);
        panels.add(controlPanel);


        SummaryView summaryView = new SummaryView();
        SummaryModel summaryModel = new SummaryModel(summaryView);
        SummaryController summaryController = new SummaryController(summaryModel, garage);
        summaryView.setSize(screenSize.width - controlPanel.getWidth(), screenSize.height - garage.getHeight() - graphView.getHeight());
        summaryView.setLocation(controlPanel.getWidth(), garage.getHeight());
        add(summaryView);
        summaryView.init();
        summaryController.init();
        panels.add(summaryView);
    }


    @Override
    public void validate() {

        screenSize = SwingUtilities.getWindowAncestor(this).getSize();
        System.out.println(screenSize);
        super.validate();
    }
}
