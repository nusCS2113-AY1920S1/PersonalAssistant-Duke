package models.data;

import models.member.Member;
import models.member.MemberList;
import models.task.Task;
import models.task.TaskList;

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
    public void addMember(Member newMember) {
        /*
        Empty method
         */
    }

    @Override
    public void editMember(int memberIndexNumber, String updatedMemberDetails) {

    }

    @Override
    public void addTask(Task newTask) {
        /*
        Empty method
         */
    }
}
