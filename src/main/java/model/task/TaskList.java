package model.task;


import java.io.Serializable;
import java.util.ArrayList;

public class TaskList implements Serializable {
    private ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public void add(Task task){
        taskList.add(task);
    }

    public ArrayList<Task> getTaskArrayList(){
        return taskList;
    }

}
