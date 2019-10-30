package model;

import model.task.ToDo;

public interface Model {
    /**
     * Methods listed here detail CRUD create read update delete functions for the model. The following is an example
     * of a create function
     * */
    void addToDo(ToDo todo);
}
