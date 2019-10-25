package model;

import model.task.Task;
import utils.DukeException;

import java.util.ArrayList;

public class ModelController implements Model {
    private TasksManager tasksManager;
    private MemberManager memberManager;
    /**
     * Handles model changes.
     * */
    public ModelController() {
        //TODO change to loading from storage
        tasksManager = new TasksManager();
        memberManager = new MemberManager();
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