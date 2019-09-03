package repositories;

import models.ITask;

import java.util.ArrayList;

public interface IRepository {
    ArrayList<ITask> getAllTasks();
    void addToRepo();
    ITask getTask();
    int getNumTasks();
    void markDone();
}
