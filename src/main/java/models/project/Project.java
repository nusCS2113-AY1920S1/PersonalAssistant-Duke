package models.project;

import models.member.IMember;
import models.member.Member;
import models.member.MemberList;
import models.reminder.Reminder;
import models.reminder.ReminderList;
import models.task.Task;
import models.task.TaskList;

import java.util.ArrayList;
import java.util.HashMap;

public class Project implements IProject {
    private String name;
    private MemberList memberList;
    private TaskList taskList;
    private ReminderList reminderList;
    private HashMap<Task, ArrayList<Member>> taskAndListOfMembersAssigned; //task_membersAssigned
    private HashMap<Member, ArrayList<Task>> memberAndIndividualListOfTasks; //member_individualTaskList

    /**
     * Class representing a task in a project.
     * @param name The description of the project.
     */
    public Project(String name) {
        this.name = name;
        this.memberList = new MemberList();
        this.taskList = new TaskList();
        this.reminderList = new ReminderList();
        this.taskAndListOfMembersAssigned = new HashMap<>();
        this.memberAndIndividualListOfTasks = new HashMap<>();
    }

    @Override
    public String getName() {
        return this.name;
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
        for (Task task : this.taskAndListOfMembersAssigned.keySet()) {
            this.taskAndListOfMembersAssigned.get(task).remove(memberToBeRemoved);
        }
        this.memberAndIndividualListOfTasks.remove(memberToBeRemoved);
        this.memberList.removeMember(memberToBeRemoved);
    }

    @Override
    public void addTask(Task newTask) {
        this.taskList.addTask(newTask);
        this.taskAndListOfMembersAssigned.put(newTask, new ArrayList<>());
    }

    @Override
    public void removeTask(int taskIndexNumber) {
        Task taskToRemove = this.getTask(taskIndexNumber);
        for (Member member : this.memberAndIndividualListOfTasks.keySet()) {
            this.memberAndIndividualListOfTasks.get(member).remove(taskToRemove);
        }
        this.taskAndListOfMembersAssigned.remove(taskToRemove);
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
    public void editTaskRequirements(int taskIndexNumber, String updatedTaskRequirements) {
        this.taskList.editTaskRequirements(taskIndexNumber, updatedTaskRequirements);
    }

    /**
     * Returns the member names with the credits of their assigned tasks.
     * @return The member names with the credits of their assigned tasks.
     */
    @Override
    public ArrayList<String> getCredits() {
        ArrayList<String> allMemberCredits = new ArrayList<>();
        ArrayList<Member> allMembers = this.getMembers().getMemberList();
        HashMap<Member, ArrayList<Task>> assignedMembers = this.getMembersIndividualTaskList();
        int count = 1;
        for (Member member : allMembers) {
            int credits = 0;
            for (Task assignedTask : assignedMembers.get(member)) {
                credits += assignedTask.getTaskCredit();
            }
            allMemberCredits.add(count + ". " + member.getName() + " | Credits: " + credits);
            count++;
        }
        return allMemberCredits;
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

    public void addReminderToList(Reminder reminder) {
        this.reminderList.addReminderList(reminder);
    }

    public String getTaskIndexName(Integer index) {
        return getTask(index).getTaskName();
    }

    public boolean MemberExists(IMember newMember) {
        return this.memberList.contains(newMember);
    }

    public int getIndexOfMember(IMember newMember) {
        return this.memberList.getIndexOfMember(newMember);
    }
}
