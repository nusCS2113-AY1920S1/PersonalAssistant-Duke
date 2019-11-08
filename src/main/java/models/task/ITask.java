package models.task;

import java.util.ArrayList;
import java.util.Date;

public interface ITask {
    String getTaskName();

    int getTaskPriority();

    String getDetails();

    Date getDueDate();

    TaskState getTaskState();

    int getTaskCredit();

    ArrayList<String> getTaskRequirements();

    int getNumOfTaskRequirements();

    void setTaskName(String newTaskName);

    void setTaskPriority(int newTaskPriority);

    void setDueDate(Date newDueDate);

    void setTaskCredit(int newTaskCredit);

    void setTaskState(String newTaskStateString);

    void removeTaskRequirement(int indexOfTaskRequirement);

    void addTaskRequirement(String newTaskRequirement);

    String getDetailsForAssignmentTable();
}
