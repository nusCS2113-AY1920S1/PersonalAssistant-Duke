package javafx;

import javafx.beans.property.SimpleStringProperty;

public class TaskFX {
    private final SimpleStringProperty number = new SimpleStringProperty("");
    private final SimpleStringProperty isDone = new SimpleStringProperty("");
    private final SimpleStringProperty taskType = new SimpleStringProperty("");
    private final SimpleStringProperty description = new SimpleStringProperty("");
    private final SimpleStringProperty deadline = new SimpleStringProperty("");

    public TaskFX() {
        this("", "", "", "", "");
    }

    public TaskFX(String number, String isDone, String taskType, String description, String deadline) {
        setNumber(number);
        setIsDone(isDone);
        setTaskType(taskType);
        setDeadline(deadline);
        setDescription(description);
    }

    public String getNumber() {
        return number.get();
    }

    public void setNumber(String input) {
        number.set(input);
    }

    public String getIsDone() {
        return isDone.get();
    }

    public void setIsDone(String input) {
        isDone.set(input);
    }

    public String getTaskType() {
        return taskType.get();
    }

    public void setTaskType(String input) {
        taskType.set(input);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String input) {
        description.set(input);
    }

    public String getDeadline() {
        return deadline.get();
    }

    public void setDeadline(String input) {
        deadline.set(input);
    }

}
