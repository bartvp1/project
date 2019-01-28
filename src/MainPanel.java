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
    private static final int screenWidth;
    private static final int screenHeight;


    private static final int margin = 25;

    private static final Rectangle graphBounds, controlBounds, simBounds, summaryBounds, queueBounds;

    private static final int simHeight, simWidth;

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        simWidth = 900;
        simHeight = 425;

        graphBounds = new Rectangle(0, screenHeight - (screenHeight / 4), screenWidth, screenHeight / 4);
        simBounds = new Rectangle((screenWidth / 2) - (simWidth / 2), margin, simWidth, simHeight);
        controlBounds = new Rectangle(margin, margin, (screenWidth - simBounds.width) / 2 - (margin * 2), screenHeight - graphBounds.height - (margin * 2));
        summaryBounds = new Rectangle(simBounds.x, simBounds.y + simBounds.height + margin, simBounds.width, screenHeight - graphBounds.height - simBounds.height - (margin * 3));
        queueBounds = new Rectangle(simBounds.x + simBounds.width + margin, margin, screenWidth - controlBounds.width - simBounds.width - (margin * 4), simBounds.height);

    }

    void init() {
        setLayout(null);
        setBackground(new Color(55, 57, 63));


        GarageView garageView = new GarageView();
//        garageView.setBounds(simX, simY, simWidth, simHeight);
        garageView.setBounds(simBounds);
        GarageModel garageModel = new GarageModel(garageView);
        FinancesController fc = new FinancesController();
        GarageController garageController = new GarageController(garageModel, fc);
        garageModel.setController(garageController);
        garageController.start();
        garageModel.init();
        add(garageView);

        FinancesController financeController = new FinancesController();

        GraphView graphView = new GraphView();
        graphView.setBounds(graphBounds);
//        graphView.setBounds(graphX, graphY, graphWidth, graphHeight);
//        graphView.setBounds(graphBounds);

        GraphModel graphModel = new GraphModel(graphView, garageModel);
        GraphController graphController = new GraphController(graphModel);
        graphController.setGarage(garageModel);
        graphController.start();
        graphView.init();
        add(graphView);


        ControlPanel controlPanel = new ControlPanel(garageModel, financeController, graphController);
//        controlPanel.setBounds(controlX, controlY, controlWidth, controlHeight);
        controlPanel.setBounds(controlBounds);
        controlPanel.init();
        add(controlPanel);


        QueueSummaryView queueSummaryView = new QueueSummaryView();
        queueSummaryView.setBounds(queueBounds);
        QueueSummaryModel queueSummaryModel = new QueueSummaryModel(queueSummaryView);

//        queueSummaryView.setLocation(controlPanel.getWidth() + garageView.getWidth() + 75, 25);
//        queueSummaryView.setSize(getToolkit().getScreenSize().width - controlPanel.getWidth() - garageView.getWidth() - 100, controlPanel.getHeight() - 275);


        add(queueSummaryView);

        queueSummaryView.init();


        SummaryView summaryView = new SummaryView();
        SummaryModel summaryModel = new SummaryModel(summaryView);
        SummaryController summaryController = new SummaryController(summaryModel);
        summaryController.setGarageModel(garageModel);
        summaryController.setQueueSummaryModel(queueSummaryModel);


        summaryView.setBounds(summaryBounds);
        add(summaryView);
        summaryView.init();
        summaryController.start();
    }

}
