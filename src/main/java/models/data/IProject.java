package models.data;

import java.util.ArrayList;
import java.util.HashMap;
import models.member.Member;
import models.member.MemberList;
import models.task.Task;
import models.task.TaskList;

public interface IProject {
    // TODO Add attributes such as Members, Tasks, Name
    String getDescription();

    MemberList getMembers();

    TaskList getTasks();

    int getNumOfMembers();

    int getNumOfTasks();

    void addMember(Member newMember);

    void editMember(int memberIndexNumber, String updatedMemberDetails);

    void removeMember(Member toBeRemoved);

    void addTask(Task newTask);

    void removeTask(int taskIndexNumber);

    boolean memberIndexExists(int indexNumber);

    Task getTask(int taskIndex);

    void editTask(int taskIndexNumber, String updatedTaskDetails);

    void editTaskRequirements(int taskIndexNumber, String[] updatedTaskRequirements, boolean haveRemove);

    ArrayList<String> getAssignedTaskList();

    void createAssignment(Task task, Member member);

    void removeAssignment(Member member, Task task);

    boolean containsAssignment(Task task, Member member);

    HashMap<Member, ArrayList<Task>> getMembersIndividualTaskList();

    HashMap<Task, ArrayList<Member>> getTasksAndAssignedMembers();


}
