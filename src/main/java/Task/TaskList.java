package Task;

import Task.Task;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task>{

    public TaskList() {
    }
    public void addTask(Task task) {
        this.add(task);
    }
}
