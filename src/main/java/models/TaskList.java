package models;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<ITask> listOfTasks;

    public TaskList() {
        listOfTasks = new ArrayList<>();
    }

    public void addToList(ITask newTask) {
        this.listOfTasks.add(newTask);
    }

    public ArrayList<ITask> getAllTasks() {
        return this.listOfTasks;
    }

    public ITask getTask(int index) {
        return this.listOfTasks.get(index);
    }

    public int getNumOfTasks() {
        return this.listOfTasks.size();
    }
}
