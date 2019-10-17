package models.task;

import models.member.ListOfMembersAssignedToTask;
import models.member.Member;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

interface ITask {
    String getTaskName();

    int getTaskPriority();

    String getDetails();

    Date getDueDate();

    TaskState getTaskState();

    int getTaskCredit();

    ListOfMembersAssignedToTask getAssignedMembers();

    HashSet<Integer> getAssignedIndexes();

    void assignMember(Member member);

    void removeMember(Member memberToRemove);

    ArrayList<String> getTaskRequirements();

    int getNumOfTaskRequirements();

    void setTaskName(String newTaskName);

    void setTaskPriority(int newTaskPriority);

    void setDueDate(String newDueDateString);

    void setTaskCredit(int newTaskCredit);

    void setTaskState(String newTaskStateString);

    void removeTaskRequirement(int indexOfTaskRequirement);

    void addTaskRequirement(String newTaskRequirement);
}
