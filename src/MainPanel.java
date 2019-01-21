import Control.ControlPanel;
import Garage.GarageController;
import Garage.GarageModel;
import Garage.GarageView;
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
    ControlPanel controlPanel;

    ArrayList<JPanel> panels = new ArrayList<>();

    void init() {
        setLayout(null);
        setBackground(new Color(55, 57, 63));


        GarageView garageView = new GarageView();
        garageView.setBounds(500, 25, 900, 425);
        GarageModel garageModel = new GarageModel(garageView);

        GarageController garageController = new GarageController(garageModel);
        garageModel.setController(garageController);
        garageController.init();
        garageModel.init();
        add(garageView);
        garageView.setVisible(true);


        GraphView graphView = new GraphView();
        GraphModel graphModel = new GraphModel(graphView, garageModel);
        GraphController graphController = new GraphController(graphModel, garageModel);
        graphController.init();
        graphView.init();
        add(graphView);


        controlPanel = new ControlPanel(garageModel, graphController);

        controlPanel.setLocation(25, 25);
        controlPanel.setSize(450, 700);
        controlPanel.init();
        add(controlPanel);
        panels.add(controlPanel);


        SummaryView summaryView = new SummaryView();
        SummaryModel summaryModel = new SummaryModel(summaryView);
        SummaryController summaryController = new SummaryController(summaryModel, garageModel);


        int height = controlPanel.getHeight() - garageView.getHeight() - garageView.getY();


        summaryView.setSize(garageView.getWidth(), height);
        summaryView.setLocation(controlPanel.getWidth() + 50, garageView.getHeight() + 50);
        add(summaryView);
        summaryView.init();
        summaryController.init();
        panels.add(summaryView);


    }

}
