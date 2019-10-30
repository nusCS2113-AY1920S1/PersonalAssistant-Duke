package model;

import model.task.Task;
import model.task.ToDo;

import java.io.Serializable;
import java.util.ArrayList;

public class ProjectManager implements Serializable {
    ArrayList<Task> tasks;

    public ProjectManager() {
        tasks = new ArrayList<Task>();
    }

    /**
     * Basic adding function of task, this can be extended very widely such as including input checks in the
     * individual data model, however this is the prototype model
     */
    public void addTodo(ToDo todo) {
        tasks.add(todo);
    }

    /**
     * Returns arraylist of tasks. However we can consider building a model for task list itself
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

}
