package QueuesSummary;

import MyComponents.Model;
import MyComponents.MyLabel;
import MyComponents.View;

import javax.swing.*;
import java.awt.*;

public class QueueSummaryView extends View {
    private JProgressBar entranceQueue = new JProgressBar(JProgressBar.VERTICAL);
    private JProgressBar exitQueue = new JProgressBar(JProgressBar.VERTICAL);
    private JProgressBar paymentQueue = new JProgressBar(JProgressBar.VERTICAL);

    private JLabel entranceQueueLabel = new JLabel("", JLabel.CENTER);
    private JLabel exitQueueLabel = new JLabel("", JLabel.CENTER);
    private JLabel paymentQueueLabel = new JLabel("", JLabel.CENTER);

    public void init() {
        setBackground(new Color(47, 49, 54));
        MyLabel titleLabel = new MyLabel("Car Queues", JLabel.CENTER, "Title");

        titleLabel.setBounds(0, 0, getWidth(), 50);
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


    @Override
    protected void update(Model model) {
        QueueSummaryModel qModel = (QueueSummaryModel) model;

        entranceQueue.setMaximum(qModel.getMaxEntranceSize());
        exitQueue.setMaximum(qModel.getMaxExitSize());
        paymentQueue.setMaximum(qModel.getMaxPaymentSize());

        entranceQueue.setValue(qModel.getCurrentEntranceSize());
        exitQueue.setValue(qModel.getCurrentExitSize());
        paymentQueue.setValue(qModel.getCurrentPaymentSize());

        entranceQueueLabel.setText(Integer.toString(qModel.getCurrentEntranceSize()) + "/" + Integer.toString(qModel.getMaxEntranceSize()));
        exitQueueLabel.setText(Integer.toString(qModel.getCurrentExitSize()) + "/" + Integer.toString(qModel.getMaxExitSize()));
        paymentQueueLabel.setText(Integer.toString(qModel.getCurrentPaymentSize()) + "/" + Integer.toString(qModel.getMaxPaymentSize()));

        repaint();
    }
}
