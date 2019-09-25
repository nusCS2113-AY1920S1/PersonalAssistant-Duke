package duchess.storage.task;

import java.io.Serializable;
import java.util.ArrayList;
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
     * Checks if a new event clashes with any existing events in tasklist.
     *
     * @param newEvent the new event to be added
     * @return true if event is clashing, false otherwise
     */
    public boolean isClashing(Event newEvent) {
        List<Event> events = new ArrayList<>();
        for (Task task : this.tasks) {
            if (task instanceof Event) {
                events.add((Event) task);
            }
        }
        return events.stream().anyMatch(event -> event.clashesWith(newEvent));
    }


}
