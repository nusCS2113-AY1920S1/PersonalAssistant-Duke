package model;

import utils.DukeException;

import java.io.Serializable;
import java.util.ArrayList;

public class TasksManager implements Serializable {
    public static final String MESSAGE_DUPLICATED_TASK_NAME = "Duplicated task name.";
    private ArrayList<Task> taskList;

    public TasksManager(ArrayList<Task> taskList) {
        if(taskList != null) {
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
    public void addTask(String name) throws DukeException {
        name = name.trim();
        if (!hasTask(name)) {
            taskList.add(new Task(name));
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
     * @param toDelete The Task object to be deleted.
     */
    public void deleteTask(Task toDelete) {
        taskList.remove(toDelete);
    }

    /**
     * Add link(s) from task(s) to member(s). Duplicated link will be cancelled.
     *
     * @param tasks Array of Member objects to link.
     * @param toAdd Array of Member object to link.
     */
    public void addMember(Task[] tasks, Member[] toAdd) {
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
     * @param tasks
     * @param toDelete
     */
    public void deleteMember(Task[] tasks, Member[] toDelete) {
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < toDelete.length; j++) {
                tasks[i].deleteMember(toDelete[j]);
            }
        }
    }

    //@@author JustinChia1997
    public ArrayList<Task> getTaskList() {
        return taskList;
    }


    //@@author JustinChia1997
    private boolean hasTask(String name) {
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
     * @param name
     * @return Task
     * */
    public Task getTaskByName(String name) {
        for (int i = 0; i < taskList.size(); i += 1) {
            if (taskList.get(i).getName().equals(name.trim())) {
                return taskList.get(i);
            }
        }
        return null;
    }

}
