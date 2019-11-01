package model;

import common.DukeException;

import java.util.ArrayList;

//@@author chenyuheng
public class Member {
    public static final String EMAIL_VALIDATION_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    public static final String MESSAGE_WRONG_EMAIL_FORMAT = "Wromg email format.";

    private String name;
    private ArrayList<String> taskList;
    private String biography;
    private String email;
    private String phone;

    public Member(String name) {
        this.name = name;
        this.taskList = new ArrayList<>();
    }

    /**
     * add javadoc please
     * */
    public void addTask(String toAdd) {
        if (!taskList.contains(toAdd)) {
            taskList.add(toAdd);
        }
    }

    /**
     * add javadoc please
     * */
    public void deleteTask(String toDelete) {
        if (taskList != null) {
            taskList.remove(toDelete);
        }
    }

    public ArrayList<String> getTaskList() {
        return taskList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name.trim();
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getEmail() {
        return email;
    }

    /**
     * set email
     * @param email email of the member
     * @throws DukeException exception
     */
    public void setEmail(String email) throws DukeException {
        if (email.matches(EMAIL_VALIDATION_REGEX)) {
            this.email = email;
        } else {
            throw new DukeException(MESSAGE_WRONG_EMAIL_FORMAT);
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //@@ JasonChanWQ
    @Override
    public boolean equals(Object obj) {
        return this == obj // if this == obj return true
                || (obj instanceof Member && name.equals(((Member) obj).name));
    }
}

