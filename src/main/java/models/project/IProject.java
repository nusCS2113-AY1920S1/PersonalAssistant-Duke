package models.project;

import models.member.IMember;
import models.member.IMemberList;
import models.member.Member;
import models.reminder.Reminder;
import models.task.ITask;
import models.task.Task;
import models.task.TaskList;

import java.util.ArrayList;
import java.util.HashMap;

public interface IProject {
    String getName();

    IMemberList getMemberList();

    TaskList getTaskList();

    boolean taskExists(ITask task);

    String getTaskIndexName(Integer index);

    int getNumOfMembers();

    int getNumOfTasks();

    void addMember(Member newMember);

    String editMember(int memberIndexNumber, String updatedMemberDetails);

    void removeMember(Member toBeRemoved);

    Member getMember(int indexNumber);

    boolean memberExists(IMember newMember);

    void addTask(Task newTask);

    void removeTask(int taskIndexNumber);

    Task getTask(int taskIndex);

    String[] editTask(int taskIndexNumber, String updatedTaskDetails);


    String[] editTaskRequirements(int taskIndexNumber, String updatedTaskRequirements);

    ArrayList<String> getCredits();

    void createAssignment(Task task, Member member);

    void removeAssignment(Member member, Task task);

    boolean containsAssignment(Task task, Member member);

    HashMap<String, ArrayList<String>> getMembersIndividualTaskList();

    HashMap<String, ArrayList<String>> getTasksAndAssignedMembers();

    IMember getMemberFromID(String memberID);

    ITask getTaskFromID(String taskID);

    void addReminderToList(Reminder reminder);

    ArrayList<Reminder> getReminderList();

    void markReminder(Boolean isDone, int index);

    Reminder getReminder(int index);

    void removeReminder(int index);

    int getReminderListSize();

}
