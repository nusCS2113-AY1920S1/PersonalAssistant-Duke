package model;

import model.Member;
import common.DukeException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

//@@author chenyuheng
public class Task {
    private String name;
    private boolean isDone;
    private ArrayList<String> memberList;
    private Date time;

    public Task(String name) {
        this.name = name.trim();
        this.memberList = new ArrayList<>();
    }

    /**
     * add javadoc please
     * */
    public void addMember(String toAdd) {
        if (!memberList.contains(toAdd)) {
            memberList.add(toAdd);
        }
    }

    /**
     * add javadoc please
     * */
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * change task to string to show on window
     * @return string of task
     */
    public String toString() {
        return "[" + this.getStatusIcon() + "] "
                + this.name
                + " (at: " + time + ")";
    }

    /**
     * Return the status icon.
     *
     * @return the status icon ("V" for done and "x" for todo) of the task
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2715");
    }
}