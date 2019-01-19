package Summary;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SummaryView extends JPanel {
    private String timeText = "Time: ";
    private String dayNameText = "Day: ";
    private String totalCarsText = "Total Cars: ";
    private String passCarsText = "Pass Cars: ";
    private String normalCarsText = "Normal Cars: ";

    private JLabel timeLabel = new JLabel(timeText);
    private JLabel dayNameLabel = new JLabel(dayNameText);
    private JLabel totalCarsLabel = new JLabel(totalCarsText);
    private JLabel passCarLabel = new JLabel(passCarsText);
    private JLabel normalCarLabel = new JLabel(normalCarsText);


    public SummaryView() {
        super(null);
    }

    public void init() {
        setBackground(Color.DARK_GRAY);
        setBorder(new LineBorder(Color.BLACK, 2));
        add(dayNameLabel);
        dayNameLabel.setBounds(0, 0, 100, 30);
        add(timeLabel);
        timeLabel.setBounds(0, 50, 100, 30);

        add(totalCarsLabel);
        totalCarsLabel.setBounds(0, 100, 100, 30);
        add(passCarLabel);
        passCarLabel.setBounds(0, 150, 100, 30);
        add(normalCarLabel);
        normalCarLabel.setBounds(0, 200, 100, 30);


    }

    void update(SummaryModel model) {
        totalCarsLabel.setText(totalCarsText + model.getTotalCars());
        passCarLabel.setText(passCarsText + model.getPassCars());
        normalCarLabel.setText(normalCarsText + model.getNormalCars());
        dayNameLabel.setText(dayNameText + model.getDayName());
        timeLabel.setText(timeText + model.getHours() + ":" + model.getMinutes());
    }
}
