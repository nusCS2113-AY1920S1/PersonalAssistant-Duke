package models.data;

import models.member.Member;
import models.member.ProjectMemberList;
import models.task.Task;
import models.task.TaskList;

public class Project implements IProject {
    private String description;
    private ProjectMemberList projectMemberList;
    private TaskList taskList;

    /**
     * Class representing a task in a project.
     * @param description The description of the project.
     */
    public Project(String description) {
        this.description = description;
        this.projectMemberList = new ProjectMemberList();
        this.taskList = new TaskList();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public ProjectMemberList getMembers() {
        return this.projectMemberList;
    }

    @Override
    public TaskList getTasks() {
        return this.taskList;
    }

    @Override
    public int getNumOfMembers() {
        return this.projectMemberList.getNumOfMembers();
    }

    @Override
    public int getNumOfTasks() {
        return this.taskList.getTaskList().size();
    }

    @Override
    public void addMember(Member newMember) {
        this.projectMemberList.addMember(newMember);
    }

    @Override
    public void editMember(int memberIndexNumber, String updatedMemberDetails) {
        this.projectMemberList.editMember(memberIndexNumber, updatedMemberDetails);
    }

    @Override
    public void removeMember(int memberIndexNumber) {
        this.projectMemberList.removeMember(memberIndexNumber);
    }

    @Override
    public void addTask(Task newTask) {
        this.taskList.addTask(newTask);
    }

    @Override
    public void removeTask(int taskIndexNumber) {
        this.taskList.removeTask(taskIndexNumber);
    }

    @Override
    public boolean memberIndexExists(int indexNumber) {
        return (indexNumber > 0 && indexNumber <= this.getNumOfMembers());
    }

    @Override
    public Task getTask(int taskIndex) {
        return this.taskList.getTask(taskIndex);
    }

    @Override
    public void editTask(String updatedTaskDetails) {
        this.taskList.editTask(updatedTaskDetails);
    }

    @Override
    public void editTaskRequirements(int taskIndexNumber, String[] updatedTaskRequirements, boolean haveRemove) {
        this.taskList.editTaskRequirements(taskIndexNumber, updatedTaskRequirements, haveRemove);
    }

}
