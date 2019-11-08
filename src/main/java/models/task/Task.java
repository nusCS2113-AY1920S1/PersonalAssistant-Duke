package models.task;

import java.util.UUID;
import util.date.DateTimeHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Task implements ITask {
    private String taskName;
    private int taskPriority;
    private Date dueDate;
    private int taskCredit;
    private TaskState taskState;
    private ArrayList<String> taskRequirements;
    private DateTimeHelper dateTimeHelper;
    private String taskID;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Task)) {
            return false;
        } else {
            Task other = (Task) obj;
            return this.taskID.equals(other.taskID);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.taskID);
    }

    /**
     * Class representing a task in a project.
     * @param taskName The name of the task.
     * @param taskPriority The priority value of the task.
     * @param dueDate The date that the task is due. [Optional]
     * @param taskCredit The amount of credit a person would receive for completing the task.
     *                   A more difficult task would receive more credit.
     * @param taskState taskState refers to whether the task is in OPEN, TO-DO, DOING, DONE.
     * @param taskRequirements ArrayList of Strings containing specific requirements of a task. [Optional]
     */
    public Task(String taskName, int taskPriority, Date dueDate, int taskCredit, TaskState taskState,
                ArrayList<String> taskRequirements) {
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.dueDate = dueDate;
        this.taskCredit = taskCredit;
        this.taskState = taskState;
        this.taskRequirements = taskRequirements;
        this.dateTimeHelper = new DateTimeHelper();
        this.taskID = UUID.randomUUID().toString();
    }

    public String getTaskName() {
        return taskName;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    /**
     * Gets the details of the task in a String format in the correct layout.
     * @return String containing all the details of the task.
     */
    public String getDetails() {
        if (this.dueDate != null) {
            return this.taskName + " | Priority: "
                    + this.taskPriority
                    + " | Due: " + dateTimeHelper.formatDateForDisplay(this.dueDate)
                    + dateTimeHelper.getDifferenceDays(this.dueDate)
                    + " | Credit: " + this.taskCredit
                    + " | State: " + this.taskState;
        } else {
            return this.taskName + " | Priority: "
                    + this.taskPriority + " | Due: -- | Credit: " + this.taskCredit + " | State: "
                    + this.taskState;
        }
    }

    /**
     * Method to get task details to be presented in a concise form for Assignment table.
     * @return String containing task details.
     */
    public String getDetailsForAssignmentTable() {
        if (this.dueDate != null) {
            return this.taskName + " (P: " + this.taskPriority + ", D: "
                    + dateTimeHelper.formatDateForDisplay(this.dueDate) + ", C: " + this.taskCredit + ", S: "
                    + this.taskState + ")";
        } else {
            return this.taskName + " (P: " + this.taskPriority + ", D: --, C: " + this.taskCredit + ", S: "
                    + this.taskState + ")";
        }

    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public TaskState getTaskState() {
        return this.taskState;
    }

    public int getTaskCredit() {
        return this.taskCredit;
    }

    /**
     * Adds index labels to task requirements for clearer viewing.
     * @return ArrayList of String of task requirements with labelled indexes.
     */
    public ArrayList<String> getTaskRequirements() {
        ArrayList<String> taskRequirements = new ArrayList<>();
        taskRequirements.add(0, "Requirements for the task '"
                + this.taskName + "':");
        int index = 1;
        for (String s : this.taskRequirements) {
            taskRequirements.add(index + ". " + s);
            index++;
        }
        return taskRequirements;
    }

    public int getNumOfTaskRequirements() {
        return this.taskRequirements.size();
    }

    public void setTaskName(String newTaskName) {
        this.taskName = newTaskName;
    }

    public void setTaskPriority(int newTaskPriority) {
        this.taskPriority = newTaskPriority;
    }

    /**
     * Set input Date object as the new dueDate.
     * @param newDueDate Date object of the new dueDate to be set.
     */
    public void setDueDate(Date newDueDate) {
        this.dueDate = newDueDate;
    }

    /**
     * Edits current task credit to new input task credit.
     * @param newTaskCredit new task credit to be set.
     */
    public void setTaskCredit(int newTaskCredit) {
        this.taskCredit = newTaskCredit;
    }

    /**
     * Converts input String into TaskState and edits current task state to new task state.
     * @param newTaskStateString String form of new task state.
     */
    public void setTaskState(String newTaskStateString) {
        switch (newTaskStateString) {
        case "done":
            this.taskState = TaskState.DONE;
            break;
        case "todo":
            this.taskState = TaskState.TODO;
            break;
        case "doing":
            this.taskState = TaskState.DOING;
            break;
        default:
            this.taskState = TaskState.OPEN;
            break;
        }
    }

    public void removeTaskRequirement(int indexOfTaskRequirement) {
        this.taskRequirements.remove(indexOfTaskRequirement - 1);
    }

    public void addTaskRequirement(String newTaskRequirement) {
        this.taskRequirements.add(newTaskRequirement);
    }
}
