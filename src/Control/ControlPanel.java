package Control;

import Garage.GarageModel;
import Garage.Reservation;
import Graph.GraphController;
import MyComponents.CheckBox;
import MyComponents.MyLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private String[] categories = {"Simulator", "Reservations", "Chart","Control Panel", "About"};

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
        settingsPanel.add(new ReservationPanel(), "Reservations");
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

            JSlider speedSlider = new JSlider(0,3,0);

            speedSlider.setMinorTickSpacing(1);
            speedSlider.setMajorTickSpacing(3);
            speedSlider.setPaintTicks(true);
            speedSlider.setSnapToTicks(true);
            speedSlider.setOpaque(false);
            JPanel panel = new JPanel(new BorderLayout(10, 0));

            panel.setOpaque(false);

            panel.add(new MyLabel("Tijd", JLabel.CENTER, "title_small"), BorderLayout.NORTH);
            panel.add(new MyLabel("Langzaam", JLabel.CENTER, "description"), BorderLayout.WEST);
            panel.add(new MyLabel("Snel", JLabel.CENTER, "description"), BorderLayout.EAST);

            panel.add(speedSlider, BorderLayout.CENTER);
            add(panel);

            speedSlider.addChangeListener(e -> {
                int pause = speedSlider.getValue();
                int newTickPause = 100;
                switch(pause){
                    case 0:
                        newTickPause = 100;
                        break;
                    case 1:
                        newTickPause = 20;
                        break;
                    case 2:
                        newTickPause = 5;
                        break;
                    case 3:
                        newTickPause = 1;
                        break;
                }

                garage.setTickPause(newTickPause);
                graphController.setTicks(newTickPause);
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
    class ReservationPanel extends JPanel {
        //todo: Reserveringen kunnen toegevoegd worden met dit menu
        ReservationPanel() {
            super(new GridLayout(15, 5));
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            //setBackground(new Color(43, 48, 52));
            setBackground(new Color(0x454545));

            String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
            Integer[] hours = new Integer[24];
            Integer[] minutes = new Integer[60];
            for(int i=0;i<24;i++){hours[i]=i;}
            for(int i=0;i<60;i++){minutes[i]=i;}

            JPanel title = new JPanel();
            JPanel panel = new JPanel();
            title.setOpaque(false);
            panel.setOpaque(false);

            title.add(new MyLabel("Reservering toevoegen", JLabel.CENTER, "title_small"), BorderLayout.NORTH);

            panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

            JComboBox daySelect = new JComboBox(days);
            JComboBox hourSelect = new JComboBox(hours);
            JComboBox minuteSelect = new JComboBox(minutes);
            JButton button = new JButton("add");
            daySelect.setMaximumSize(new Dimension(80,20));
            hourSelect.setMaximumSize(new Dimension(40,20));
            minuteSelect.setMaximumSize(new Dimension(40,20));
            button.setMaximumSize(new Dimension(40,20));

            panel.add(daySelect);
            panel.add(new MyLabel(" at ", JLabel.CENTER, "description"));
            panel.add(hourSelect);
            panel.add(new MyLabel(" : ", JLabel.CENTER, "description"));
            panel.add(minuteSelect);
            panel.add(Box.createRigidArea(new Dimension(5,0)));
            panel.add(button);


            add(title);
            add(panel);

            button.addActionListener(e ->  {
                    int day = daySelect.getSelectedIndex();
                    int hour = hourSelect.getSelectedIndex();
                    int minute = minuteSelect.getSelectedIndex();
                    garage.addReservation(day,hour,minute);
            });

        }

    }

    class ControlSettings extends JPanel {
        ControlSettings() {
            super(new GridLayout(10, 1));
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            setBackground(new Color(43, 48, 52));


            //Panel
            JPanel panel = new JPanel(new BorderLayout(10, 0));
            panel.setOpaque(false);

            panel.add(new MyLabel("Waiting in Q: " + garage.getEntranceCarQueue().carsInQueue(), JLabel.CENTER, "title_small"), BorderLayout.NORTH, 0);


            add(panel);


            JSlider enterSpeedSlider = new JSlider();
            enterSpeedSlider.setMaximum(20);
            enterSpeedSlider.setMinimum(0);
            enterSpeedSlider.setValue(garage.getEnterSpeed());
            enterSpeedSlider.setOpaque(false);

            //Panel
            JPanel panelEnterspeed = new JPanel(new BorderLayout(10, 0));
            panelEnterspeed.setOpaque(false);

            panelEnterspeed.add(new MyLabel("Entrance: " + enterSpeedSlider.getValue(), JLabel.CENTER, "title_small"), BorderLayout.NORTH, 0);
            panelEnterspeed.add(new MyLabel("0", JLabel.CENTER, "description"), BorderLayout.WEST);
            panelEnterspeed.add(new MyLabel("20", JLabel.CENTER, "description"), BorderLayout.EAST);
            panelEnterspeed.add(enterSpeedSlider, BorderLayout.CENTER);

            add(panelEnterspeed);
            enterSpeedSlider.addChangeListener(e -> {
                int speed = enterSpeedSlider.getValue();
                garage.setEnterSpeed(speed);
                panel.updateUI();
                panelEnterspeed.remove(0);
                panelEnterspeed.add(new MyLabel("Entrance: " + enterSpeedSlider.getValue(), JLabel.CENTER, "title_small"), BorderLayout.NORTH, 0);


                panel.removeAll();
                panel.add(new MyLabel("Waiting in Q: " + garage.getEntranceCarQueue().carsInQueue(), JLabel.CENTER, "title_small"), BorderLayout.NORTH, 0);

            });


            //exits
            JSlider totalExitsSlider = new JSlider();
            totalExitsSlider.setMaximum(20);
            totalExitsSlider.setMinimum(0);
            totalExitsSlider.setValue(garage.getExitSpeed());
            totalExitsSlider.setOpaque(false);

            //Panel
            JPanel panelTotalExits = new JPanel(new BorderLayout(10, 0));
            panelTotalExits.setOpaque(false);

            panelTotalExits.add(new MyLabel("Exits: " + totalExitsSlider.getValue(), JLabel.CENTER, "title_small"), BorderLayout.NORTH, 0);
            panelTotalExits.add(new MyLabel("0", JLabel.CENTER, "description"), BorderLayout.WEST);
            panelTotalExits.add(new MyLabel("20", JLabel.CENTER, "description"), BorderLayout.EAST);
            panelTotalExits.add(totalExitsSlider, BorderLayout.CENTER);

            add(panelTotalExits);
            totalExitsSlider.addChangeListener(e -> {
                int speed = totalExitsSlider.getValue();
                garage.setExitSpeed(speed);

                panelTotalExits.remove(0);
                panelTotalExits.add(new MyLabel("Exits: " + totalExitsSlider.getValue(), JLabel.CENTER, "title_small"), BorderLayout.NORTH, 0);


                panel.removeAll();
                panel.add(new MyLabel("Waiting in Q: " + garage.getEntranceCarQueue().carsInQueue(), JLabel.CENTER, "title_small"), BorderLayout.NORTH, 0);

            });

            panel.removeAll();
            panel.add(new MyLabel("Waiting in Q: " + garage.getEntranceCarQueue().carsInQueue(), JLabel.CENTER, "title_small"), BorderLayout.NORTH, 0);
        }
    }

    class AboutSettings extends JPanel {
        //todo: Informatie over ons, dat we zo cool zijn enzo.
        AboutSettings() {
            setBackground(Color.YELLOW);
        }
    }
}
