package model;

import model.task.Task;

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
    public void addTask(Task task) {
        tasksManager.addTask(task);
    }

    @Override
    public ArrayList<Task> getTaskList() { return tasksManager.getTasks(); }
}
