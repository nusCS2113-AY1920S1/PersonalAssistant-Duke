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
     * */
    public ModelController() {
        //TODO change to loading from storage
        storage = new Storage();
        tasksManager = new TasksManager(storage.loadTasks());
        memberManager = new MemberManager(storage.loadMembers());
//        GsonBuilder builder = new GsonBuilder();
//        builder.setPrettyPrinting();
//        gson = builder.create();
    }

    //@@author JustinChia1997
    /**
     * Loads the data from storage into memory
     * */
    @Override
    public void load() {
        //TODO
    }

    /**
     * Saves the data into a persistent json object
     * */
    @Override
    public void save() {
        storage.saveTasks(tasksManager.getTaskList());
        storage.loadTasks();    }

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
//        String json = gson.toJson(tasksManager.getTaskList());
//        System.out.println("CHECK FOR THE STUFF");
//        System.out.println(json);
    }

    @Override
    public Task deleteTask(String name) throws DukeException {
        Task toDelete = tasksManager.getTaskByName(name);
        ArrayList<Member> memberList = toDelete.getMemberList();
        for (int i = 0; i < memberList.size(); i++) {
            unlink(toDelete, memberList.get(i));
        }
        tasksManager.deleteTask(toDelete);
        return toDelete;
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
        ArrayList<Task> taskArrayList = toDelete.getTaskList();
        for (int i = 0; i < taskArrayList.size(); i++) {
            unlink(taskArrayList.get(i), toDelete);
        }
        memberManager.deleteMember(toDelete);
        return toDelete;
    }

    @Override
    public void link(int tasksIndexes, String memberNames) {
        Task task = tasksManager.getTaskById(tasksIndexes);
        Member member = memberManager.getMemberByName(memberNames);
        task.addMember(member);
        member.addTask(task);
    }

    @Override
    public void unlink(int tasksIndexes, String memberNames) {
        Task task = tasksManager.getTaskById(tasksIndexes);
        Member member = memberManager.getMemberByName(memberNames);
        unlink(task, member);
    }

    private void unlink(Task task, Member member) {
        task.deleteMember(member);
        member.deleteTask(task);
    }
}