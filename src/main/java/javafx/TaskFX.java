package javafx;

import javafx.beans.property.SimpleStringProperty;

/**
 * javafx class to display task data in a tabular format in the GUI.
 * Each TaskFX object is a task with names, status, type, description and optional deadline.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.3
 */
public class TaskFX {
    private final SimpleStringProperty number = new SimpleStringProperty("");
    private final SimpleStringProperty isDone = new SimpleStringProperty("");
    private final SimpleStringProperty taskType = new SimpleStringProperty("");
    private final SimpleStringProperty description = new SimpleStringProperty("");
    private final SimpleStringProperty deadline = new SimpleStringProperty("");
    private final SimpleStringProperty priority = new SimpleStringProperty("");

    /**
     * Empty constructor method.
     */
    public TaskFX() {
        this("", "", "", "", "", "");
    }

    /**
     * Constructor method for task data.
     * Takes in important data to be displayed in the table view taskView
     *
     * @param number The numerical ID of the task as a string.
     * @param isDone The status of the task.
     * @param taskType The type of task.
     * @param description The description of the task.
     * @param deadline The deadline of the task, if there is any.
     */
    public TaskFX(String number, String isDone, String taskType, String description, String deadline, String priority) {
        setNumber(number);
        setIsDone(isDone);
        setTaskType(taskType);
        setDeadline(deadline);
        setDescription(description);
        setPriority(priority);
    }

    /**
     * Returns the numerical ID of the task.
     * Required to be used by tableview.
     *
     * @return The numerical ID of the task as a string.
     */
    public String getNumber() {
        return number.get();
    }

    /**
     * Sets the numerical ID of this TaskFX object
     *
     * @param input The numerical ID of this task as a string, used in the constructor
     */
    public void setNumber(String input) {
        number.set(input);
    }

    /**
     * Returns the status of the task.
     * Required to be used by tableview.
     *
     * @return The status of the task as a string.
     */
    public String getIsDone() {
        return isDone.get();
    }

    /**
     * Sets the status of this TaskFX object
     *
     * @param input The status of this task as a string, used in the constructor
     */
    public void setIsDone(String input) {
        isDone.set(input);
    }

    /**
     * Returns the task type of the task.
     * Required to be used by tableview.
     *
     * @return The task type of the task as a string.
     */
    public String getTaskType() {
        return taskType.get();
    }

    /**
     * Sets the task type of this TaskFX object
     *
     * @param input The task type of this task as a string, used in the constructor
     */
    public void setTaskType(String input) {
        taskType.set(input);
    }

    /**
     * Returns the description of the task.
     * Required to be used by tableview.
     *
     * @return The description of the task as a string.
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Sets the description of this TaskFX object
     *
     * @param input The description of this task as a string, used in the constructor
     */
    public void setDescription(String input) {
        description.set(input);
    }

    /**
     * Returns the deadline of the task.
     * Required to be used by tableview.
     *
     * @return The deadline of the task as a string.
     */
    public String getDeadline() {
        return deadline.get();
    }

    /**
     * Sets the deadline of this TaskFX object
     *
     * @param input The deadline of this task as a string, used in the constructor
     */
    public void setDeadline(String input) {
        deadline.set(input);
    }


    /**
     * Returns the priority of this TaskFX object
     * @return
     */
    public String getPriority() {
        return priority.get();
    }


    /**
     * Sets the priority of this TaskFX object
     * @param priority
     */
    public void setPriority(String priority) {
        this.priority.set(priority);
    }
}
