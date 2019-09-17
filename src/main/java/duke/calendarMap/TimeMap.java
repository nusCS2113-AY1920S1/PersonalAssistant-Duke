package duke.calendarMap;

import duke.task.Task;

import java.util.TreeMap;

public class TimeMap extends TreeMap<String, Task> {


    public Task remove(String key, Task task) {
        if (this.get(key) != null) {
            super.remove(key);
            return task;
        }
        return null;
    }

    public Task put(String key, Task task) {
        if (this.get(key) == null) {
            super.put(key, task);
            return task;
        }
        return null;
    }
}
