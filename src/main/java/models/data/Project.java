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
        return (indexNumber > 0 && indexNumber <= getNumOfMembers());
    }

    @Override
    public Task getTask(int taskIndex) {
        return this.taskList.getTask(taskIndex);
    }

    @Override
    public void editTask(int taskIndexNumber, String updatedTaskDetails) {
        this.taskList.editTask(taskIndexNumber, updatedTaskDetails);
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
    @Override
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
    @Override
    public boolean containsAssignment(Task task, Member member) {
        return memberAndIndividualListOfTasks.get(member).contains(task)
            && taskAndListOfMembersAssigned.get(task).contains(member);
    }

    /**
     * Returns a hashmap with information about each member's task assignment.
     * @return hashmap with member as key and accompanying task list.
     */
    @Override
    public HashMap<Member, ArrayList<Task>> getMembersIndividualTaskList() {
        return this.memberAndIndividualListOfTasks;
    }

    /**
     * Returns a hashmap with information about each task's assignment to members.
     * @return hashmap with task as key and accompanying list of assigned members.
     */
    @Override
    public HashMap<Task, ArrayList<Member>> getTasksAndAssignedMembers() {
        return this.taskAndListOfMembersAssigned;
    }

    /**
     * Verifies that a member with a given index number exists.
     * @param index The given member index number.
     * @return true if the project has a member with the given index number.
     */
    public boolean hasMemberIndex(Integer index) {
        return index > 0 && index <= getNumOfMembers();
    }

    /**
     * Verifies that a task with a given index number exists.
     * @param indexNumber The given task index number.
     * @return true if the project has a task with the given index number.
     */
    public boolean hasTaskIndex(Integer indexNumber) {
        return indexNumber > 0 && indexNumber <= getNumOfTasks();
    }


}
