import CarGraph.GraphController;
import CarGraph.GraphModel;
import CarGraph.GraphView;
import Control.ControlPanel;
import Finance.FinanceView;
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

    private static final Rectangle graphBounds, controlBounds, simBounds, summaryBounds, queueBounds, financeBounds;

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
        queueBounds = new Rectangle(simBounds.x + simBounds.width + margin, simBounds.height + (margin*2) , screenWidth - controlBounds.width - simBounds.width - (margin * 4), screenHeight - 770);
        financeBounds = new Rectangle(simBounds.x + simBounds.width + margin, margin , screenWidth - controlBounds.width - simBounds.width - (margin * 4), screenHeight - 655);

    }

    void init() {
        setLayout(null);
        setBackground(new Color(55, 57, 63));

        FinanceView financeView = new FinanceView();
        financeView.setBounds(financeBounds);
        financeView.init();
        add(financeView);

        GarageView garageView = new GarageView();
        garageView.setBounds(simBounds);
        GarageModel garageModel = new GarageModel(garageView);
        GarageController garageController = new GarageController(garageModel,financeView);


        garageModel.setController(garageController);
        garageController.start();
        garageModel.init();
        add(garageView);


        GraphView graphView = new GraphView();
        graphView.setBounds(graphBounds);
        GraphModel graphModel = new GraphModel(graphView, garageModel);
        GraphController graphController = new GraphController(graphModel);
        graphController.setGarage(garageModel);
        graphController.start();
        graphView.init();
        add(graphView);


        ControlPanel controlPanel = new ControlPanel(garageModel, graphController);
        controlPanel.setBounds(controlBounds);
        controlPanel.init();
        add(controlPanel);


        QueueSummaryView queueSummaryView = new QueueSummaryView();
        queueSummaryView.setBounds(queueBounds);
        QueueSummaryModel queueSummaryModel = new QueueSummaryModel(queueSummaryView,garageModel);

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
