package model;

import model.task.Task;

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
    public void addTask(Task task) {
        tasksManager.addTask(task);
    }

    @Override
    public ArrayList<Task> getTaskList() { return tasksManager.getTasks(); }
}
