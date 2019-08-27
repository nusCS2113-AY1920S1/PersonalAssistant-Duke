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

    public void showAllTasks() {
        for (int i = 0; i < this.listOfTasks.size(); i++) {
            System.out.print(i + 1);
            System.out.println(".[" + this.listOfTasks.get(i).getInitials() + "]"
                    + "[" + this.listOfTasks.get(i).getStatusIcon() + "] "
                    + this.listOfTasks.get(i).getDescription()
            );
        }
    }

    public ITask getTask(int index) {
        return this.listOfTasks.get(index);
    }

    public int getNumOfTasks() {
        return this.listOfTasks.size();
    }
}
