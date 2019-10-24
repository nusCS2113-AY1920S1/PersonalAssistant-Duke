package model;

import model.task.Task;

import java.util.ArrayList;

public interface Model {
    /**
     * Methods listed here detail CRUD create read update delete functions for the model. The following is an example
     * of a create function
     * */
    void addTask(Task task);

    ArrayList<Task> getTaskList();
}
