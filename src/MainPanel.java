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

class MainPanel extends JPanel {


    void init() {
        setLayout(null);
        setBackground(new Color(55, 57, 63));

        GarageView garageView = new GarageView();
        GarageModel garageModel = new GarageModel(garageView);
        GarageController garageController = new GarageController(garageModel);
        garageModel.setController(garageController);
        garageController.start();
        garageModel.init();
        add(garageView);

        FinancesController financeController = new FinancesController();

        GraphView graphView = new GraphView();
        GraphModel graphModel = new GraphModel(graphView, garageModel);
        GraphController graphController = new GraphController(graphModel);
        graphController.setGarage(garageModel);
        graphController.start();
        graphView.init();
        add(graphView);


//        ControlPanel controlPanel = new ControlPanel(garageModel, graphController);
        ControlPanel controlPanel = new ControlPanel(garageModel, financeController, graphController);
//
//        controlPanel.setLocation(25, 25);
//        controlPanel.setSize(450, 700);
        controlPanel.init();
        add(controlPanel);


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


        int height = controlPanel.getHeight() - garageView.getHeight() - garageView.getY();

        summaryView.setSize(garageView.getWidth(), height);
        summaryView.setLocation(controlPanel.getWidth() + 50, garageView.getHeight() + 50);
        add(summaryView);
        summaryView.init();
        summaryController.start();
    }

}
