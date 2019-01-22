package Control;

import Garage.GarageModel;
import Graph.GraphController;
import MyComponents.CheckBox;
import MyComponents.MyLabel;

import javax.swing.*;
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

    private GraphController graphController;
    private JPanel settingsPanel;
    JPanel categoryPanel = new JPanel(new GridLayout(0, 1, 10, 50));
    private GarageModel garage;
    JLabel titleLabel = new JLabel("Configuratie", JLabel.CENTER);

    public ControlPanel(GarageModel garage, GraphController graph) {
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
        setBackground(new Color(47, 49, 54));

        titleLabel.setBounds(0, 0, getWidth(), 50);
        titleLabel.setFont(new Font("Dubai Light", Font.PLAIN, 25));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel);

        categoryPanel.setBounds(10, titleLabel.getHeight() + 10, 130, getHeight() - titleLabel.getHeight() - 30);
        categoryPanel.setBackground(Color.DARK_GRAY);
        categoryPanel.setOpaque(false);
        add(categoryPanel);

        //  Panel waar de settings staan
        settingsPanel = new JPanel(new CardLayout());
        int settingsX = categoryPanel.getWidth() + categoryPanel.getX() + 10;
        settingsPanel.setBounds(settingsX, titleLabel.getHeight() + 10, getWidth() - settingsX - 10, getHeight() - titleLabel.getHeight() - 30);
        settingsPanel.setOpaque(false);
        add(settingsPanel);

        createCategoryButtons(categoryPanel, settingsPanel);

        // De verschillende menu's toevoegen aan de settingspanel
        settingsPanel.add(new ChartSettings(), "Chart");
        SimulatorSettings simSet = new SimulatorSettings();
        settingsPanel.add(simSet, "Simulator");
        settingsPanel.add(new ControlSettings(), "Control Panel");
        settingsPanel.add(new AboutSettings(), "About");

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
        exitButton.setBackground(new Color(225, 0, 0));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setForeground(new Color(0, 0, 0, 255));
        exitButton.addActionListener(e -> System.exit(0));

        garage.addReservation(1, 2, 10);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 0, 0, 150));
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(getWidth() - 2, 0, getWidth() - 2, getHeight() - 4);
        g2.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2);

        g2.setStroke(new BasicStroke(.4f));
        g.drawLine(0, 0, getWidth() - 1, 0);
        g.drawLine(0, 0, 0, getHeight());
    }

    class ChartSettings extends JPanel {
        //todo: snelheid van de grafiek, welke auto's je wilt zien en misschien een layout verandering?

        JPanel panel = new JPanel(new GridLayout(1, 0));
        JCheckBox fillMode = new JCheckBox("1", true);

        ChartSettings() {
            super(null);

            setOpaque(false);
            panel.setPreferredSize(new Dimension(50, 50));
            panel.setSize(100, 10);
            panel.setOpaque(false);


            fillMode.setFont(new Font("Dubai Light", Font.PLAIN, 50));
            fillMode.setForeground(Color.WHITE);
            fillMode.setOpaque(false);
//            fillMode.setBounds(100, 100, 100, 100);

            panel.setOpaque(false);

//            add(fillMode);

            fillMode.addActionListener(e -> {
                graphController.toggleFillMode();
                fillMode.repaint();
            });

            CheckBox myCheckBox = new CheckBox(true);
            myCheckBox.setSize(15.0);
            myCheckBox.setBounds(100, 100, 100, 100);
            add(myCheckBox);


        }

    }

    class SimulatorSettings extends JPanel {
        //todo: snelheid van de de simulatie, aanpassingen aan het aantal ingangen, uitgangen etc.


        SimulatorSettings() {
            super(new GridLayout(10, 1));
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));


            setBackground(new Color(43, 48, 52));
            JSlider speedSlider = new JSlider();
            speedSlider.setMaximum(25);
            speedSlider.setMinimum(1);
            speedSlider.setValue(speedSlider.getMaximum() - garage.getTickPause());

            speedSlider.setOpaque(false);

            JPanel panel = new JPanel(new BorderLayout(10, 0));


            panel.setOpaque(false);


            panel.add(new MyLabel("Tijd", JLabel.CENTER, "title_small"), BorderLayout.NORTH);
            panel.add(new MyLabel("Langzaam", JLabel.CENTER, "description"), BorderLayout.WEST);
            panel.add(new MyLabel("Snel", JLabel.CENTER, "description"), BorderLayout.EAST);

            panel.add(speedSlider, BorderLayout.CENTER);

            add(panel);

            speedSlider.addChangeListener(e -> {
                int speed = (speedSlider.getMaximum() + 1) - speedSlider.getValue();

                garage.setTickPause(speed);
                graphController.setTicks(speed);
            });

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
//            g.setColor(new Color(80, 85, 93));
            g.setColor(new Color(0, 0, 0, 150));

            g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
            g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);

            g2.setStroke(new BasicStroke(.1f));
            g.drawLine(0, 0, getWidth() - 1, 0);
            g.drawLine(0, 0, 0, getHeight());

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
