package models.member;

import models.task.Task;

public class Member implements IMember {
    private String name;
    private String phone;
    private String email;
    private int indexNumber;
    private ListOfTasksAssignedToMember listOfTasksAssignedToMember;

    /**
     * Class representing a member in a project team.
     * @param name The name of the member.
     * @param phone The phone number of the member.
     * @param email The email address of the member.
     * @param indexNumber The index number assigned to the member, unique to the project.
     */
    public Member(String name, String phone, String email, int indexNumber) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.indexNumber = indexNumber;
    }

    @Override
    public String getDetails() {
        return this.indexNumber + ". " + this.name + " (Phone: " + this.phone + " | Email: "
            + this.email + ")";
    }

    @Override
    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }

    @Override
    public int getIndexNumber() {
        return this.indexNumber;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPhone() {
        return this.phone;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void updateDetails(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public void assignTask(Task task) {
        this.listOfTasksAssignedToMember.addAssignedTask(task);
    }

    public boolean isAlreadyAssigned(Task task) {
        return listOfTasksAssignedToMember.contains(task);
    }
}
