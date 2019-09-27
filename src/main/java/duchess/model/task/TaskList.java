package duchess.model.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TaskList implements Serializable {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task get(int i) {
        return tasks.get(i);
    }

    public void delete(int i) {
        tasks.remove(i);
    }

    /**
     * Checks if a new task clashes with any existing tasks in tasklist.
     *
     * @param newTask the new task to be added
     * @return true if task is clashing, false otherwise
     */
    public boolean isClashing(Task newTask) {
        return this.getTasks().stream()
                .map(Task::getClashables)
                .flatMap(Collection::stream)
                .anyMatch(task -> task.clashesWith(newTask));
    }
}
