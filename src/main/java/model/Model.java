package model;

import common.DukeException;
import core.Duke;

import java.util.ArrayList;

/**
 * Exposes the api of Model, this falls in line with open close policy, allowing the different implementations of
 * model to be used, without having to change other major parts of the code
 */
public interface Model {
    //@@author JustinChia1997
    //==================Storage Interface======================

    void load();

    void save();

    //==================Task Interface======================
    ArrayList<Task> getTaskList();

    int getTaskListSize();

    String getTaskNameById(int index);

    TasksManager getTasksManager();

    Task addTask(String name) throws DukeException;

    boolean hasTask(String name) throws DukeException;

    String getTasksByKeyword(String keyword) throws DukeException;

    String scheduleTeamAll() throws DukeException;

    String scheduleTeamTodo() throws DukeException;

    String updateTaskDes(int index, String des);

    //TODO look into using exceptions instead?
    boolean addTaskReqSkill(String taskName, String skillName);


    //==================Member Interface======================
    ArrayList<Member> getMemberList();

    int getMemberListSize();

    String getMemberNameById(int index);

    MemberManager getMemberManager();

    void addMember(String name) throws DukeException;

    String updateMemberBio(int index, String bio);

    String updateMemberEmail(int index, String email) throws DukeException;

    String updateMemberPhone(int index, String phone);

    boolean hasMember(String name) throws DukeException;

    boolean addMemberSkill(String memberName, String skillName);

    //==================Task and Member Interface======================
    void link(int tasksIndexes, String memberNames);

    void unlink(int tasksIndexes, String memberNames);

    String deleteTask(int index) throws DukeException;

    String deleteMember(int index) throws DukeException;

    String scheduleMemberAll(String memberName) throws DukeException;

    String scheduleMemberTodo(String memberName) throws DukeException;

}
