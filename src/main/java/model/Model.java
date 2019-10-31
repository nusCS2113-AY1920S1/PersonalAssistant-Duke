package model;

import common.DukeException;
import core.Duke;

import java.util.ArrayList;
//@@authorJustinChia1997
/**
 * Exposes the api of Model, this falls in line with open close policy, allowing the different implementations of
 * model to be used, without having to change other major parts of the code
 *
 * */
public interface Model {

    //==================Storage Interface======================
    void load();

    void save();

    //==================Task Interface======================
    ArrayList<Task> getTaskList();

    int getTaskListSize();

    TasksManager getTasksManager();

    Task addTask(String name) throws DukeException;

    String deleteTask(int taskIndexInList) throws DukeException;


    boolean hasTask(String name) throws DukeException;

    String getTasksByKeyword(String keyword) throws DukeException;

    String scheduleTeamAll() throws DukeException;

    String scheduleTeamTodo() throws DukeException;

    String scheduleMemberAll(String memberName) throws DukeException;

    String scheduleMemberTodo(String memberName) throws DukeException;

    //==================Member Interface======================
    ArrayList<Member> getMemberList();

    int getMemberListSize();

    MemberManager getMemberManager();

    void addMember(String name) throws DukeException;

    boolean deleteMember(String name) throws DukeException;

    boolean hasMember(String name) throws DukeException;

    //==================Task and Member Interface======================
    void link(int tasksIndexes, String memberNames);

    void unlink(int tasksIndexes, String memberNames);
}
