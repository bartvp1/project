package Control;

import CarGraph.GraphController;
import Garage.GarageModel;
import MyComponents.MyButton;
import MyComponents.MyLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ControlPanel extends JPanel {
    private String[] categories = {"Control", "Reserveren", "About"};

    private GraphController graphController;
    private GarageModel garage;
    private ArrayList<MyButton> buttons = new ArrayList<>();

    public ControlPanel(GarageModel garage, GraphController graph) {
        super(null);
        this.garage = garage;
        this.graphController = graph;
    }

    public void init() {
        setBackground(new Color(47, 49, 54));

        JLabel titleLabel = new JLabel("Configuration", JLabel.CENTER);
        titleLabel.setBounds(0, 0, getWidth(), 50);
        titleLabel.setFont(new Font("Dubai Light", Font.PLAIN, 25));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel);

        //Panel met de Configuratie Tabs
        JPanel categoryPanel = new JPanel(new GridLayout(10, 1, 0, 20));
        categoryPanel.setBounds(10, titleLabel.getHeight() + 10, 120, 620);
        categoryPanel.setBackground(Color.DARK_GRAY);
        categoryPanel.setOpaque(false);
        add(categoryPanel);

        //Panel met de settings
        JPanel settingsPanel = new JPanel(new CardLayout());
        int settingsX = categoryPanel.getWidth() + categoryPanel.getX() + 10;
        settingsPanel.setBounds(settingsX, titleLabel.getHeight() + 10, getWidth() - settingsX - 10, getHeight() - titleLabel.getHeight() - 30);
        settingsPanel.setOpaque(false);
        add(settingsPanel);


        createCategoryButtons(categoryPanel, settingsPanel);

        // De verschillende menu's toevoegen aan de settingspanel
        settingsPanel.add(new SimulatorSettings(), "Control");
        settingsPanel.add(new ReservationPanel(), "Reserveren");
        settingsPanel.add(new AboutSettings(), "About");

        // De eerste panel die je ziet is de chart settings
        CardLayout cl = (CardLayout) settingsPanel.getLayout();
        cl.show(settingsPanel, "Control Panel");


        // leegje knopjes erin gooien zodat exitbutton onderaan kan staan
        for (int i = 0; i < 3; i++) {
            JPanel p = new JPanel();
            p.setVisible(false);
            categoryPanel.add(p);
        }
        // exit button sluit alles af
        MyButton exitButton = new MyButton("Exit");
        categoryPanel.add(exitButton);

        exitButton.addActionListener(e -> System.exit(0));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.setColor(new Color(0, 0, 0, 150));
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(getWidth() - 2, 0, getWidth() - 2, getHeight() - 4);
        g2.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2);


        g.setColor(new Color(0, 0, 0, 150));
        g2.setStroke(new BasicStroke(.4f));
        g.drawLine(0, 0, getWidth() - 1, 0);
        g.drawLine(0, 0, 0, getHeight());
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
            MyButton button = new MyButton(s);
            buttons.add(button);
            if (s.equals("Control")) {
                button.setSelected(true);
            }

            categoryPanel.add(button, BorderLayout.EAST);

            button.addActionListener(e -> {
                CardLayout cl = (CardLayout) settingsPanel.getLayout();
                cl.show(settingsPanel, s);

                for (JButton clickedButton : buttons) {
                    if (clickedButton.equals(button)) {
                        clickedButton.setSelected(true);
                    } else {
                        clickedButton.setSelected(false);
                    }
                }
            });
        }
    }

    class SimulatorSettings extends JPanel {


        SimulatorSettings() {
            super(new GridLayout(10, 1));
            setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
            setBackground(new Color(0x454545));

            JSlider speedSlider = new JSlider(0, 3, 0);

            speedSlider.setMinorTickSpacing(1);
            speedSlider.setMajorTickSpacing(3);
            speedSlider.setPaintTicks(true);
            speedSlider.setSnapToTicks(true);
            speedSlider.setOpaque(false);
            JPanel panel = new JPanel(new BorderLayout(10, 0));

            panel.setOpaque(false);

            panel.add(new MyLabel("Simulation speed", JLabel.CENTER, "title_small"), BorderLayout.NORTH);
            panel.add(new MyLabel("Slow", JLabel.CENTER, "description"), BorderLayout.WEST);
            panel.add(new MyLabel("Fast", JLabel.CENTER, "description"), BorderLayout.EAST);

            panel.add(speedSlider, BorderLayout.CENTER);
            add(panel);

            speedSlider.addChangeListener(e -> {
                int pause = speedSlider.getValue();
                int newTickPause = 100;
                switch (pause) {
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

            //Panel
            JPanel enterSpeed = new JPanel(new BorderLayout(10, 0));
            enterSpeed.setOpaque(false);
            JSlider enterSpeedSlider = new JSlider(0, 20, garage.getEnterSpeed());
            enterSpeedSlider.setOpaque(false);

            enterSpeed.add(new MyLabel("Entering (cars/minute): " + enterSpeedSlider.getValue(), JLabel.CENTER, "title_small"), BorderLayout.NORTH, 0);
            enterSpeed.add(new MyLabel("0", JLabel.CENTER, "description"), BorderLayout.WEST);
            enterSpeed.add(new MyLabel("20", JLabel.CENTER, "description"), BorderLayout.EAST);
            enterSpeed.add(enterSpeedSlider, BorderLayout.CENTER);
            super.add(enterSpeed);

            enterSpeedSlider.addChangeListener(e -> {
                int speed = enterSpeedSlider.getValue();
                garage.setEnterSpeed(speed);
                enterSpeed.remove(0);
                enterSpeed.add(new MyLabel("Entering (cars/minute): " + enterSpeedSlider.getValue(), JLabel.CENTER, "title_small"), BorderLayout.NORTH, 0);
            });


            //exits
            JSlider totalExitsSlider = new JSlider(0, 20, garage.getExitSpeed());
            totalExitsSlider.setOpaque(false);

            //Panel
            JPanel exitSpeed = new JPanel(new BorderLayout(10, 0));
            exitSpeed.setOpaque(false);

            exitSpeed.add(new MyLabel("Leaving (cars/minute): " + totalExitsSlider.getValue(), JLabel.CENTER, "title_small"), BorderLayout.NORTH, 0);
            exitSpeed.add(new MyLabel("0", JLabel.CENTER, "description"), BorderLayout.WEST);
            exitSpeed.add(new MyLabel("20", JLabel.CENTER, "description"), BorderLayout.EAST);
            exitSpeed.add(totalExitsSlider, BorderLayout.CENTER);
            add(exitSpeed);
            totalExitsSlider.addChangeListener(e -> {
                int speed = totalExitsSlider.getValue();
                garage.setExitSpeed(speed);
                exitSpeed.remove(0);
                exitSpeed.add(new MyLabel("Leaving (cars/minute): " + totalExitsSlider.getValue(), JLabel.CENTER, "title_small"), BorderLayout.NORTH, 0);
            });
        }
    }

    class ReservationPanel extends JPanel {

        ReservationPanel() {
            super(new GridLayout(10, 1));
            setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
            setBackground(new Color(0x454545));

            String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
            Integer[] hours = new Integer[24];
            Integer[] minutes = new Integer[60];
            for (int i = 0; i < 24; i++) {
                hours[i] = i;
            }
            for (int i = 0; i < 60; i++) {
                minutes[i] = i;
            }

            JPanel panel = new JPanel();
            panel.setOpaque(false);

            panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

            JComboBox daySelect = new JComboBox(days);
            JComboBox hourSelect = new JComboBox(hours);
            JComboBox minuteSelect = new JComboBox(minutes);
            JButton button = new JButton("add");
            daySelect.setMaximumSize(new Dimension(80, 20));
            hourSelect.setMaximumSize(new Dimension(40, 20));
            minuteSelect.setMaximumSize(new Dimension(40, 20));
            button.setMaximumSize(new Dimension(70, 20));

            panel.add(daySelect);
            panel.add(new MyLabel(" at ", JLabel.CENTER, "description"));
            panel.add(hourSelect);
            panel.add(new MyLabel(" : ", JLabel.CENTER, "description"));
            panel.add(minuteSelect);
            panel.add(Box.createRigidArea(new Dimension(45, 0)));
            panel.add(button, BorderLayout.EAST);
            super.add(panel);

            button.addActionListener(e -> {
                int day = daySelect.getSelectedIndex();
                int hour = hourSelect.getSelectedIndex();
                int minute = minuteSelect.getSelectedIndex();
                garage.addReservation(day, hour, minute);
            });

        }

    }

    class AboutSettings extends JPanel {
      
        AboutSettings() {
            setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
            setBackground(new Color(0x454545));
            add(new MyLabel("About", JLabel.CENTER, "title_small"), BorderLayout.NORTH);
            add(new MyLabel("<html>Deze parkeersimulator is gemaakt door: <br><ul><li>Vincent Huisman</li><li>Riet van Noordt Wieringa</li><li>Jarno Steursma</li><li>Bart van Poele</li></ul> </html>", JLabel.CENTER, "description"));
        }
    }

}
