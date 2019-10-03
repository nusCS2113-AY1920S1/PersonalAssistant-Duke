package dolla.task;

import java.util.ArrayList;

public class TaskList {
    protected ArrayList<Task> list;

    public TaskList(ArrayList<Task> importTaskList) {
        this.list = importTaskList;
    }

    public ArrayList<Task> get() {
        return list;
    }

    public void add(Task newTask) {
        list.add(newTask);
    }

    public int size() {
        return list.size();
    }

    public Task getFromList(int index) {
        return list.get(index);
    }

    public void removeFromList(int index) {
        list.remove(index);
    }

    public void replaceTask(int index, Task newTask) {
        list.set(index, newTask);
    }
}
