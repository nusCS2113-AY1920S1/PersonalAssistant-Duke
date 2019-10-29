package model;

import utils.DukeException;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ModelController implements Model {
    private TasksManager tasksManager;
    private MemberManager memberManager;
    private Gson gson;
    private Storage storage;

    /**
     * Handles model changes.
     */
    public ModelController() {
        //TODO change to loading from storage
        storage = new Storage();
        tasksManager = new TasksManager(storage.loadTasks());
        memberManager = new MemberManager(storage.loadMembers());
    }

    //@@author JustinChia1997

    /**
     * Loads the data from storage into memory
     */
    @Override
    public void load() {
        //TODO
    }

    /**
     * Saves the data into a persistent json object
     */
    @Override
    public void save() {
        storage.saveTasks(tasksManager.getTaskList());
        storage.saveMembers(memberManager.getMemberList());
    }

    //=================Task interfaces=============================================
    @Override
    public ArrayList<Task> getTaskList() {
        return tasksManager.getTaskList();
    }

    @Override
    public TasksManager getTasksManager() {
        return tasksManager;
    }

    @Override
    public Task addTask(String name) throws DukeException {
        Task newTask = tasksManager.addTask(name);
        storage.saveTasks(tasksManager.getTaskList());
        storage.loadTasks();
        return newTask;
    }

    @Override
    public Task deleteTask(String name) throws DukeException {
        Task toDelete = tasksManager.getTaskByName(name);
        ArrayList<String> memberList = toDelete.getMemberList();
        tasksManager.deleteTask(toDelete);
        return toDelete;
    }

    @Override
    public boolean hasTask(String name) throws DukeException {
        return tasksManager.hasTask(name);
    }

    @Override
    public ArrayList<Member> getMemberList() {
        return memberManager.getMemberList();
    }

    @Override
    public MemberManager getMemberManager() {
        return memberManager;
    }

    @Override
    public void addMember(String name) throws DukeException {
        memberManager.addMember(name);
        storage.saveMembers(memberManager.getMemberList());
    }

    @Override
    public Member deleteMember(String name) throws DukeException {
        Member toDelete = memberManager.getMemberByName(name);
        memberManager.deleteMember(toDelete);
        return toDelete;
    }

    @Override
    public boolean hasMember(String name) throws DukeException {
        return memberManager.hasMember(name);
    }

    @Override
    public void link(int taskIndex, String memberName){
        tasksManager.getTaskById(taskIndex).addMember(memberName);
        //TODO consider uuid for linking
        memberManager.getMemberByName(memberName).addTask(tasksManager.getTaskById(taskIndex).getName());
    }

    @Override
    public void unlink(int taskIndex, String memberName){

    }

}