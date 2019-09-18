package duke;

import duke.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Task addTask(Task task) {
        tasks.add(task);
        return task;
    }

    public Task removeTask(int index)throws IndexOutOfBoundsException {
        return tasks.remove(index);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task markDone(int index) throws IndexOutOfBoundsException {
        tasks.get(index).markDone();
        return tasks.get(index);
    }
}
