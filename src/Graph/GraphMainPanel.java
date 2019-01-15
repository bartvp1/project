package Graph;

import Garage.Garage;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

// Dit is de onderste panel waar de grafiek in staat.
// De grafiek staat in een ScrollPanel(GraphScrollPanel) zodat als de lijnen rechts buiten beeld komen deze gescrolled kan worden.
public class GraphMainPanel extends JPanel {
    // De scrollpanel waar de grafiek in staat.
    private GraphScrollPanel sp;

    public GraphMainPanel(Garage garage) {
        // Deze panel heeft null als layout zodat ik zelf de x en y locatie zelf kan bepalen
        super(null);

        // Creer instantie van de scrollpanel en geef garage mee
        sp = new GraphScrollPanel(garage);
    }

    public void init() {
        // Achtergrond kleur van deze panel.
        setBackground(Color.DARK_GRAY);

        // Gooi de scrollpanel op het beeldscherm
        add(sp);

        //Initialeer dingen in de scrollpanel
        sp.init();

        // Maak start stop en reset button
        makeButtons();

        // Maak label waar de kleuren op staan van de lijnen in de grafiek
        makeLabels();

        // De combobox om aantal ticks te kiezen
        JLabel ticksLabel = new JLabel("ticks", JLabel.CENTER);
        ticksLabel.setBounds(2, 5, 100, 20);
        ticksLabel.setFont(new Font("Dubai Light", Font.PLAIN, 15));
        ticksLabel.setForeground(Color.WHITE);
        add(ticksLabel);

        String[] tickValues = {"25", "50", "100", "250", "500", "1000", "2500"};
        JComboBox<String> ticksBox = new JComboBox<>(tickValues);
        ticksBox.setBounds(2, 25, 100, 20);
        ticksBox.setSelectedIndex(3);
        ticksBox.addActionListener(e -> {
            int ticks = Integer.parseInt((String) Objects.requireNonNull(ticksBox.getSelectedItem()));
            sp.getContent().setTicks(ticks);
        });
        add(ticksBox);

        sp.getContent().setTicks(Integer.parseInt(tickValues[3]));

        repaint();
    }


    private void makeButtons() {
        Color backgroundColor = new Color(100, 100, 100);
        Color textColor = new Color(255, 255, 255);
        Font textFont = new Font("Dubai Light", Font.BOLD, 15);
        Insets noMargins = new Insets(0, 0, 0, 0);
        String[] buttonTexts = {"Stop", "Start", "Reset"};
        int x = 2;
        int y = 50;
        int width = 100;
        int height = 20;

        for (String s : buttonTexts) {
            JButton button = new JButton(s);
            button.setBackground(backgroundColor);
            button.setForeground(textColor);
            button.setFont(textFont);
            button.setMargin(noMargins);
            button.setBounds(x, y, width, height);
            button.addActionListener(e -> {
                try {
                    Method method = sp.getContent().getClass().getMethod(s.toLowerCase());
                    method.invoke(sp.getContent());
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            });

            add(button);
            y += 25;
        }


    }

    private void makeLabels() {
        String[] texts = {"Total Cars", "Pass Cars", "Normal Cars"};
        int x = 2;
        int y = 130;
        int width = 100;
        int height = 20;
        for (String s : texts) {
            JLabel label = new JLabel(s, JLabel.CENTER);
            label.setBounds(x, y, width, height);
            switch (s) {
                case "Total Cars":
                    label.setForeground(Color.GREEN);
                    break;
                case "Pass Cars":
                    label.setForeground(Color.RED);
                    break;
                case "Normal Cars":
                    label.setForeground(Color.BLUE);
                    break;
            }
            add(label);
            y += 30;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        if (sp != null) {
            for (int i = 0; i <= 10; i++) {
                g.drawString(Integer.toString((10 - i) * 60), sp.getX() - 20, (i * 25) - 10);
            }
        }
    }
}