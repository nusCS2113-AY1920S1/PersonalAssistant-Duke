package model;

import com.google.gson.Gson;
import storage.Storage;
import common.DukeException;

import java.util.ArrayList;
import java.util.Date;


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
    public String getTodoTasks() {
        return tasksManager.getTodoTasks();
    }

    @Override
    public String getTasks() {
        return tasksManager.getTasks();
    }

    @Override
    public int getTaskListSize() {
        return tasksManager.getTaskListSize();
    }

    @Override
    public String getTaskNameById(int index) {
        return tasksManager.getNameById(index);
    }

    @Override
    public String getTaskNameByIdOnList(int index) {
        return tasksManager.getTaskNameByIdOnList(index);
    }

    @Override
    public String getTaskIsDoneByIdOnList(int index) {
        return tasksManager.getTaskIsDoneByIdOnList(index);
    }

    @Override
    public String getTaskDescriptionByIdOnList(int index) {
        return tasksManager.getTaskDescriptionByIdOnList(index);
    }

    @Override
    public Date getTaskDateTimeByIdOnList(int index) {
        return tasksManager.getTaskDateTimeByIdOnList(index);
    }

    @Override
    public ArrayList getMemberListOfTaskByIdOnList(int index) {
        return tasksManager.getMemberListOfTaskByIdOnList(index);
    }

    @Override
    public ArrayList getSkillListOfTaskByIdOnList(int index) {
        return tasksManager.getSkillListOfTaskByIdOnList(index);
    }

    @Override
    public Date getTaskReminderByIdOnList(int index) {
        return tasksManager.getTaskReminderByIdOnList(index);
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

    @Override
    public boolean addTaskReqSkill(String taskName, String skillName) {
        return tasksManager.addReqSkill(taskName, skillName);
    }

    //@@author yuyanglin28
    /**
     * This method is to update the description of a task
     * @param index the index of task (begin from 0)
     * @param des new description
     * @return old description
     */
    public String updateTaskDes(int index, String des) {
        String oldDes = tasksManager.getTaskDes(index);
        tasksManager.updateTaskDes(index, des);
        return oldDes;
    }


    public String getTasksByKeyword(String keyword) {
        return tasksManager.getTasksByKeyword(keyword);
    }

    public String tasksAllInorderTime() {
        return tasksManager.tasksAllInorderTime();
    }

    public String tasksTodoInorderTime() {
        return tasksManager.tasksTodoInorderTime();
    }

    public String tasksAllInorderPicNum() {
        return tasksManager.tasksAllInorderPicNum();
    }

    public String tasksTodoInorderPicNum() {
        return tasksManager.tasksTodoInorderPicNum();
    }

    //@@author JasonChanWQ
    /**
     * Checks if task index exists in task list
     * @param taskIndex Index of the task
     * @return false if the task not in list, true if task in list
     */
    public boolean isInTaskList(int taskIndex) {
        if (taskIndex < 1 || taskIndex > tasksManager.getTaskList().size()) {
            return false;
        }
        return true;
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
    public String getMemberNameById(int index) {
        return memberManager.getMemberNameById(index);
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

    @Override
    public boolean addMemberSkill(String memberName, String skillName) {
        return memberManager.addSkill(memberName, skillName);
    }


    //@@author yuyanglin28

    /**
     * This method is to update the biography of a member
     * @param name member name
     * @param bio new bio
     * @return old bio
     */
    @Override
    public String updateMemberBio(String name, String bio) {
        String oldBio = memberManager.getMemberBio(name);
        memberManager.updateMemberBio(name, bio);
        return oldBio;
    }

    //@@author yuyanglin28

    /**
     * This method is to update the email of a member
     * @param name member name
     * @param email new email
     * @return old email
     * @throws DukeException throw exception when the email format is not correct
     */
    @Override
    public String updateMemberEmail(String name, String email) throws DukeException {
        String oldEmail = memberManager.getMemberEmail(name);
        memberManager.updateMemberEmail(name, email);
        return oldEmail;
    }

    //@@author yuyanglin28

    /**
     * This method is to update the phone of a member
     * @param name member name
     * @param phone new phone
     * @return old phone
     */
    @Override
    public String updateMemberPhone(String name, String phone) {
        String oldPhone = memberManager.getMemberPhone(name);
        memberManager.updateMemberPhone(name, phone);
        return oldPhone;
    }

    //@@author JasonChanWQ
    /**
     * get the corresponding member index by the name
     * @param name input name
     * @return memberIndex
     */
    public int getMemberIdByName(String name) {
        int memberIndex = 0;
        for (int i = 0; i < memberManager.getMemberList().size(); i++) {
            if (name.equals(memberManager.getMemberNameById(i))) {
                memberIndex = i;
                break;
            }
        }
        return memberIndex;
    }

    //@@author JasonChanWQ
    /**
     * get member biography by name
     * @param name input name
     * @return memberBio
     */
    public String getMemberBioByName(String name) {
        String memberBio = "";
        for (int i = 0; i < memberManager.getMemberList().size(); i++) {
            if (name.equals(memberManager.getMemberNameById(i))) {
                memberBio = memberManager.getMemberBio(name);
                break;
            }
        }
        return memberBio;
    }

    //@@author JasonChanWQ
    /**
     * get member email by name
     * @param name input name
     * @return memberEmail
     */
    public String getMemberEmailByName(String name) {
        String memberEmail = "";
        for (int i = 0; i < memberManager.getMemberList().size(); i++) {
            if (name.equals(memberManager.getMemberNameById(i))) {
                memberEmail = memberManager.getMemberEmail(name);
                break;
            }
        }
        return memberEmail;
    }

    //@@author JasonChanWQ
    /**
     * get member phone by name
     * @param name input name
     * @return memberPhone
     */
    public String getMemberPhoneByName(String name) {
        String memberPhone = "";
        for (int i = 0; i < memberManager.getMemberList().size(); i++) {
            if (name.equals(memberManager.getMemberNameById(i))) {
                memberPhone = memberManager.getMemberPhone(name);
                break;
            }
        }
        return memberPhone;
    }

    @Override
    public ArrayList<String> getTaskListOfMemberByName(String name) {
        return memberManager.getTaskListOfMember(name);
    }

    @Override
    public ArrayList<String> getSkillListOfMemberByName(String name) {
        return memberManager.getSkillListOfMember(name);
    }


    //============================ Task and member interfaces =================================

    //@@author JustinChia1997
    @Override
    public void link(int taskIndex, String memberName) {
        tasksManager.getTaskById(taskIndex).addMember(memberName);
        //TODO consider uuid for linking
        memberManager.getMemberByName(memberName).addTask(tasksManager.getTaskById(taskIndex).getName());
    }

    @Override
    public void unlink(int taskIndex, String memberName) throws DukeException {
        Task task = tasksManager.getTaskById(taskIndex);
        if (!task.hasMember(memberName)) {
            throw new DukeException("Warning: no link between " + task.getName() + " and " + memberName);
        }
        task.deleteMember(memberName);
        memberManager.getMemberByName(memberName).deleteTask(task.getName());
    }

    //@@author yuyanglin28
    /**
     * This method is to delete a member in member list, and also corresponding member in task list
     * @param memberName to be deleted member name
     */
    @Override
    public void deleteMember(String memberName) {
        tasksManager.deleteMemberInTasks(memberName);
        Member toDelete = memberManager.getMemberByName(memberName);
        memberManager.deleteMember(toDelete);
    }


    //@@author yuyanglin28

    /**
     * This method is to delete a task in task list and also corresponding task in member list
     * @param index index of to be deleted task
     * @return to be deleted task's name
     */
    @Override
    public String deleteTask(int index) {
        String name = tasksManager.getNameById(index);
        memberManager.deleteTaskInMembers(name);
        Task toDelete = tasksManager.getTaskByName(name);
        tasksManager.deleteTask(toDelete);
        return name;
    }

    //@@author yuyanglin28

    /**
     * This method is to schedule all assigned tasks for a member
     * @param memberName member name
     * @return sorted tasks list
     */
    @Override
    public String scheduleMemberAll(String memberName) {
        ArrayList<String> tasksName = memberManager.getTaskListOfMember(memberName);
        return tasksManager.tasksAllInorderTime(tasksName);
    }

    //@@author yuyanglin28

    /**
     * This method is to schedule todo assigned tasks for a member
     * @param memberName member name
     * @return sorted todo tasks list
     */
    @Override
    public String scheduleMemberTodo(String memberName) {
        ArrayList<String> tasksName = memberManager.getTaskListOfMember(memberName);
        return tasksManager.tasksTodoInorderTime(tasksName);
    }

    //@@author yuyanglin28

    /**
     * This method is to check deadline crash of every member,
     * two tasks has time crash if their deadlines are at the same day.
     * This method will get the task list of a member and pass to tasks manager to check the crash,
     * do it for every member.
     * @return string of the time crash, if no time crash, return an empty string
     */
    @Override
    public String check() {
        String result = "";
        for (int i = 0; i < memberManager.getMemberListSize(); i++) {
            String memberName = memberManager.getMemberNameById(i);
            ArrayList<String> tasksName = memberManager.getTaskListOfMember(i);
            String subResult = tasksManager.check(tasksName);
            if (!subResult.equals("")) {
                result += "\n" + memberName + ": " + tasksManager.check(tasksName);
            }
        }
        return result;
    }

    //@@author yuyanglin28

    /**
     * This method is to get the member list in order of progress.
     * This method will first get the progress of every member through task manager and store in an array list,
     * then pass the array list to member manager to sorted the progress.
     *
     * @return sorted member list, progress from high to low
     */
    @Override
    public String membersInorderProgress() {
        ArrayList<Double> progress = new ArrayList<>();
        for (int i = 0; i < memberManager.getMemberListSize(); i++) {
            ArrayList<String> tasksName = memberManager.getTaskListOfMember(i);
            progress.add(tasksManager.getProgress(tasksName));
        }
        return memberManager.membersInorderProgress(progress);
    }


    //@@author yuyanglin28

    /**
     * This method is to get the member list in order of number of todo tasks.
     * This method will first get the todo task num of every member through task manager and store in an array list,
     * then pass the array list to member manager to sorted the todo tasks num.
     *
     * @return sorted member list, num of todo tasks from small to big
     */
    @Override
    public String membersInorderTodoNum() {
        ArrayList<Integer> todoNum = new ArrayList<>();
        for (int i = 0; i < memberManager.getMemberListSize(); i++) {
            ArrayList<String> tasksName = memberManager.getTaskListOfMember(i);
            todoNum.add(tasksManager.getTodoTasks(tasksName));
        }
        return memberManager.membersInorderTodoNum(todoNum);
    }
}