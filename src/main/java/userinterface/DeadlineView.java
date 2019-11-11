package userinterface;

import javafx.scene.text.Text;

/**
 * Class for deadline table to store and display.
 */
public class DeadlineView {
    private Text date;
    private Text task;
    private Text overDays;

    /**
     * This creates DeadlineView object.
     */
    public DeadlineView(Text task, Text date, Text overDays) {
        this.date = date;
        this.task = task;
        this.overDays = overDays;
    }

    public Text getDate() {
        return date;
    }

    public void setDate(Text date) {
        this.date = date;
    }

    public Text getTask() {
        return task;
    }

    public void setTask(Text task) {
        this.task = task;
    }

    public Text getOverDays() {
        return overDays;
    }
}
