package model;

import model.task.Task;
import utils.DukeException;

import java.util.ArrayList;

public class ModelController implements Model {
    private TasksManager tasksManager;

    /**
     * Handles model changes.
     * */
    public ModelController() {
        //Create new project manager object, TODO change to loading from storage
        tasksManager = new TasksManager();

    }

    //================== Task CRUD operations ==============================
    @Override
    public void addTask(String name) throws DukeException {
        tasksManager.addTask(name);
    }

    /**
     * Currently exposes the tasklist as an ArrayList
     * */
    @Override
    public ArrayList<Task> getTaskList() { return tasksManager.getTaskList(); }

    /**
     * Delete a task
     * */
    @Override
    public Task deleteTask(String nameOfTask) throws DukeException  {
        return tasksManager.deleteTask(nameOfTask);
    }
}