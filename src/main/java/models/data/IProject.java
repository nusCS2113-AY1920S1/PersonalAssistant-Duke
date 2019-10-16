package models.data;

import models.member.Member;
import models.member.ProjectMemberList;
import models.task.Task;
import models.task.TaskList;

public interface IProject {
    // TODO Add attributes such as Members, Tasks, Name
    String getDescription();

    ProjectMemberList getMembers();

    TaskList getTasks();

    int getNumOfMembers();

    int getNumOfTasks();

    void addMember(Member newMember);

    void editMember(int memberIndexNumber, String updatedMemberDetails);

    void removeMember(int memberIndexNumber);

    void addTask(Task newTask);

    void removeTask(int taskIndexNumber);

    boolean memberIndexExists(int indexNumber);

    Task getTask(int taskIndex);

    void editTask(String updatedTaskDetails);

    void editTaskRequirements(int taskIndexNumber, String[] updatedTaskRequirements, boolean haveRemove);
}
