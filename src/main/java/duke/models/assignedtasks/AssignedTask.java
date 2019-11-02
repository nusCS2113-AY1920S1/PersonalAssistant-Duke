//@@author WEIFENG-NUSCEG

package duke.models.assignedtasks;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a Assigned task that has been assigned to a patient with a unique ID.
 */
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
     * Create a new AssignedTask Object with patient's id, task's id and the type of the task.
     *
     * @param pid  Patient's Id
     * @param tid  Task's Id
     * @param type Type of the assigned task (deadline or period)
     */
    public AssignedTask(int pid, int tid, String type) {
        this.patientId = new SimpleIntegerProperty(pid);
        this.taskId = new SimpleIntegerProperty(tid);
        this.taskType = new SimpleStringProperty(type);
    }

    /**
     * Create a new AssignedTask Object with patient's id, task's id, the type of the task,
     * the is done flag and is recursive flg.
     *
     * @param pid          Patient's Id
     * @param tid          Task's Id
     * @param isdone       A boolean flag to reflect whether the task is done
     * @param isrecurrsive A flag to reflect whether the task is recursive
     * @param type         Type of the assigned task (deadline or period)
     */
    public AssignedTask(int pid, int tid, boolean isdone, boolean isrecurrsive, String type) {
        this.patientId = new SimpleIntegerProperty(pid);
        this.taskId = new SimpleIntegerProperty(tid);
        this.taskType = new SimpleStringProperty(type);
        this.isDone = new SimpleBooleanProperty(isdone);
        this.isRecursive = new SimpleBooleanProperty(isrecurrsive);
    }

    /**
     * Create a new AssignedTask Object with patient's id, task's id, the type of the task,
     * the is done flag and is recursive flg, and its unique id.
     *
     * @param pid          A Patient's Id
     * @param tid          A Task's Id
     * @param isdone       A boolean flag to reflect whether the task is done
     * @param isrecurrsive A flag to reflect whether the task is recursive
     * @param type         Type of the assigned task (deadline or period)
     * @param uid          Unique id of the task
     */
    public AssignedTask(int pid, int tid, boolean isdone, boolean isrecurrsive, String type, int uid) {
        this.patientId = new SimpleIntegerProperty(pid);
        this.taskId = new SimpleIntegerProperty(tid);
        this.taskType = new SimpleStringProperty(type);
        this.isDone = new SimpleBooleanProperty(isdone);
        this.isRecursive = new SimpleBooleanProperty(isrecurrsive);
        this.uuid = new SimpleIntegerProperty(uid);
    }


    /**
     * Set the unique id to the assigned task.
     *
     * @param uuid unique id of the task
     */
    public void setUuid(int uuid) {
        this.uuid.set(uuid);
    }

    /**
     * Get the unique id from assigned task.
     *
     * @return the unique id of assigned task
     */
    public int getUuid() {
        return this.uuid.get();
    }

    /**
     * Get the deadline before formatting from assigned task.
     *
     * @return the deadline before formatting of assigned task
     */
    public String getTodoDateRaw() {
        return this.todoDateRaw.get();
    }

    /**
     * Set the deadline before formatting to assigned task.
     *
     * @param todoDateRaw deadline time before formatting
     */
    public void setTodoDateRaw(String todoDateRaw) {
        this.todoDateRaw.set(todoDateRaw);
    }

    /**
     * Get the deadline in LocalDateTime from assigned task.
     *
     * @return the deadline in LocalDateTime of assigned task
     */
    public LocalDateTime getTodoDate() {
        return this.todoDate;
    }

    /**
     * Set the deadline in LocalDateTime to assigned task.
     *
     * @param todoDate deadline in LocalDateTime of assigned task
     */
    public void setTodoDate(LocalDateTime todoDate) {
        this.todoDate = todoDate;
    }


    /**
     * Get the start time before formatting from assigned task.
     *
     * @return the start time before formatting from assigned task
     */
    public String getStartDateRaw() {
        return this.startDateRaw.get();
    }

    /**
     * Set the start time before formatting to assigned task.
     *
     * @param startDateRaw deadline time before formatting
     */
    public void setStartDateRaw(String startDateRaw) {
        this.startDateRaw.set(startDateRaw);
    }

    /**
     * Get the start time in LocalDateTime from assigned task.
     *
     * @return the start time in LocalDateTime of assigned task
     */
    public LocalDateTime getStartDate() {
        return this.startDate;
    }


    /**
     * Set the start time in LocalDateTime to assigned task.
     *
     * @param startDate deadline in LocalDateTime of assigned task
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the end time before formatting from assigned task.
     *
     * @return the end time before formatting from assigned task
     */
    public String getEndDateRaw() {
        return this.endDateRaw.get();
    }

    /**
     * Set the end time before formatting to assigned task.
     *
     * @param endDateRaw deadline time before formatting
     */
    public void setEndDateRaw(String endDateRaw) {
        this.endDateRaw.set(endDateRaw);
    }

    /**
     * Set the end time in LocalDateTime to assigned task.
     *
     * @param endDate deadline in LocalDateTime of assigned task
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Get the end time in LocalDateTime from assigned task.
     *
     * @return the end time in LocalDateTime of assigned task
     */
    public LocalDateTime getEndDate() {
        return this.endDate;
    }


    /**
     * Retrieve the patient ID from assigned task.
     *
     * @return patient id
     */
    public int getPid() {
        return patientId.get();
    }

    /**
     * Set the patient id in assigned task.
     *
     * @param patientId the patient's id
     */
    public void setPid(int patientId) {
        this.patientId.set(patientId);
    }

    /**
     * Retrieve the task id from assigned task.
     *
     * @return the task id
     */
    public int getTid() {
        return this.taskId.get();
    }


    /**
     * Set the task id in assigned task.
     *
     * @param taskId the task's id
     */
    public void setTid(int taskId) {
        this.taskId.set(taskId);
    }

    /**
     * Retrieve the task type.
     *
     * @return name of the task type
     */
    public String getType() {
        return taskType.get();
    }


    /**
     * Check whether the task is done.
     *
     * @return a bool indicates done or not yet
     */
    public boolean getIsDone() {
        return this.isDone.get();
    }

    /**
     * Check whether the task is recursive.
     *
     * @returna bool indicates recursive or not
     */
    public boolean getIsRecursive() {
        return isRecursive.get();
    }

    /**
     * Set the task as done.
     */
    public void markDone() {
        this.isDone.set(true);
    }

    /**
     * Set the task as not done yet.
     */
    public void undoIsDone() {
        this.isDone.set(false);
    }

    /**
     * Set the task as a recursive task.
     */
    public void markRecur() {
        this.isRecursive.set(true);
    }

    /**
     * Set the task as a non-recursive task.
     */
    public void undoRecur() {
        this.isRecursive.set(false);
    }

    /**
     * Get the status icon of the assigned task.
     *
     * @return a string contains of a icon indicates isDone
     */
    public String getStatusIcon() {
        return (isDone.get() ? "\u2713" : "\u2718"); // unicode icon
    }

    /**
     * Get the recursive icon of the assigned task.
     *
     * @return a string contains of a icon indicates isRecursive
     */
    public String getRecursiveIcon() {
        return (isRecursive.get() ? "\u0298" : "\u0275"); // unicode icon
    }

    /**
     * Get the unique ID, status icon and recursive icon.
     *
     * @return a string contains the unique ID, status icon and recursive icon
     */
    public String printStatus() {
        return " Unique ID " + uuid.get() + " " + "["
            + this.getStatusIcon() + "] " + "[" + this.getRecursiveIcon() + "] ";
    }


    /**
     * Convert the important information of assigned task to a string.
     *
     * @return a string with important information of assigned task
     */
    public abstract String toString();
}
