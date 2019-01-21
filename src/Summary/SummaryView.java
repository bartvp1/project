package Summary;

import MyComponents.MyLabel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SummaryView extends JPanel {
    private String timeText = "Time: ";
    private String dayNameText = "Day: ";
    private String totalCarsText = "Total Cars: ";
    private String passCarsText = "Pass Cars: ";
    private String normalCarsText = "Normal Cars: ";

    private MyLabel timeLabel = new MyLabel("", JLabel.CENTER, "Title_small");
    private JLabel dayNameLabel = new JLabel(dayNameText, JLabel.RIGHT);
    private MyLabel totalCarsLabel = new MyLabel("", JLabel.LEFT, "Title_small");
    private MyLabel passCarLabel = new MyLabel("", JLabel.LEFT, "Title_small");
    private MyLabel normalCarLabel = new MyLabel("", JLabel.LEFT, "Title_small");
    private MyLabel reservedCarLabel = new MyLabel("", JLabel.LEFT, "Title_small");
    String[] days = {"Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag", "Zaterdag", "Zondag"};

    public SummaryView() {
        super(null);
    }

    String[] labelNames = {"TotalCars", "PassCars", "NormalCars", "ReservedCars"};
    HashMap<String, JLabel> labels = new HashMap<>();

    HashMap<String, MyLabel> dayLabels = new HashMap<>();

    JProgressBar passCarBar = new JProgressBar(0, 540);
    JProgressBar totalCarBar = new JProgressBar(0, 540);
    JProgressBar normalCarBar = new JProgressBar(0, 540);
    JProgressBar reservedCarBar = new JProgressBar(0, 540);


    public void init() {
        setBackground(new Color(47, 49, 54));
        MyLabel title = new MyLabel("Overzicht", JLabel.CENTER, "Title");
        title.setBounds(0, 0, getWidth(), 50);
        add(title);

        int dayLength = getWidth() / days.length;
        for (int i = 0; i < days.length; i++) {
            MyLabel label = new MyLabel(days[i], JLabel.CENTER, "Normal");
            label.setForeground(new Color(255, 255, 255, 25));
            label.setBounds(i * dayLength, 50, dayLength, 20);
            dayLabels.put(days[i], label);
            add(label);
        }

        JPanel panel = new JPanel(new GridLayout(0, 3, 25, 5));
        panel.setBounds(25, 100, 750, 125);
        panel.setOpaque(false);

        totalCarBar = new JProgressBar(0, 540);
        totalCarBar.setBounds(100, 75, 100, 25);
        totalCarBar.setValue(500);
        totalCarBar.setForeground(Color.GREEN);

        panel.add(new MyLabel("Totaal aantal auto's", JLabel.LEFT, "Title_small"));
        panel.add(totalCarBar);
        panel.add(totalCarsLabel);

        passCarBar = new JProgressBar(0, 120);
        passCarBar.setBounds(100, 75, 100, 25);
        passCarBar.setValue(500);
        passCarBar.setForeground(Color.BLUE);

        panel.add(new MyLabel("Totaal pashouders", JLabel.LEFT, "Title_small"));
        panel.add(passCarBar);
        panel.add(passCarLabel);

        normalCarBar = new JProgressBar(0, 420);
        normalCarBar.setBounds(100, 75, 100, 25);
        normalCarBar.setValue(500);
        normalCarBar.setForeground(Color.RED);

        panel.add(new MyLabel("Totaal betalende auto's", JLabel.LEFT, "Title_small"));
        panel.add(normalCarBar);
        panel.add(normalCarLabel);

        reservedCarBar = new JProgressBar(0, 540);
        reservedCarBar.setBounds(100, 75, 100, 25);
        reservedCarBar.setValue(500);
        reservedCarBar.setForeground(Color.YELLOW);

        panel.add(new MyLabel("Totaal gereserveerde plekken", JLabel.LEFT, "Title_small"));
        panel.add(reservedCarBar);
        panel.add(reservedCarLabel);

        add(panel);

        timeLabel.setText("dsf");
        timeLabel.setFontSize(75);
        timeLabel.setFontName("Arial");
        timeLabel.setBounds(525, 50, getWidth() / 2, getHeight() - 50);
    }

    String timeString;
    SummaryModel model;

    void update(SummaryModel model) {
        this.model = model;

        normalCarLabel.setText(Integer.toString(model.getNormalCars()));
        reservedCarLabel.setText(Integer.toString(model.getReservedCars()));
        totalCarsLabel.setText(Integer.toString(model.getTotalCars()));
        passCarLabel.setText(Integer.toString(model.getPassCars()));

        totalCarBar.setValue(model.getTotalCars());
        passCarBar.setValue(model.getPassCars());
        normalCarBar.setValue(model.getNormalCars());


        reservedCarBar.setMaximum(model.getReservedLocations());
        reservedCarBar.setValue(model.getReservedCars());


//        dayNameLabel.setText(dayNameText + model.getDayName());


        timeLabel.setText(model.getHours() + ":" + model.getMinutes());

        for (String day : days) {
            MyLabel label = dayLabels.get(day);

            Color color = day.equals(model.getDayName()) ? Color.WHITE : new Color(255, 255, 255, 25);
            label.setForeground(color);
        }

//        repaint();

        String hours = Integer.toString(model.getHours());
        String minutes = Integer.toString(model.getMinutes());
        hours = hours.length() <= 1 ? "0" + hours : hours;
        minutes = minutes.length() <= 1 ? "0" + minutes : minutes;

        timeString = hours + ":" + minutes;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        BasicStroke stroke = (BasicStroke) g2.getStroke();
        g.setColor(new Color(0, 0, 0, 150));

        g2.setColor(new Color(0, 0, 0, 150));
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(getWidth() - 2, 0, getWidth() - 2, getHeight() - 4);
        g2.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2);


        if (model != null) {
            g2.setFont(new Font("Arial", Font.PLAIN, 75));
            g2.setColor(Color.WHITE);

            g2.drawString(timeString, 600, getHeight() / 2 + 50);

        }

        g.setColor(new Color(0, 0, 0, 150));
        g2.setStroke(new BasicStroke(.4f));
        g.drawLine(0, 0, getWidth() - 1, 0);
        g.drawLine(0, 0, 0, getHeight());
        g2.setStroke(stroke);

    }
}
