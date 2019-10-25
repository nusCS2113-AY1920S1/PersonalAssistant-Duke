package model;

import model.task.Task;
import utils.DukeException;

import java.util.ArrayList;

public interface Model {
    /**
     * Methods listed here detail CRUD create read update delete functions for the model. The following is an example
     * of a create function
     * */
    //==================CRUD for Task======================

    void addTask(String name) throws DukeException;

    ArrayList<Task> getTaskList();

    Task deleteTask(String nameOfTask) throws DukeException;
}
