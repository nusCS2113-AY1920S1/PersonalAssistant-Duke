package models.data;

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

    void addMember(Member newMember);

    void editMember(int memberIndexNumber, String updatedMemberDetails);

    void removeMember(int memberIndexNumber);

    void addTask(Task newTask);

}
