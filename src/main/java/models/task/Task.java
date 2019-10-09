package models.task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private String taskName;
    private int taskPriority;
    private Date dueDate;
    private int taskCredit;
    private State state;


    /**
     * Class representing a task in a project.
     * @param taskName The name of the task.
     * @param taskPriority The priority value of the task.
     * @param dueDate The date that the task is due.
     * @param taskCredit The amount of credit a person would receive for completing the task.
     *                   A more difficult task would receive more credit.
     * @param state State refers to whether the task is in OPEN, TO-DO, DOING, DONE. 
     */
    public Task(String taskName, int taskPriority, Date dueDate, int taskCredit, State state) {
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.dueDate = dueDate;
        this.taskCredit = taskCredit;
        this.state = state;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public String getDueDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        return formatter.format(this.dueDate);
    }

    public String getDetails() {
        if (this.dueDate != null) {
            return this.taskName + " | Priority: "
                    + this.taskPriority + " | Due: " + this.getDueDateString() + " | Credit: "
                    + this.taskCredit + " | State: " + this.state;
        } else {
            return this.taskName + " | Priority: "
                    + this.taskPriority + " | Due: -- | Credit: " + this.taskCredit + " | State: "
                    + this.state;
        }
    }
}
