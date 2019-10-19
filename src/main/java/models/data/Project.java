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
    private HashMap<Task, ArrayList<Member>> tasksAssignedToMembers; //member_individualTaskList
    private HashMap<Member, ArrayList<Task>> membersAssignedToTask; //task_membersAssigned

    /**
     * Class representing a task in a project.
     * @param description The description of the project.
     */
    public Project(String description) {
        this.description = description;
        this.memberList = new MemberList();
        this.taskList = new TaskList();
        this.tasksAssignedToMembers = new HashMap<>();
        this.membersAssignedToTask = new HashMap<>();
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
        for (HashMap.Entry<Task, ArrayList<Member>> task: tasksAssignedToMembers.entrySet()) {
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
        if (tasksAssignedToMembers.containsKey(task)) {
            tasksAssignedToMembers.get(task).add(member);
        } else {
            ArrayList<Member> memberList = new ArrayList<>();
            memberList.add(member);
            tasksAssignedToMembers.put(task,memberList);
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
        if (membersAssignedToTask.containsKey(member)) {
            membersAssignedToTask.get(member).add(task);
        } else {
            ArrayList<Task> taskList = new ArrayList<>();
            taskList.add(task);
            membersAssignedToTask.put(member, taskList);
        }
    }

    /**
     * Removes a task from a member's individual task list.
     * @param task to be removed from an individual's task list.
     */
    public void removeTaskFromMembers(Task task) {
        ArrayList<Member> listOfMember = tasksAssignedToMembers.get(task);
        for (Member member: listOfMember) {
            membersAssignedToTask.get(member).remove(task);
        }
        tasksAssignedToMembers.remove(task);
    }

    /**
     * This method is used when a member is removed.
     * This removes the member from the memberToTasks and taskToMembers.
     * @param member the member to be remove from the HashMap
     */
    public void removeMemberToTasks(Member member) {
        ArrayList<Task> listOfTask = membersAssignedToTask.get(member);
        for (Task task: listOfTask) {
            tasksAssignedToMembers.get(task).remove(member);
        }
        membersAssignedToTask.remove(member);
    }

    /**
     * Removes the assignment
     * @param member
     * @param task
     */
    public void unassignMemberFromTask(Member member, Task task) {
        tasksAssignedToMembers.get(member).remove(task);
        membersAssignedToTask.get(task).remove(member);
    }
}
