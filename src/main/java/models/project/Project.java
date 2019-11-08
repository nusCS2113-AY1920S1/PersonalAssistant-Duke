package models.project;

import models.member.IMember;
import models.member.Member;
import models.member.MemberList;
import models.member.NullMember;
import models.reminder.Reminder;
import models.reminder.ReminderList;
import models.task.ITask;
import models.task.NullTask;
import models.task.Task;
import models.task.TaskList;
import models.task.TaskState;

import java.util.ArrayList;
import java.util.HashMap;

public class Project implements IProject {
    private String name;
    private MemberList memberList;
    private TaskList taskList;
    private ReminderList reminderList;
    private HashMap<String, ArrayList<String>> taskAndListOfMembersAssigned; //taskID_listOfMemberIDs
    private HashMap<String, ArrayList<String>> memberAndIndividualListOfTasks; //memberID_listOfTaskIDs

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

    //@@author iamabhishek98
    @Override
    public void addMember(Member newMember) {
        this.memberList.addMember(newMember);
        this.memberAndIndividualListOfTasks.put(newMember.getMemberID(), new ArrayList<>());
    }

    //@@author iamabhishek98
    @Override
    public String editMember(int memberIndexNumber, String updatedMemberDetails) {
        return this.memberList.editMember(memberIndexNumber, updatedMemberDetails);
    }

    //@@author iamabhishek98
    @Override
    public void removeMember(Member memberToBeRemoved) {
        for (String taskID: this.taskAndListOfMembersAssigned.keySet()) {
            this.taskAndListOfMembersAssigned.get(taskID).remove(memberToBeRemoved.getMemberID());
        }
        this.memberAndIndividualListOfTasks.remove(memberToBeRemoved);
        this.memberList.removeMember(memberToBeRemoved);
    }

    @Override
    public void addTask(Task newTask) {
        this.taskList.addTask(newTask);
        this.taskAndListOfMembersAssigned.put(newTask.getTaskID(), new ArrayList<>());
    }

    //@@author iamabhishek98
    @Override
    public void removeTask(int taskIndexNumber) {
        Task taskToRemove = this.getTask(taskIndexNumber);
        for (String memberID : this.memberAndIndividualListOfTasks.keySet()) {
            this.memberAndIndividualListOfTasks.get(memberID).remove(taskToRemove.getTaskID());
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

    //@@author iamabhishek98
    @Override
    public String[] editTask(int taskIndexNumber, String updatedTaskDetails) {
        return this.taskList.editTask(taskIndexNumber, updatedTaskDetails);
    }

    @Override
    public String[] editTaskRequirements(int taskIndexNumber, String updatedTaskRequirements) {
        return this.taskList.editTaskRequirements(taskIndexNumber, updatedTaskRequirements);
    }

    //@@author iamabhishek98
    /**
     * Returns the member names with the credits of their assigned tasks.
     * @return The member names with the credits of their assigned tasks.
     */
    @Override
    public ArrayList<String> getCredits() {
        /*
        *
        *       THIS METHOD IS TO BE REFACTORED
        *
         */
        ArrayList<String> allMemberCredits = new ArrayList<>();
        ArrayList<Member> allMembers = this.getMembers().getMemberList();
        HashMap<String, ArrayList<String>> memberIDAndTaskIDs = this.getMembersIndividualTaskList(); //memberID_taskIDs
        int count = 1;
        for (Member member : allMembers) {
            int totalCredits = 0;
            int doneCredits = 0;
            for (String taskID : memberIDAndTaskIDs.get(member.getMemberID())) {
                // credits are split equally between members
                ITask assignedTask = getTaskFromID(taskID);
                int taskCredit = (assignedTask.getTaskCredit()) / (memberIDAndTaskIDs.size());
                totalCredits += taskCredit;
                // members only get credits if the task is "DONE"
                if (assignedTask.getTaskState() == TaskState.DONE) {
                    doneCredits += taskCredit;
                }
            }
            int scale = 20;
            int percentDone = (int) ((doneCredits / (float) totalCredits) * scale);
            String progress = "";
            for (int i = 0; i < percentDone; i++) {
                progress += "#";
            }
            for (int i = percentDone; i < scale; i++) {
                progress += ".";
            }
            allMemberCredits.add(count + ". " + member.getName() + ": " + doneCredits + " credits");
            allMemberCredits.add("   Progress: " + progress + " (" + percentDone * (100 / scale) + "%)");
            count++;
        }
        return allMemberCredits;
    }

    //@@author sinteary
    /**
     * This method assigns a task to a member by adding the task to a member's individual
     * task list - tasksAssignedToMembers.
     * @param task the task which you wish to assign to the member.
     * @param member the member you wish to assign the task to.
     */
    @Override
    public void createAssignment(Task task, Member member) {
        taskAndListOfMembersAssigned.get(task.getTaskID()).add(member.getMemberID());
        memberAndIndividualListOfTasks.get(member.getMemberID()).add(task.getTaskID());
    }

    /**
     * Removes the assignment between member and task.
     * @param member the member to unassign the task from.
     * @param task the task to be unassigned.
     */
    @Override
    public void removeAssignment(Member member, Task task) {
        taskAndListOfMembersAssigned.get(task.getTaskID()).remove(member.getMemberID());
        memberAndIndividualListOfTasks.get(member.getMemberID()).remove(task.getTaskID());
    }

    /**
     * Checks if assignment exists between a member and task.
     * @param task The task in question.
     * @param member The member in question.
     * @return true task has already been assigned to a member.
     */
    @Override
    public boolean containsAssignment(Task task, Member member) {
        return memberAndIndividualListOfTasks.get(member.getMemberID()).contains(task.getTaskID())
            && taskAndListOfMembersAssigned.get(task.getTaskID()).contains(member.getMemberID());
    }

    /**
     * Returns a hashmap with information about each member's task assignment.
     * @return hashmap with member as key and accompanying task list.
     */
    @Override
    public HashMap<String, ArrayList<String>> getMembersIndividualTaskList() {
        return this.memberAndIndividualListOfTasks;
    }

    /**
     * Returns a hashmap with information about each task's assignment to members.
     * @return hashmap with task as key and accompanying list of assigned members.
     */
    @Override
    public HashMap<String, ArrayList<String>> getTasksAndAssignedMembers() {
        return this.taskAndListOfMembersAssigned;
    }

    public IMember getMemberFromID(String memberID) {
        for (Member member : this.memberList.getMemberList()) {
            if (memberID.equals(member.getMemberID())) {
                return member;
            }
        }
        return new NullMember("Unable to find this member.");
    }

    public ITask getTaskFromID(String taskID) {
        for (Task task : this.taskList.getTaskList()) {
            if (taskID.equals(task.getTaskID())) {
                return task;
            }
        }
        return new NullTask();
    }
    //@@author

    @Override
    public void addReminderToList(Reminder reminder) {
        this.reminderList.addReminderList(reminder);
    }

    @Override
    public ArrayList<Reminder> getReminderList() {
        return reminderList.getReminderList();
    }

    public String getTaskIndexName(Integer index) {
        return getTask(index).getTaskName();
    }

    public boolean memberExists(IMember newMember) {
        return this.memberList.contains(newMember);
    }

    public Member getMember(int indexNumber) {
        return (Member) this.memberList.getMember(indexNumber);
    }

    public boolean taskExists(ITask task) {
        return this.taskList.contains((Task) task);
    }

    /**
     * Set the status of the Reminder.
     * @param isDone the status of the reminder.
     * @param index the 1 based index of the reminder.
     */
    @Override
    public void markReminder(Boolean isDone, int index) {
        reminderList.getReminderList().get(index - 1).setIsDone(isDone);
    }

    /**
     * Get a reminder from the reminderList.
     * @param index the index of the Reminder.
     * @return Reminder object.
     */
    @Override
    public Reminder getReminder(int index) {
        return reminderList.getReminderList().get(index - 1);
    }

    public void removeReminder(int index) {
        reminderList.getReminderList().remove(index - 1);
    }

    public int getReminderListSize() {
        return reminderList.getReminderList().size();
    }

}
