package model;

import common.LoggerController;
import model.Member;
import common.DukeException;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

public class Task {
    private String name;
    private String description;
    private boolean isDone;
    private ArrayList<String> memberList;
    private ArrayList<String> skillReqList;
    private Date time;
    private Date reminder;

    /**
     * Task object model
     */
    public Task(String name) {
        this.name = name.trim();
        this.memberList = new ArrayList<>();
        this.skillReqList = new ArrayList<String>();
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
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MM-yyyy HH:mm'H'");
        return "[" + this.getStatusIcon() + "] " + this.name + (time == null ? "" : (" (due: "
                + sdf.format(time) + ")"));
    }

    /**
     * Return the status icon.
     *
     * @return the status icon ("V" for done and "x" for todo) of the task
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2715");
    }

    //@@author JustinChia1997
    public ArrayList<String> getReqSkills() {
        return this.skillReqList;
    }

    /**
     * Adds a required skill to required skills array
     *
     * @return a boolean if addition was successful
     */
    public boolean addReqSkill(String skillName) {
        //TODO add regex to check for skillName
        if (!skillReqList.contains(skillName)) {
            skillReqList.add(skillName);
            LoggerController.logDebug(Task.class, "Added skill into model" + skillName);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> getSkillReqList() {
        return skillReqList;
    }


    /**
     * Checks if member has a skill
     *
     * @param skillName is the skillname you are searching for
     */
    public boolean hasSkill(String skillName) {
        if (skillReqList != null) {
            return skillReqList.contains(skillName);
        }
        return false;
    }

    //@@author JasonChanWQ
    public boolean hasMember(String oldName) {
        return memberList.contains(oldName);
    }

    //@@author JasonChanWQ
    public void updateMember(String oldName, String newName) {
        memberList.set(memberList.indexOf(oldName), newName);
    }

}