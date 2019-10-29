package model;

import common.DukeException;

import java.io.Serializable;
import java.util.ArrayList;

public class TasksManager implements Serializable {
    public static final String MESSAGE_DUPLICATED_TASK_NAME = "Duplicated task name.";
    private ArrayList<Task> taskList;

    /**
     * add javadoc please
     * */
    public TasksManager(ArrayList<Task> taskList) {
        if (taskList != null) {
            this.taskList = taskList;
        } else {
            this.taskList = new ArrayList<Task>();
        }
    }

    //@@author JustinChia1997

    /**
     * Add a new task with name.
     *
     * @param name The name of the new task, case sensitive.
     * @throws DukeException If duplicated task name if found.
     */
    public Task addTask(String name) throws DukeException {
        name = name.trim();
        if (!hasTask(name)) {
            Task newTask = new Task(name);
            taskList.add(newTask);
            return newTask;
        } else {
            throw new DukeException(MESSAGE_DUPLICATED_TASK_NAME);
        }
    }

    /**
     * Get the Task object by its id.
     *
     * @param id The id, or the index of the Task ArrayList, which is non-persistent.
     *           An id starts with 0.
     * @return Return the Task object if found, return null if index is wrong.
     */
    public Task getTaskById(int id) {
        if (id >= 0 && id < taskList.size()) {
            return taskList.get(id);
        }
        return null;
    }

    /**
     * Delete a task from the task list.
     *
     * @param taskIndexInList The task index in tasklist to be deleted.
     */
    public Task deleteTask(int taskIndexInList) {

        return taskList.remove(taskIndexInList - 1);
    }

    /**
     * Add link(s) from task(s) to member(s). Duplicated link will be cancelled.
     *
     * @param tasks Array of Member objects to link.
     * @param toAdd Array of Member object to link.
     */
    public void addMember(Task[] tasks, String[] toAdd) {
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < toAdd.length; j++) {
                tasks[i].addMember(toAdd[j]);
            }
        }
    }

    /**
     * Delete link(s) from task(s) to member(s). Non-existing link won't be deleted.
     * This is the reverse method of <code>addMember(Task[] tasks, Member[] toAdd)</code> method.
     *
     * @param tasks arraylist
     * @param toDelete arraylist
     */
    public void deleteMember(Task[] tasks, String[] toDelete) {
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < toDelete.length; j++) {
                tasks[i].deleteMember(toDelete[j]);
            }
        }
    }

    //@@author yuyanglin28

    /**
     * delete member (person in charge) in task list
     * @param memberName member name to be deleted
     */
    public void deleteMemberInTasks(String memberName) {
        for (int i = 0; i < taskList.size(); i++) {
            Task toCheck = taskList.get(i);
            toCheck.deleteMember(memberName);
        }
    }

    //@@author JustinChia1997
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public int getTaskListSize() {
        return taskList.size();
    }


    //@@author JustinChia1997
    /**
     * checks if task is present in task list
     * */
    public boolean hasTask(String name) {
        for (int i = 0; i < taskList.size(); i += 1) {
            if (taskList.get(i).getName().equals(name.trim())) {
                return true;
            }
        }
        return false;
    }

    //@@author JustinChia1997

    /**
     * Finds Task from task list. returns null if no match was found
     *
     * @param name arraylist
     * @return Task
     */
    public Task getTaskByName(String name) {
        for (int i = 0; i < taskList.size(); i += 1) {
            if (taskList.get(i).getName().equals(name.trim())) {
                return taskList.get(i);
            }
        }
        return null;
    }

    public String getNameByTask(Task task) {
        return task.getName();
    }

}
