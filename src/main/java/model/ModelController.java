package model;

import model.task.Task;

import java.util.ArrayList;

public class ModelController implements Model {
    private ProjectManager projectManager;

    /**
     * Handles model changes.
     * */
    public ModelController() {
        //Create new project manager object, TODO change to loading from storage
        projectManager = new ProjectManager();

    }

    @Override
    public void addTask(Task task) {
        projectManager.addTask(task);
    }

    @Override
    public ArrayList<Task> getTaskList() { return projectManager.getTasks(); }
}
