package duke.tasks;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> storeList;

    public TaskList() {
        storeList = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> storeList) {
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

    public ArrayList<Task> getAllTasks() {
        return storeList;
    }

    public int numTasks() {
        return storeList.size();
    }

}