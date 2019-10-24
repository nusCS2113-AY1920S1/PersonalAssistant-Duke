package model;

import model.task.Task;
import model.task.TaskList;

import java.io.Serializable;
import java.util.ArrayList;

public class TasksManager implements Serializable {
    //TODO ensure tasks are unique, condition is that no two task can have same name. Link storage
    TaskList taskList;

    public TasksManager() {
        this.taskList = new TaskList(new ArrayList<Task>());
    }

    /**
     * Basic adding function of task, this can be extended very widely such as including input checks in the
     * individual data model, however this is the prototype model
     */
    public void addTask(Task task) {
        taskList.add(task);
    }

    /**
     * Returns arraylist of tasks. However we can consider building a model for task list itself
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

}
