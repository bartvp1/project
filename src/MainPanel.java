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


/**
 * De Mainpanel is de contentpane van het frame
 * Deze panel is ingedeeld met vijf subpanels: graphPanel, controlPanel, garagePanel, summaryPanel en een queueSummaryPanel
 */
class MainPanel extends JPanel {

    /**
     * screenWidth en screenHeight krijgen de waarde van de width en height van je beeldscherm
     */
    private static final int screenWidth;
    private static final int screenHeight;

    /**
     * margin bepaalt de ruimtes tussen de subPanels
     */

    private static final int margin = 25;

    /**
     * De x, y, width en height van de subpanelen als in een Rectangle
     * De simHeight en simWidth zijn vast waardes vandaar dat ze apart zijn
     */
    private static final Rectangle graphBounds, controlBounds, simBounds, summaryBounds, queueBounds;
    private static final int simHeight, simWidth;

    /**
     * Nog voor de initialisatie worden de rectangles berekent
     * De graphPanel zit onderaan en vult de hele breedte van het beeldscherm. de hoogte is 1/4 van de hoogte van beeldscherm.
     * De garagePanel(sim) zit in midden-boven van het beeldscherm en heeft een vaste width en height.
     * De controlPanel zit links boven en de width en height wordt berekend met de omringende subpanels en de margin ertussen
     * De summaryPanel zit in het midden van het beeldscherm onder de garagePanel en boven de grafiek. width en height worden  weer berekend met de omringende subpanels.
     * De queuePanel zit rechtsboven. width en height worden berekend.
     */
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

    /**
     * Initialisatie van de MainPanel
     * Layout is null zodat de subpanels met x en y coordinaten kunnen worden geplaatst.
     * Alle subPanels worden hier aagemaakt en toegevoegd. De meeste subPanels bestaan uit een model, controller en view.
     */
    void init() {
        setLayout(null);
        setBackground(new Color(55, 57, 63));

        // De Garage panel
        // view
        GarageView garageView = new GarageView();
        garageView.setBounds(simBounds);
        //model
        GarageModel garageModel = new GarageModel(garageView);
        //controller
        FinancesController fc = new FinancesController();
        GarageController garageController = new GarageController(garageModel);
        garageController.setFinanceController(fc);


        garageModel.setController(garageController);
        garageController.start();
        garageModel.init();
        add(garageView);

        FinancesController financeController = new FinancesController();

        // De grafiek panel
        // view
        GraphView graphView = new GraphView();
        graphView.setBounds(graphBounds);
        // model
        GraphModel graphModel = new GraphModel(graphView, garageModel);
        //controller
        GraphController graphController = new GraphController(graphModel);


        graphController.setGarage(garageModel);
        graphController.start();


        graphView.init();
        add(graphView);

        // De control Panel
        ControlPanel controlPanel = new ControlPanel(garageModel, financeController, graphController);
        controlPanel.setBounds(controlBounds);
        controlPanel.init();
        add(controlPanel);

//      De queue panel - maakt gebruik van de summary controller
        // view
        QueueSummaryView queueSummaryView = new QueueSummaryView();
        queueSummaryView.setBounds(queueBounds);
        // model
        QueueSummaryModel queueSummaryModel = new QueueSummaryModel(queueSummaryView, garageModel);

        add(queueSummaryView);
        queueSummaryView.init();

//      De summary Panel
        // view
        SummaryView summaryView = new SummaryView();
        // model
        SummaryModel summaryModel = new SummaryModel(summaryView);
        // controller
        SummaryController summaryController = new SummaryController(summaryModel);
        summaryController.setGarageModel(garageModel);
        summaryController.setQueueSummaryModel(queueSummaryModel);


        summaryView.setBounds(summaryBounds);
        add(summaryView);
        summaryView.init();
        summaryController.start();
    }

}
