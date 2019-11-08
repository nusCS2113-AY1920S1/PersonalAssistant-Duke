package models.task;

import java.util.ArrayList;
import java.util.Date;

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
    public void setDueDate(Date newDueDate) {
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

    @Override
    public String getDetailsForAssignmentTable() {
        return null;
    }
}
