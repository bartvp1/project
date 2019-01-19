package Control;

import Garage.Garage;
import Graph.GraphController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Control Panel beheert de controls
 * Bestaat uit twee delen:
 * 1. Een deel waar je de categorie kunt kiezen.
 * Categorieen: "Chart", "Simulator", "Control Panel", "About";
 * <p>
 * 2. Een deel wat bestaat uit de panel met de settings die bij de categorie hoort.
 * De card layout zorgt ervoor dat er wordt geswitched als een categorie wordt geklikt.
 * De verschillende settings (Panels) worden beheert in aparte classes onderaan de pagina.
 * <p>
 * Als het blijkt dat we weinig settings hebben kunnen we alles op 1 panel doen.
 */
public class ControlPanel extends JPanel {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private String[] categories = {"Simulator", "Chart", "Control Panel", "About"};
    private Garage garage;
    private GraphController graphController;
    private JPanel settingsPanel;

    public ControlPanel(Garage garage, GraphController graph) {
        super(null);
        this.garage = garage;
        this.graphController = graph;
    }

    /**
     * Grafiek Settings
     * <p>
     * Speed
     * Show different Cars
     * Change Grafiek style?
     * Toggle scroll / repeat
     * Start
     * Stop
     * Pause
     * Reset
     */


    public void init() {
        //  grootte van dit scherm, breedte: 1/3 van  je beeldscherm & hoogte: 3/4 van je beeldscherm.
//        setSize((screenSize.width / 3), ((screenSize.height / 4) * 3));

        // Wordt nog aangepast als we 't wat mooier maken
        setBackground(Color.DARK_GRAY);
        setBorder(new LineBorder(Color.BLACK, 3, true));

        // Panel waar je de category kan kiezen.
        JPanel categoryPanel = new JPanel(new GridLayout(0, 1, 10, 50));
        categoryPanel.setBounds(10, 10, 130, getHeight() - 25);
        categoryPanel.setBackground(Color.DARK_GRAY);
        add(categoryPanel);

        //  Panel waar de settings staan
        settingsPanel = new JPanel(new CardLayout());
        settingsPanel.setBounds(150, 10, getWidth() - 160, getHeight() - 20);
        settingsPanel.setBackground(Color.DARK_GRAY);
        settingsPanel.setBorder(new LineBorder(Color.BLACK, 3, true));
        add(settingsPanel);

        createCategoryButtons(categoryPanel, settingsPanel);

        // De verschillende menu's toevoegen aan de settingspanel
        settingsPanel.add(new ChartSettings(), "Chart");
        SimulatorSettings simSet = new SimulatorSettings();
        settingsPanel.add(simSet, "Simulator");
        settingsPanel.add(new ControlSettings(), "Control Panel");
        settingsPanel.add(new AboutSettings(), "About");
        simSet.init();
        // De eerste panel die je ziet is de chart settings
        CardLayout cl = (CardLayout) settingsPanel.getLayout();
        cl.show(settingsPanel, "Simulator");

        // leegje knopjes erin gooien zodat exitbutton onderaan kan staan
        for (int i = 0; i < 3; i++) {
            JPanel p = new JPanel();
            p.setVisible(false);
            categoryPanel.add(p);
        }
        // exit button sluit alles af
        JButton exitButton = new JButton("Exit");
        categoryPanel.add(exitButton);
        exitButton.setBackground(new Color(225, 0, 0, 100));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setForeground(new Color(0, 0, 0, 255));
        exitButton.addActionListener(e -> System.exit(0));
    }


    /**
     * @param categoryPanel de categorypanel waar de categories staan
     * @param settingsPanel de settignspanel waar de settings staan.
     *                      <p>
     *                      Hij gaat alle categories bij langs en maakt voor elke een button, deze stopt ie in de categorypanel
     *                      Als er op wordt geklikt, switched de panel naar de juist categorie
     */
    private void createCategoryButtons(JPanel categoryPanel, JPanel settingsPanel) {

        for (String s : categories) {
            JButton button = new JButton(s);
            categoryPanel.add(button);
            button.addActionListener(e -> {
                CardLayout cl = (CardLayout) settingsPanel.getLayout();
                cl.show(settingsPanel, s);
            });
        }
    }

    class ChartSettings extends JPanel {
        //todo: snelheid van de grafiek, welke auto's je wilt zien en misschien een layout verandering?
        JButton fillMode = new JButton("Fill");
        JSlider speedSlider = new JSlider();
        JPanel panel = new JPanel(new BorderLayout());

        ChartSettings() {
            super(new GridLayout(5, 1));
            setBackground(Color.DARK_GRAY);

            add(panel);

            panel.setBorder(new LineBorder(Color.BLACK, 1, true));
            add(panel);
            panel.add(new JLabel("Snelheid", JLabel.CENTER), BorderLayout.NORTH);
            panel.add(new JLabel("Langzaam"), BorderLayout.WEST);
            panel.add(speedSlider, BorderLayout.CENTER);
            panel.add(new JLabel("Snel"), BorderLayout.EAST);


            add(fillMode);
            fillMode.addActionListener(e -> graphController.toggleFillMode());
            System.out.println(graphController.getTicks());
            speedSlider.setValue(speedSlider.getMaximum() - graphController.getTicks());
            speedSlider.addChangeListener(e -> {
                int sliderValue = speedSlider.getValue();

                sliderValue = 100 - sliderValue;
                if (sliderValue <= 0) {
                    sliderValue = 1;
                }
                graphController.setTicks(sliderValue);
            });
//
//            JButton hideButton = new JButton("Hide/Show");
//            gridPanel.add(hideButton);
//            hideButton.addActionListener(e -> {
//                if (graph.isUp()) {
//                    graph.doHide();
//                } else if (graph.isDown()) {
//                    graph.doShow();
//                }
//
//            });
        }
    }

    class SimulatorSettings extends JPanel {
        //todo: snelheid van de de simulatie, aanpassingen aan het aantal ingangen, uitgangen etc.
        Garage gar;

        SimulatorSettings() {
            super(new GridLayout(10, 1));
            setBackground(Color.DARK_GRAY);
            JSlider speedSlider = new JSlider();
            speedSlider.setValue(speedSlider.getMaximum() - garage.getTickPause());

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JLabel("Snelheid", JLabel.CENTER), BorderLayout.NORTH);
            panel.add(new JLabel("Langzaam"), BorderLayout.WEST);
            panel.add(speedSlider, BorderLayout.CENTER);
            panel.add(new JLabel("Snel"), BorderLayout.EAST);

            add(panel);

            speedSlider.addChangeListener(e -> {
                int speed = speedSlider.getMaximum() - speedSlider.getValue();
                speed = speed == 0 ? 1 : speed;
                garage.setTickPause(speed);
            });

        }

        void init() {
            System.out.println(garage.getTickPause());
        }
    }

    class ControlSettings extends JPanel {
        //todo: Controls van de control panel, als er niks in hoeft kan deze weg.
        ControlSettings() {
            setBackground(Color.BLUE);
        }
    }

    class AboutSettings extends JPanel {
        //todo: Informatie over ons, dat we zo cool zijn enzo.
        AboutSettings() {
            setBackground(Color.YELLOW);
        }
    }
}
