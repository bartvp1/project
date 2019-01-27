package QueuesSummary;

import MyComponents.Model;
import MyComponents.View;

public class QueueSummaryModel extends Model {
    private int maxEntranceSize;
    private int currentEntranceSize;
    private int maxPaymentSize;
    private int currentPaymentSize;
    private int maxExitSize;
    private int currentExitSize;
    private View view;

    public QueueSummaryModel(View view) {
        this.view = view;
    }

    public void update() {
        QueueSummaryView qView = (QueueSummaryView) view;
        qView.update(this);
    }

    int getMaxEntranceSize() {
        return maxEntranceSize;
    }

    public void setMaxEntranceSize(int maxEntranceSize) {
        this.maxEntranceSize = maxEntranceSize;
    }

    int getCurrentEntranceSize() {
        return currentEntranceSize;
    }

    public void setCurrentEntranceSize(int currentEntranceSize) {
        this.currentEntranceSize = currentEntranceSize;
    }

    int getMaxPaymentSize() {
        return maxPaymentSize;
    }

    public void setMaxPaymentSize(int maxPaymentSize) {
        this.maxPaymentSize = maxPaymentSize;
    }

    int getCurrentPaymentSize() {
        return currentPaymentSize;
    }

    public void setCurrentPaymentSize(int currentPaymentSize) {
        this.currentPaymentSize = currentPaymentSize;
    }

    int getMaxExitSize() {
        return maxExitSize;
    }

    public void setMaxExitSize(int maxExitSize) {
        this.maxExitSize = maxExitSize;
    }

    int getCurrentExitSize() {
        return currentExitSize;
    }

    public void setCurrentExitSize(int currentExitSize) {
        this.currentExitSize = currentExitSize;
    }
}
