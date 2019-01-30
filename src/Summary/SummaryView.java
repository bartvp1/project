package Summary;

import MyComponents.Model;
import MyComponents.MyLabel;
import MyComponents.View;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SummaryView extends View {

    private MyLabel timeLabel = new MyLabel("", JLabel.CENTER, "Title_small");
    private MyLabel totalCarsLabel = new MyLabel("", JLabel.LEFT, "Title_small");
    private MyLabel passCarLabel = new MyLabel("", JLabel.LEFT, "Title_small");
    private MyLabel normalCarLabel = new MyLabel("", JLabel.LEFT, "Title_small");
    private MyLabel reservedCarLabel = new MyLabel("", JLabel.LEFT, "Title_small");
    private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private HashMap<String, MyLabel> dayLabels = new HashMap<>();

    private JProgressBar passCarBar = new JProgressBar(0, 540);
    private JProgressBar totalCarBar = new JProgressBar(0, 540);
    private JProgressBar normalCarBar = new JProgressBar(0, 540);
    private JProgressBar reservedCarBar = new JProgressBar(0, 540);


    public void init() {
        setBackground(new Color(47, 49, 54));
        MyLabel title = new MyLabel("Overview", JLabel.CENTER, "Title");
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
        panel.setBounds(25, 125, 750, 125);
        panel.setOpaque(false);

        totalCarBar.setBounds(100, 75, 100, 25);
        totalCarBar.setValue(500);
        totalCarBar.setForeground(Color.GREEN);

        panel.add(new MyLabel("Total cars", JLabel.LEFT, "Title_small"));
        panel.add(totalCarBar);
        panel.add(totalCarsLabel);


        passCarBar.setBounds(100, 75, 100, 25);
        passCarBar.setValue(500);
        passCarBar.setForeground(Color.BLUE);

        panel.add(new MyLabel("Member cars", JLabel.LEFT, "Title_small"));
        panel.add(passCarBar);
        panel.add(passCarLabel);


        normalCarBar.setBounds(100, 75, 100, 25);
        normalCarBar.setValue(500);
        normalCarBar.setForeground(Color.RED);

        panel.add(new MyLabel("Regular cars", JLabel.LEFT, "Title_small"));
        panel.add(normalCarBar);
        panel.add(normalCarLabel);


        reservedCarBar.setBounds(100, 75, 100, 25);
        reservedCarBar.setValue(500);
        reservedCarBar.setForeground(Color.YELLOW);

        panel.add(new MyLabel("Reserved spots", JLabel.LEFT, "Title_small"));
        panel.add(reservedCarBar);
        panel.add(reservedCarLabel);

        add(panel);

        timeLabel.setText("dsf");
        timeLabel.setFontSize(75);
        timeLabel.setFontName("Arial");
        timeLabel.setBounds(525, 50, getWidth() / 2, getHeight() - 50);
    }

    private String timeString;
    private Model model;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (model != null) {
            g2.setFont(new Font("Arial", Font.PLAIN, 75));
            g2.setColor(Color.WHITE);
            g2.drawString(timeString, 625, getHeight() / 2 + 60);
        }
    }

    @Override
    protected void update(Model model) {
        SummaryModel sModel = (SummaryModel) model;

        this.model = model;
        int totalCars = sModel.getPassCars() + sModel.getNormalCars();
        normalCarLabel.setText(Integer.toString(sModel.getNormalCars()));
        reservedCarLabel.setText(Integer.toString(sModel.getReservedCars()));
        totalCarsLabel.setText(Integer.toString(totalCars));
        passCarLabel.setText(Integer.toString(sModel.getPassCars()));

        totalCarBar.setValue(totalCars);
        passCarBar.setValue(sModel.getPassCars());
        normalCarBar.setValue(sModel.getNormalCars());


        reservedCarBar.setMaximum(sModel.getReservedLocations());
        reservedCarBar.setValue(sModel.getReservedCars());

        timeLabel.setText(sModel.getHours() + ":" + sModel.getMinutes());

        for (String day : days) {
            MyLabel label = dayLabels.get(day);

            Color color = day.equals(sModel.getDayName()) ? Color.WHITE : new Color(255, 255, 255, 25);
            label.setForeground(color);
        }


        String hours = Integer.toString(sModel.getHours());
        String minutes = Integer.toString(sModel.getMinutes());
        hours = hours.length() <= 1 ? "0" + hours : hours;
        minutes = minutes.length() <= 1 ? "0" + minutes : minutes;

        timeString = hours + ":" + minutes;

        repaint();
    }
}
