package model;

import model.Member;
import common.DukeException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

public class Task {
    private String name;
    private String description;
    private boolean isDone;
    private ArrayList<String> memberList;
    private Date time;
    private Date reminder;

    public Task(String name) {
        this.name = name.trim();
        this.memberList = new ArrayList<>();
    }

    /**
     * add javadoc please
     */
    public void addMember(String toAdd) {
        if (!memberList.contains(toAdd)) {
            memberList.add(toAdd);
        }
    }

    /**
     * add javadoc please
     */
    public void deleteMember(String toDelete) {
        System.out.println(memberList);
        memberList.remove(toDelete);
    }

    public ArrayList<String> getMemberList() {
        return memberList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return isDone;
    }

    public void markAsDone() {
        isDone = true;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getReminder() {
        return reminder;
    }

    public void setReminder(Date time) {
        this.reminder = time;
    }

    /**
     * change task to string to show on window
     *
     * @return string of task
     */
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.name + (time == null ? "" : (" (at: " + time + ")"));
    }

    /**
     * Return the status icon.
     *
     * @return the status icon ("V" for done and "x" for todo) of the task
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2715");
    }

    //@@ JasonChanWQ
    public boolean hasMember(String oldName) {
        return memberList.contains(oldName);
    }

    //@@ JasonChanWQ
    public void updateMember(String oldName, String newName) {
        memberList.set(memberList.indexOf(oldName), newName);
    }
}