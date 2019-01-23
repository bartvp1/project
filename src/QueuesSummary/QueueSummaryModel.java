package QueuesSummary;

public class QueueSummaryModel {
    int maxEntranceSize;
    int currentEntranceSize;
    int maxPaymentSize;
    int currentPaymentSize;
    int maxExitSize;
    int currentExitSize;
    QueueSummaryView view;

    public QueueSummaryModel(QueueSummaryView view) {
        this.view = view;
    }

    public void update(){
        this.view.update(this);
    }

    public int getMaxEntranceSize() {
        return maxEntranceSize;
    }

    public void setMaxEntranceSize(int maxEntranceSize) {
        this.maxEntranceSize = maxEntranceSize;
    }

    public int getCurrentEntranceSize() {
        return currentEntranceSize;
    }

    public void setCurrentEntranceSize(int currentEntranceSize) {
        this.currentEntranceSize = currentEntranceSize;
    }

    public int getMaxPaymentSize() {
        return maxPaymentSize;
    }

    public void setMaxPaymentSize(int maxPaymentSize) {
        this.maxPaymentSize = maxPaymentSize;
    }

    public int getCurrentPaymentSize() {
        return currentPaymentSize;
    }

    public void setCurrentPaymentSize(int currentPaymentSize) {
        this.currentPaymentSize = currentPaymentSize;
    }

    public int getMaxExitSize() {
        return maxExitSize;
    }

    public void setMaxExitSize(int maxExitSize) {
        this.maxExitSize = maxExitSize;
    }

    public int getCurrentExitSize() {
        return currentExitSize;
    }

    public void setCurrentExitSize(int currentExitSize) {
        this.currentExitSize = currentExitSize;
    }
}
