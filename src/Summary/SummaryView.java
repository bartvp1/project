package Summary;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.regex.Pattern;

public class SummaryView extends JPanel {
    private String timeText = "Time: ";
    private String dayNameText = "Day: ";
    private String totalCarsText = "Total Cars: ";
    private String passCarsText = "Pass Cars: ";
    private String normalCarsText = "Normal Cars: ";

    private JLabel timeLabel = new JLabel(timeText, JLabel.RIGHT);
    private JLabel dayNameLabel = new JLabel(dayNameText, JLabel.RIGHT);
    private JLabel totalCarsLabel = new JLabel(totalCarsText);
    private JLabel passCarLabel = new JLabel(passCarsText);
    private JLabel normalCarLabel = new JLabel(normalCarsText);


    public SummaryView() {
        super(null);
    }

    String[] labelNames = {"TotalCars", "PassCars", "NormalCars", "ReservedCars"};
    HashMap<String, JLabel> labels = new HashMap<>();

    public void init() {
        setBackground(Color.DARK_GRAY);
        setBorder(new LineBorder(Color.BLACK, 2));

        int y = 100;
        for (String s : labelNames) {
            JLabel label = new JLabel("", JLabel.RIGHT);
            label.setForeground(Color.WHITE);
            labels.put(s, label);
            label.setBounds(10, y, 100, 30);
            add(label);
            y += 40;
        }
        add(dayNameLabel);
        dayNameLabel.setBounds(10, 0, 100, 30);
        add(timeLabel);
        timeLabel.setBounds(10, 50, 100, 30);



    }


    void update(SummaryModel model) {

        dayNameLabel.setText(dayNameText + model.getDayName());
        timeLabel.setText(timeText + model.getHours() + ":" + model.getMinutes());
        for (String s : labelNames) {
            JLabel label = labels.get(s);
            String methodName = "get" + s;
            try {
                Method method = model.getClass().getDeclaredMethod(methodName);
                int i = (int) method.invoke(model);
                String n = Integer.toString(i);

                String[] description = s.split("(?=\\p{Upper})");

                String descr = description[0] + " " + description[1] + ": ";
                label.setText(descr + n);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }


        }
    }
}
