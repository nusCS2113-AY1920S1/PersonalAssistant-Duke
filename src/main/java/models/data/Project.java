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
    private HashMap<Task, ArrayList<Member>> taskAndListOfMembersAssigned; //task_membersAssigned
    private HashMap<Member, ArrayList<Task>> memberAndIndividualListOfTasks; //member_individualTaskList

    /**
     * Class representing a task in a project.
     * @param description The description of the project.
     */
    public Project(String description) {
        this.description = description;
        this.memberList = new MemberList();
        this.taskList = new TaskList();
        this.taskAndListOfMembersAssigned = new HashMap<Task, ArrayList<Member>>();
        this.memberAndIndividualListOfTasks = new HashMap<Member, ArrayList<Task>>();
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
        this.memberAndIndividualListOfTasks.put(newMember, new ArrayList<>());
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

        this.taskAndListOfMembersAssigned.put(newTask, new ArrayList<>());
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
        for (HashMap.Entry<Task, ArrayList<Member>> task: taskAndListOfMembersAssigned.entrySet()) {
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
    public void createAssignment(Task task, Member member) {
        taskAndListOfMembersAssigned.get(task).add(member);
        memberAndIndividualListOfTasks.get(member).add(task);
    }

    /**
     * Removes the assignment between member and task.
     * @param member the member to unassign the task from.
     * @param task the task to be unassigned.
     */
    public void removeAssignment(Member member, Task task) {
        taskAndListOfMembersAssigned.get(task).remove(member);
        memberAndIndividualListOfTasks.get(member).remove(task);
    }

    /**
     * Checks if assignment exists between a member and task.
     * @param task The task in question.
     * @param member The member in question.
     * @return true task has already been assigned to a member.
     */
    public boolean containsAssignment(Task task, Member member) {
        return memberAndIndividualListOfTasks.get(member).contains(task);
    }
}
