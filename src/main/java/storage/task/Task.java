package storage.task;

import interpreter.Parser;

import java.time.LocalDate;
import java.time.LocalTime;

public class Task {
    protected String taskName;
    protected String taskDetails;
    protected String detailDesc;
    protected TaskType taskType;
    protected Boolean isDone = false;
    private TaskList queuedTasks = null;
    protected LocalDate date;
    protected LocalTime time;

    /**
     * Constructor for the 'Task' Class.
     * @param name Name of the task as inputted by the user
     */
    public Task(String name) {
        this.taskType = TaskType.BLANK;
        this.taskName = name.replace(this.taskType.name(), "").trim();
        this.date = LocalDate.now();
        this.time = null;
        this.isDone = false;
    }

    /**
     * Get a 'tick' or 'cross' depending on .isDone
     *
     * @return A special string that represents a tick for true or a cross for false
     */
    public String getStatusIcon() {
        if (this.isDone) {
            return "\u2713"; // / u2713
        } else {
            return "\u2718"; // / u2718
        }
    }

    /**
     * Sets a Task object's .isDone property to true.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Sets a Task object's .isDone property to false.
     */
    public void markNotDone() {
        this.isDone = false;
    }

    /**
     * Generates a String Describing the Task Object.
     * Optimized for user's reading.
     *
     * @return String detailing the Task Object, taskName and taskDetails
     */
    public String genTaskDesc() {
        String generatedStr = "";
        if (this.taskName != null) {
            generatedStr = this.taskName;
        }
        if (this.detailDesc != null || this.taskDetails != null) {
            generatedStr += " (";
            if (this.detailDesc != null && !this.detailDesc.isEmpty()) {
                generatedStr += this.detailDesc + ": ";
            }
            if (this.taskDetails != null && !this.taskDetails.isEmpty()) {
                generatedStr += this.taskDetails;
            }
            generatedStr += ")";
        }
        return generatedStr;
    }

    public String genTypeAcronym() {
        return this.taskType.name().substring(0, 1);
    }

    /**
     * Records the details from the user input into this Task object.
     * @param userInput The input taken from the user in CLI
     */
    public void recordTaskDetails(String userInput) {
        userInput = Parser.removeStr(this.taskType.name(), userInput);
        this.taskName = userInput;
        int indexBackslash = userInput.indexOf('/');
        //Check if '/' exists
        if (indexBackslash >= 0) {
            int indexMsg = userInput.indexOf(' ', indexBackslash);
            if (indexMsg >= 0) {
                this.detailDesc = userInput.substring(indexBackslash + 1, indexMsg);
            }
            String[] splitDetails = userInput.split('/' + this.detailDesc, 2);
            this.taskName = splitDetails[0].trim();
            if (splitDetails.length > 1) {
                this.taskDetails = splitDetails[1].trim();
            }
        }
    }

    /**
     * Initializes the Queue if it hasn't been initialized.
     */
    public void initializeQueue() {
        if (this.isQueuedTasks()) {
            return;
        }
        this.setQueuedTasks(new TaskList());
    }

    // -- Boolean Checkers

    /**
     * Checks if the current task has any queued tasks.
     * @return false if queuedTask property is null, true otherwise
     */
    public boolean isQueuedTasks() {
        if (this.queuedTasks == null) {
            return false;
        } else {
            return true;
        }
    }

    // -- Setters & Getters

    /**
     * Getter for taskName Property.
     * @return String containing the taskName of the Task
     */
    public String getTaskName() {
        return this.taskName;
    }

    /**
     * Setter for taskName Property.
     * @param taskName taskName to be set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Getter for detailDesc Property.
     * @return String containing the detailDesc of the Task
     */
    public String getDetailDesc() {
        return detailDesc;
    }

    /**
     * Setter for the detailDesc Property.
     * @param detailDesc detailDesc to be set
     */
    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    /**
     * Getter for the taskDetails Property.
     * @return String containing the taskDetails of the Task
     */
    public String getTaskDetails() {
        return taskDetails;
    }

    /**
     * Setter for the taskDetails Property.
     * @param taskDetails taskDetails to be set
     */
    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    /**
     * Getter for the taskType Property.
     * @return TaskType of the taskType Property.
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * Setter for the taskType Property.
     * @param taskType TaskType Enum to be set
     */
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    /**
     * Getter for the isDone Property.
     * @return Boolean representing the isDone Property
     */
    public Boolean getIsDone() {
        return isDone;
    }

    /**
     * Getter for queuedTask Property.
     *
     * @return ArrayList containing Task Objects
     */
    public TaskList getQueuedTasks() {
        return queuedTasks;
    }

    /**
     * Setter for queued Task Property.
     *
     * @param queuedTasks ArrayList to be set
     */
    public void setQueuedTasks(TaskList queuedTasks) {
        this.queuedTasks = queuedTasks;
    }

    /**
     * Setter for datetime property.
     *
     * @param date The Date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Getter for datetime property.
     *
     * @return Date represented by the datetime property
     */
    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
