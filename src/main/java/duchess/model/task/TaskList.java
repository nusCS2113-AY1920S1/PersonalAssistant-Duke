package duchess.model.task;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
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

    @JsonGetter("tasks")
    public List<Task> getTasks() {
        return this.tasks;
    }

    @JsonSetter("tasks")
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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
                .anyMatch(task -> task.clashesWith(newTask));
    }
}
