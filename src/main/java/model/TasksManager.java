package model;

import model.task.Task;
import model.task.TaskList;
import utils.DukeException;

import java.io.Serializable;
import java.util.ArrayList;

public class TasksManager implements Serializable {
    //TODO ensure tasks are unique, condition is that no two task can have same name. Link storage
    private ArrayList<Task> taskList;

    public TasksManager() {
        taskList = new ArrayList<Task>();
    }

    //@@author JustinChia1997
    /**
     * Basic adding function of task, this can be extended very widely such as including input checks in the
     * individual data model, however this is the prototype model
     */
    public void addTask(String name) throws DukeException {
        if(getTaskByName(name) == null) {
            taskList.add(new Task(name));
        } else {
            throw new DukeException("Duplicated task");
        }
    }

    /**
     * Deletes the task from model
     * */
    public Task deleteTask(String name) throws DukeException {
        name = name.trim();
        int index = getTaskIndex(name);
        if(index >= 0){
            Task temp = taskList.get(index);
            taskList.remove(index);
            return temp;
        } else {
            throw new DukeException("Task does not exist");
        }

    }


    //@@author JustinChia1997
    /**
     * Returns arraylist of tasks. However we can consider building a model for task list itself
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    /**
     * Get task by name
     * */
    public Task getTaskByName(String name){
        Task temp = null;
        for(int i=0; i < taskList.size(); i+=1) {
            if(taskList.get(i).getName() == name ) {
                temp = taskList.get(i);
            }
        }
        return temp;
    }

    //author JustinChia1997
    /**
     * returns the index of the task
     * */
    public int getTaskIndex(String name){
        for(int i=0; i < taskList.size(); i+=1) {
            System.out.println(taskList.get(i).getName());
            if (taskList.get(i).getName() == name) {

                return i;
            }
        }
        return -1;
    }

}
