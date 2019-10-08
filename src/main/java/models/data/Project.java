package models.data;

import models.member.Member;
import models.member.MemberList;
import models.task.Task;
import models.task.TaskList;

public class Project implements IProject {
    private String description;
    private MemberList memberList;
    private TaskList taskList;

    public Project(String description) {
        this.description = description;
        this.memberList = new MemberList();
        this.taskList = new TaskList();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public MemberList getMembers() {
        return this.memberList;
    }

    @Override
    public TaskList getTasks() {
        return this.taskList;
    }

    @Override
    public int getNumOfMembers() {
        return 0;
    }

    @Override
    public void addMember(Member newMember) {
        this.memberList.addMember(newMember);
    }

    @Override
    public void addTask(Task newTask) {
        this.taskList.addTask(newTask);
    }
}
