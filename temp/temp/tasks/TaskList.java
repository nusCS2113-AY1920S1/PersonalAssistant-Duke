package duke.tasks;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> storeList;

    public TaskList() {
        storeList = new ArrayList<>();
    }

    public TaskList(List<Task> storeList) {
        this.storeList = storeList;
    }

    public void addTask(Task task) {
        this.storeList.add(task);
    }

    public void changeTask(Task task, int position) {
        this.storeList.add(position,task);
    }

    public void deleteTask(int index) {
        this.storeList.remove(index);
    }

    public Task getTask(int index) {
        return this.storeList.get(index);
    }

    public List<Task> getAllTasks() {
        return storeList;
    }

    public int numTasks() {
        return storeList.size();
    }

    @JsonGetter("tasks")
    public List<Task> getStoreList() {
        return storeList;
    }

    @JsonSetter("tasks")
    public void setStoreList(List<Task> storeList) {
        this.storeList = storeList;
    }
}