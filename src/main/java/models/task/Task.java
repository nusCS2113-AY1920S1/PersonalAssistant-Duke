package models.task;

public class Task {
    private String taskName;
    private int taskPriority;

    /**
     * Class representing a task in a project.
     * @param taskName The name of the task.
     * @param taskPriority The priority value of the task.
     */
    public Task(String taskName, int taskPriority){
        this.taskName = taskName;
        this.taskPriority = taskPriority;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getTaskPriority(){
        return taskPriority;
    }

    public String getDetails() {
        return this.taskName + " Priority: "
                + this.taskPriority;
    }
}
