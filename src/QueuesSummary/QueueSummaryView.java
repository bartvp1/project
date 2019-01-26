package QueuesSummary;

import MyComponents.MyLabel;

import javax.swing.*;
import java.awt.*;

public class QueueSummaryView extends JPanel {
    JProgressBar entranceQueue = new JProgressBar(JProgressBar.VERTICAL);
    JProgressBar exitQueue = new JProgressBar(JProgressBar.VERTICAL);
    JProgressBar paymentQueue = new JProgressBar(JProgressBar.VERTICAL);
    JLabel entranceQueueLabel = new JLabel("", JLabel.CENTER);
    JLabel exitQueueLabel = new JLabel("", JLabel.CENTER);
    JLabel paymentQueueLabel = new JLabel("", JLabel.CENTER);

    public QueueSummaryView() {
        super(null);
    }

    public void init() {
        setBackground(new Color(47, 49, 54));
        MyLabel titleLabel = new MyLabel("Car Queues", JLabel.CENTER, "Title");

        titleLabel.setBounds(0, 0, getWidth(), 50);
//        titleLabel.setFont(new Font("Dubai Light", Font.PLAIN, 25));
//        titleLabel.setForeground(Color.WHITE);
        add(titleLabel);

        JPanel pBarPanel = new JPanel(new GridLayout(1, 7, 50, 0));
        JPanel labelPanel = new JPanel(new GridLayout(1, 7, 50, 0));

        pBarPanel.setOpaque(false);
        pBarPanel.setBounds(getWidth() / 4, getHeight() - 50 - 135 - 50, getWidth() / 2, 150);

        labelPanel.setOpaque(false);
        labelPanel.setBounds(getWidth() / 4, getHeight() - 50 - 25, getWidth() / 2, 50);
        labelPanel.setBackground(Color.GREEN);

        entranceQueueLabel.setForeground(Color.WHITE);
        exitQueueLabel.setForeground(Color.WHITE);
        paymentQueueLabel.setForeground(Color.WHITE);

        entranceQueue.setForeground(Color.RED);

        pBarPanel.add(entranceQueue);
        pBarPanel.add(exitQueue);
        pBarPanel.add(paymentQueue);
        add(pBarPanel);

        labelPanel.add(entranceQueueLabel);
        labelPanel.add(exitQueueLabel);
        labelPanel.add(paymentQueueLabel);
        add(labelPanel);

        repaint();
    }

    public void update(QueueSummaryModel model) {
        entranceQueue.setMaximum(model.getMaxEntranceSize());
        exitQueue.setMaximum(model.getMaxExitSize());
        paymentQueue.setMaximum(model.getMaxPaymentSize());

        entranceQueue.setValue(model.getCurrentEntranceSize());
        exitQueue.setValue(model.getCurrentExitSize());
        paymentQueue.setValue(model.getCurrentPaymentSize());

        entranceQueueLabel.setText(Integer.toString(model.getCurrentEntranceSize()) + "/" + Integer.toString(model.getMaxEntranceSize()));
        exitQueueLabel.setText(Integer.toString(model.getCurrentExitSize()) + "/" + Integer.toString(model.getMaxExitSize()));
        paymentQueueLabel.setText(Integer.toString(model.getCurrentPaymentSize()) + "/" + Integer.toString(model.getMaxPaymentSize()));

        repaint();

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
}
