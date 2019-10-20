package models.data;

import models.member.Member;
import models.member.MemberList;
import models.task.Task;
import models.task.TaskList;

import java.util.ArrayList;
import java.util.HashMap;

public class Project implements IProject {
    private String description;
    private MemberList memberList;
    private TaskList taskList;
    private HashMap<Task, ArrayList<Member>> task_Members; //task_membersAssigned
    private HashMap<Member, ArrayList<Task>> member_Tasks; //member_individualTaskList

    /**
     * Class representing a task in a project.
     * @param description The description of the project.
     */
    public Project(String description) {
        this.description = description;
        this.memberList = new MemberList();
        this.taskList = new TaskList();
        this.task_Members = new HashMap<Task, ArrayList<Member>>();
        this.member_Tasks = new HashMap<Member, ArrayList<Task>>();
        System.out.println(this.task_Members);
        System.out.println(this.member_Tasks);
        System.out.println(this);
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public MemberList getMembers() {
        return this.memberList;
    }

    @Override
    public TaskList getTasks() {
        return this.taskList;
    }

    @Override
    public int getNumOfMembers() {
        return this.memberList.getNumOfMembers();
    }

    @Override
    public int getNumOfTasks() {
        return this.taskList.getTaskList().size();
    }

    @Override
    public void addMember(Member newMember) {
        this.memberList.addMember(newMember);
        this.member_Tasks.put(newMember, new ArrayList<>());
    }

    @Override
    public void editMember(int memberIndexNumber, String updatedMemberDetails) {
        this.memberList.editMember(memberIndexNumber, updatedMemberDetails);
    }

    @Override
    public void removeMember(Member memberToBeRemoved) {
        this.memberList.removeMember(memberToBeRemoved);
    }

    @Override
    public void addTask(Task newTask) {
        this.taskList.addTask(newTask);

        this.task_Members.put(newTask, new ArrayList<>());
    }

    @Override
    public void removeTask(int taskIndexNumber) {
        this.taskList.removeTask(taskIndexNumber);
    }

    @Override
    public boolean memberIndexExists(int indexNumber) {
        return (indexNumber > 0 && indexNumber <= this.getNumOfMembers());
    }

    @Override
    public Task getTask(int taskIndex) {
        return this.taskList.getTask(taskIndex);
    }

    @Override
    public void editTask(String updatedTaskDetails) {
        this.taskList.editTask(updatedTaskDetails);
    }

    @Override
    public void editTaskRequirements(int taskIndexNumber, String[] updatedTaskRequirements, boolean haveRemove) {
        this.taskList.editTaskRequirements(taskIndexNumber, updatedTaskRequirements, haveRemove);
    }

    @Override
    public ArrayList<String> getAssignedTaskList() {
        ArrayList<String> assignedTaskListString = new ArrayList<>();
        for (HashMap.Entry<Task, ArrayList<Member>> task: task_Members.entrySet()) {
            assignedTaskListString.add(task.getKey().getTaskName() + " is assigned to: ");
            for (Member member: task.getValue()) {
                assignedTaskListString.add(member.getName());
            }
        }
        return assignedTaskListString;
    }

    /**
     * This method assigns a task to a member by adding the task to a member's individual
     * task list - tasksAssignedToMembers.
     * @param task the task which you wish to assign to the member.
     * @param member the member you wish to assign the task to.
     */
    @Override
    public void addTaskToMemberTaskList(Task task, Member member) {
        if (task_Members.containsKey(task)) {
            task_Members.get(task).add(member);
        } else {
            ArrayList<Member> memberList = new ArrayList<>();
            memberList.add(member);
            task_Members.put(task,memberList);
        }
        assignMemberToTask(member, task);
    }

    /**
     * Adds an assignment by establishing a link between a task and a member.
     * This method adds a member to the list of members working on a particular task.
     * @param member the member which you wish to assign to a task.
     * @param task the particular task of interest.
     */
    @Override
    public void assignMemberToTask(Member member, Task task) {
        if (member_Tasks.containsKey(member)) {
            member_Tasks.get(member).add(task);
        } else {
            ArrayList<Task> taskList = new ArrayList<>();
            taskList.add(task);
            member_Tasks.put(member, taskList);
        }
    }

    /**
     * Removes a task from a member's individual task list.
     * @param task to be removed from an individual's task list.
     */
    public void removeTaskFromMembers(Task task) {
        ArrayList<Member> listOfMember = task_Members.get(task);
        for (Member member: listOfMember) {
            member_Tasks.get(member).remove(task);
        }
        task_Members.remove(task);
    }

    /**
     * This method is used when a member is removed.
     * This removes the member from the memberToTasks and taskToMembers.
     * @param member the member to be remove from the HashMap
     */
    public void removeMemberToTasks(Member member) {
        ArrayList<Task> listOfTask = member_Tasks.get(member);
        for (Task task: listOfTask) {
            task_Members.get(task).remove(member);
        }
        member_Tasks.remove(member);
    }

    /**
     * Removes the assignment
     * @param member
     * @param task
     */
    public void unassignMemberFromTask(Member member, Task task) {
        task_Members.get(task).remove(member);
        member_Tasks.get(member).remove(task);
    }

    public boolean containsAssignment(Task task, Member member) {
        System.out.println("testing");
        if (member_Tasks.containsKey(member)) {
            return member_Tasks.get(member).contains(task);
        } else {
            return false;
        }
    }
}
