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

    @Override
    public void load() {
        //TODO
    }

    @Override
    public void save() {
        //TODO
    }

    @Override
    public ArrayList<Task> getTaskList() {
        return tasksManager.getTaskList();
    }

    @Override
    public void addTask(String name) throws DukeException {

    }

    @Override
    public Task deleteTask(int index) throws DukeException {
        Task toDelete = tasksManager.getTaskIndex(index);
        tasksManager.deleteTask(toDelete);
    }

    @Override
    public ArrayList<Member> getMemberList() {
        return null;
    }

    @Override
    public void addMember(String name) throws DukeException {

    }

    @Override
    public Task deleteMember(String name) throws DukeException {
        return null;
    }

    @Override
    public void link(int[] tasksIndexes, String[] memberNames) {

    }

    @Override
    public void unlink(int[] tasksIndexes, String[] memberNames) {

    }
}