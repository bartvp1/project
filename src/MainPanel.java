import CarGraph.GraphController;
import CarGraph.GraphModel;
import CarGraph.GraphView;
import Control.ControlPanel;
import Garage.FinancesController;
import Garage.GarageController;
import Garage.GarageModel;
import Garage.GarageView;
import QueuesSummary.QueueSummaryModel;
import QueuesSummary.QueueSummaryView;
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

        FinancesController financeController = new FinancesController();

        GraphView graphView = new GraphView();
        GraphModel graphModel = new GraphModel(graphView, garageModel);
        GraphController graphController = new GraphController(graphModel);
        graphController.setGarage(garageModel);
        graphController.init();
        graphView.init();
        add(graphView);


        controlPanel = new ControlPanel(garageModel, financeController, graphController);

        controlPanel.setLocation(25, 25);
        controlPanel.setSize(450, 700);
        controlPanel.init();
        add(controlPanel);
        panels.add(controlPanel);


        QueueSummaryView queueSummaryView = new QueueSummaryView();
        QueueSummaryModel queueSummaryModel = new QueueSummaryModel(queueSummaryView);
        queueSummaryView.setLocation(controlPanel.getWidth() + garageView.getWidth() + 75, 25);
        queueSummaryView.setSize(getToolkit().getScreenSize().width - controlPanel.getWidth() - garageView.getWidth() - 100, controlPanel.getHeight() - 275);


        add(queueSummaryView);

        queueSummaryView.init();


        SummaryView summaryView = new SummaryView();
        SummaryModel summaryModel = new SummaryModel(summaryView);
        SummaryController summaryController = new SummaryController(summaryModel);
        summaryController.setGarageModel(garageModel);
        summaryController.setQueueSummaryModel(queueSummaryModel);
//        SummaryController summaryController = new SummaryController(summaryModel, garageModel, queueSummaryModel);


        int height = controlPanel.getHeight() - garageView.getHeight() - garageView.getY();


        summaryView.setSize(garageView.getWidth(), height);
        summaryView.setLocation(controlPanel.getWidth() + 50, garageView.getHeight() + 50);
        add(summaryView);
        summaryView.init();
        summaryController.start();
        panels.add(summaryView);


    }

}
