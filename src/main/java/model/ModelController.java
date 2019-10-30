package model;

import com.google.gson.Gson;
import storage.Storage;
import common.DukeException;

import java.util.ArrayList;


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

    public int getTaskListSize() {
        return tasksManager.getTaskListSize();
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
    public boolean hasTask(String name) throws DukeException {
        return tasksManager.hasTask(name);
    }



    //=================Member interfaces=============================================

    @Override
    public ArrayList<Member> getMemberList() {
        return memberManager.getMemberList();
    }

    @Override
    public int getMemberListSize() {
        return memberManager.getMemberListSize();
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
    public boolean hasMember(String name) throws DukeException {
        return memberManager.hasMember(name);
    }


    //============================

    @Override
    public void link(int taskIndex, String memberName) {
        tasksManager.getTaskById(taskIndex).addMember(memberName);
        //TODO consider uuid for linking
        memberManager.getMemberByName(memberName).addTask(tasksManager.getTaskById(taskIndex).getName());
    }

    @Override
    public void unlink(int taskIndex, String memberName) {
        tasksManager.getTaskById(taskIndex).deleteMember(memberName);
        memberManager.getMemberByName(memberName).deleteTask(memberName);
    }

    @Override
    //@@author yuyanglin28
    public boolean deleteMember(String name) {
        tasksManager.deleteMemberInTasks(name);
        Member toDelete = memberManager.getMemberByName(name);
        if (memberManager.deleteMember(toDelete)) {
            storage.saveMembers(memberManager.getMemberList());
            storage.saveTasks(tasksManager.getTaskList());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String deleteTask(int taskIndexInList) throws DukeException {
        Task toDelete = tasksManager.deleteTask(taskIndexInList);
        String toDeleteName = tasksManager.getNameByTask(toDelete);
        memberManager.deleteTaskInMembers(toDeleteName);
        storage.saveMembers(memberManager.getMemberList());
        storage.saveTasks(tasksManager.getTaskList());
        return toDeleteName;
    }

    public String getTasksByKeyword(String keyword) {
        return tasksManager.getTasksByKeyword(keyword);
    }

    public String scheduleTeamAll() {
        return tasksManager.scheduleTeamAll();
    }

    public String scheduleTeamTodo() {
        return tasksManager.scheduleTeamTodo();
    }

    public String scheduleMemberAll(String memberName) {
        ArrayList<String> tasksName = memberManager.getTaskListOfMember(memberName);
        return tasksManager.scheduleAllTasks(tasksName);
    }

    public String scheduleMemberTodo(String memberName) {
        ArrayList<String> tasksName = memberManager.getTaskListOfMember(memberName);
        return tasksManager.scheduleTodoTasks(tasksName);
    }
}