package model;

import common.DukeException;
import core.Duke;

import java.util.ArrayList;
import java.util.Date;

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

    String getTasks();

    String getTodoTasks();

    String getTaskNameById(int index);

    String getTaskNameByIdOnList(int indexOfTask);

    String getTaskIsDoneByIdOnList(int indexOfTask);

    String getTaskDescriptionByIdOnList(int indexOfTask);

    Date getTaskDateTimeByIdOnList(int indexOfTask);

    ArrayList<String> getMemberListOfTaskByIdOnList(int indexOfTask);

    ArrayList<String> getSkillListOfTaskByIdOnList(int indexOfTask);

    Date getTaskReminderByIdOnList(int indexOfTask);

    TasksManager getTasksManager();

    String getTasksByKeyword(String keyword) throws DukeException;

    Task addTask(String name) throws DukeException;

    boolean hasTask(String name) throws DukeException;

    String tasksAllInorderTime() throws DukeException;

    String tasksTodoInorderTime() throws DukeException;

    String tasksAllInorderPicNum();

    String tasksTodoInorderPicNum();

    String updateTaskDes(int index, String des);

    boolean isInTaskList(int taskIndex);

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

    String membersInorderProgress();

    String membersInorderTodoNum();

    int getMemberIdByName(String name);

    String getMemberBioByName(String name);

    String getMemberEmailByName(String name);

    String getMemberPhoneByName(String name);

    ArrayList<String> getTaskListOfMemberByName(String name);

    ArrayList<String> getSkillListOfMemberByName(String name);

    //==================Task and Member Interface======================
    void link(int tasksIndexes, String memberNames);

    void unlink(int tasksIndexes, String memberNames) throws DukeException;

    String deleteTask(int index) throws DukeException;

    String deleteMember(int index) throws DukeException;

    String scheduleMemberAll(String memberName) throws DukeException;

    String scheduleMemberTodo(String memberName) throws DukeException;

    String check();
}
