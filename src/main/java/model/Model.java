package model;

import utils.DukeException;

import java.util.ArrayList;

public interface Model {
    //==================Storage Interface======================
    void load();
    void save();

    //==================Task Interface======================
    ArrayList<Task> getTaskList();
    void addTask(String name) throws DukeException;
    Task deleteTask(String name) throws DukeException;

    //==================Member Interface======================
    ArrayList<Member> getMemberList();
    void addMember(String name) throws DukeException;
    Member deleteMember(String name) throws DukeException;

    //==================Task and Member Interface======================
    void link(int tasksIndexes, String memberNames);
    void unlink(int tasksIndexes, String memberNames);
}
