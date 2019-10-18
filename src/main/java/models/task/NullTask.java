package models.task;

import models.member.ListOfMembersAssignedToTask;
import models.member.Member;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class NullTask implements ITask {
    @Override
    public String getTaskName() {
        return null;
    }

    @Override
    public int getTaskPriority() {
        return 0;
    }

    @Override
    public String getDetails() {
        return null;
    }

    @Override
    public Date getDueDate() {
        return null;
    }

    @Override
    public TaskState getTaskState() {
        return null;
    }

    @Override
    public int getTaskCredit() {
        return 0;
    }

    @Override
    public ListOfMembersAssignedToTask getAssignedMembers() {
        return null;
    }

    @Override
    public HashSet<Integer> getAssignedIndexes() {
        return null;
    }

    @Override
    public void assignMember(Member member) {
        /**
         * Empty method
         */
    }

    @Override
    public void removeMember(Member memberToRemove) {
        /**
         * Empty method
         */
    }

    @Override
    public ArrayList<String> getTaskRequirements() {
        return null;
    }

    @Override
    public int getNumOfTaskRequirements() {
        return 0;
    }

    @Override
    public void setTaskName(String newTaskName) {
        /**
         * Empty method
         */
    }

    @Override
    public void setTaskPriority(int newTaskPriority) {
        /**
         * Empty method
         */
    }

    @Override
    public void setDueDate(String newDueDateString) {
        /**
         * Empty method
         */
    }

    @Override
    public void setTaskCredit(int newTaskCredit) {
        /**
         * Empty method
         */
    }

    @Override
    public void setTaskState(String newTaskStateString) {
        /**
         * Empty method
         */
    }

    @Override
    public void removeTaskRequirement(int indexOfTaskRequirement) {
        /**
         * Empty method
         */
    }

    @Override
    public void addTaskRequirement(String newTaskRequirement) {
        /**
         * Empty method
         */
    }
}
