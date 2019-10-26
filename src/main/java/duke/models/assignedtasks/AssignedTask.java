package duke.models.assignedtasks;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class AssignedTask {
    private SimpleIntegerProperty uuid = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty patientId;
    private SimpleIntegerProperty taskId;
    private SimpleBooleanProperty isDone = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty isRecursive = new SimpleBooleanProperty(false);
    private SimpleStringProperty taskType;
    private SimpleStringProperty todoDateRaw = new SimpleStringProperty("null");
    private SimpleStringProperty startDateRaw = new SimpleStringProperty("null");
    private SimpleStringProperty endDateRaw = new SimpleStringProperty("null");
    private LocalDateTime todoDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    /**
     * .
     *
     * @param pid  .
     * @param tid  .
     * @param type .
     */
    public AssignedTask(int pid, int tid, String type) {
        this.patientId = new SimpleIntegerProperty(pid);
        this.taskId = new SimpleIntegerProperty(tid);
        this.taskType = new SimpleStringProperty(type);
    }

    /**
     * .
     *
     * @param pid          .
     * @param tid          .
     * @param isdone       .
     * @param isrecurrsive .
     * @param type         .
     */
    public AssignedTask(int pid, int tid, boolean isdone, boolean isrecurrsive, String type) {
        this.patientId = new SimpleIntegerProperty(pid);
        this.taskId = new SimpleIntegerProperty(tid);
        this.taskType = new SimpleStringProperty(type);
        this.isDone = new SimpleBooleanProperty(isdone);
        this.isRecursive = new SimpleBooleanProperty(isrecurrsive);
    }

    /**
     * .
     *
     * @param uuid .
     */
    public void setUuid(int uuid) {
        this.uuid.set(uuid);
    }

    /**
     * .
     */
    public int getUuid() {
        return this.uuid.get();
    }

    /**
     * .
     */
    public String getTodoDateRaw() {
        return this.todoDateRaw.get();
    }

    /**
     * .
     */
    public void setTodoDateRaw(String todoDateRaw) {
        this.todoDateRaw.set(todoDateRaw);
    }

    /**
     * .
     */
    public LocalDateTime getTodoDate() {
        return this.todoDate;
    }

    /**
     * .
     */
    public void setTodoDate(LocalDateTime todoDate) {
        this.todoDate = todoDate;
    }


    /**
     * .
     */
    public String getStartDateRaw() {
        return this.startDateRaw.get();
    }

    /**
     * .
     */
    public void setStartDateRaw(String startDateRaw) {
        this.startDateRaw.set(startDateRaw);
    }

    /**
     * .
     */
    public LocalDateTime getStartDate() {
        return this.startDate;
    }


    /**
     * .
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * .
     */
    public String getEndDateRaw() {
        return this.endDateRaw.get();
    }

    /**
     * .
     */
    public void setEndDateRaw(String endDateRaw) {
        this.endDateRaw.set(endDateRaw);
    }

    /**
     * .
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * .
     */
    public LocalDateTime getEndDate() {
        return this.endDate;
    }


    /**
     * .
     *
     * @return .
     */
    public int getPid() {
        return patientId.get();
    }

    /**
     * Get
     */


    /**
     * .
     */
    public void setPid(int patientId) {
        this.patientId.set(patientId);
    }

    /**
     * .
     *
     * @return .
     */
    public int getTid() {
        return this.taskId.get();
    }


    /**
     * .
     *
     * @param taskId .
     */
    public void setTid(int taskId) {
        this.taskId.set(taskId);
    }

    /**
     * .
     *
     * @return .
     */
    public String getType() {
        return taskType.get();
    }


    /**
     * .
     *
     * @return .
     */
    public boolean getIsDone() {
        return this.isDone.get();
    }

    /**
     * .
     *
     * @return .
     */
    public boolean getIsRecursive() {
        return isRecursive.get();
    }

    /**
     * .
     */
    public void markDone() {
        this.isDone.set(true);
    }

    /**
     * .
     */
    public void undoIsDone() {
        this.isDone.set(false);
    }

    /**
     * .
     */
    public void markRecur() {
        this.isRecursive.set(true);
    }

    /**
     * .
     */
    public void undoRecur() {
        this.isRecursive.set(false);
    }

    /**
     * .
     *
     * @return .
     */
    public String getStatusIcon() {
        return (isDone.get() ? "\u2713" : "\u2718"); // unicode icon
    }

    /**
     * .
     *
     * @return .
     */
    public String getRecursiveIcon() {
        return (isRecursive.get() ? "\u0298" : "\u0275"); // unicode icon
    }

    /**
     * .
     *
     * @return .
     */
    public String printStatus() {
        return " Unique ID " + uuid.get() + " " + "["
            + this.getStatusIcon() + "] " + "[" + this.getRecursiveIcon() + "] ";
    }


    /**
     * .
     *
     * @return .
     */
    public abstract String toString();
}
