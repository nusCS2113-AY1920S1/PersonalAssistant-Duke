package models.data;

import java.util.HashMap;
import models.member.Member;
import models.member.MemberList;
import models.task.Task;
import models.task.TaskList;

import java.util.ArrayList;

public class NullProject implements IProject {
    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public MemberList getMembers() {
        return null;
    }

    @Override
    public TaskList getTasks() {
        return null;
    }

    @Override
    public int getNumOfMembers() {
        return 0;
    }

    @Override
    public int getNumOfTasks() {
        return 0;
    }

    @Override
    public void addMember(Member newMember) {
        /*
        Empty method
         */
    }

    @Override
    public void editMember(int memberIndexNumber, String updatedMemberDetails) {
        /*
        Empty method
         */
    }

    @Override
    public void removeMember(Member toBeRemoved) {
        /*
        Empty method
         */
    }

    @Override
    public void addTask(Task newTask) {
        /*
        Empty method
         */
    }

    @Override
    public void removeTask(int taskIndexNumber) {
        /*
        Empty method
         */
    }

    @Override
    public boolean memberIndexExists(int indexNumber) {
        return false;
    }

    @Override
    public Task getTask(int taskIndex) {
        return null;
    }

    @Override
    public void editTask(int taskIndexNumber, String updatedTaskDetails) {
        /*
        Empty method
         */
    }

    @Override
    public void editTaskRequirements(int taskIndexNumber, String[] updatedTaskRequirements, boolean haveRemove) {
        /*
        Empty method
         */
    }

    @Override
    public ArrayList<String> getAssignedTaskList() {
        return null;
    }

    @Override
    public ArrayList<String> getCredits() {
        return null;
    }

    @Override
    public void createAssignment(Task task, Member member) {
        /*
        Empty method
         */
    }

    @Override
    public void removeAssignment(Member member, Task task) {
        /*
        Empty method
         */
    }

    @Override
    public boolean containsAssignment(Task task, Member member) {
        return false;
    }

    @Override
    public HashMap<Member, ArrayList<Task>> getMembersIndividualTaskList() {
        return null;
    }

    @Override
    public HashMap<Task, ArrayList<Member>> getTasksAndAssignedMembers() {
        return null;
    }

}
