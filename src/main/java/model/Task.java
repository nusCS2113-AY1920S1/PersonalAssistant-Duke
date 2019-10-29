package model;

import model.Member;
import utils.DukeException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

//@@author chenyuheng
public class Task {
    private String name;
    private String description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}