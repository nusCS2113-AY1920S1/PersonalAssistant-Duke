package tasklist;

import exception.DukeException;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;

import java.util.ArrayList;

import static common.Messages.*;

/**
 * Handles all the operations for the task in the list
 */
public class TaskList {

    private ArrayList<Task> taskList;
    private static String msg = "";

    /**
     * Constructor for the class TaskList
     */
    public TaskList() {
        this.taskList = new ArrayList<Task>();
    }

    /**
     * Constructor to initialize taskList
     * @param taskList loaded tasklist from file
     */
    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Search for matching tasks in taskList
     * @param description String containing the targeted search
     * @return list of matching tasks
     * @throws DukeException if not able to find any matching task
     */
    public ArrayList<String> findTask(String description) throws DukeException{
        ArrayList<String> arrFind = new ArrayList<>();
        for (int i = 0; i < getSize(); i++){
            if(taskList.get(i).getDescription().contains(description)) {
                arrFind.add(taskList.get(i).toString());
            }
        }
        if(arrFind.isEmpty()){
            throw new DukeException(ERROR_MESSAGE_NOTFOUND);
        }else {
            return arrFind;
        }
    }

    /**
     * Get all the tasks in the current taskList
     * @return list of tasks in the taskList
     */
    public ArrayList<String> listTask(){
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++){
            final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add("     " + displayIndex + ". " + taskList.get(i));
        }
        return arrList;
    }

    /**
     * Get number of tasks in taskList
     * @return Integer corresponding to the number of tasks in taskList
     */
    public int getSize(){
        return taskList.size();
    }

    /**
     * Adds deadline task to taskList
     * @param description String containing the description of the task
     * @param by String containing the date and time of the deadline for the task
     */
    public void addDeadlineTask(String description, String by){
        String date = new Deadline(description, by).convertDate(by);
        taskList.add(new Deadline(description, date));
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    /**
     * Adds event task to taskList
     * @param description String containing the description of the task
     * @param at String containing the venue of the event
     */
    public void addEventTask(String description, String at) {
        taskList.add(new Event(description, at));
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    /**
     * Adds todo task to taskList
     * @param description String containing the description of the task
     */
    public void addTodoTask(String description) {
        taskList.add(new Todo(description));
        int index = taskList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + taskList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    /**
     * Mark the task as completed
     * @param i index of the task in taskList
     */
    public void doneTask(int i){
        taskList.get(i).markAsDone();
        System.out.println(MESSAGE_MARKED + "       " + taskList.get(i));
    }

    /**
     * Delete task in taskList
     * @param i index of the task in taskList
     */
    public void deleteTask(int i){
        if (taskList.size() - 1 <= 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_DELETE + "       " + taskList.get(i) + "\n" + MESSAGE_ITEMS1 + (taskList.size() - 1) + msg);
        taskList.remove(taskList.get(i));
    }

    /**
     * Get the current taskList in file
     * @return ArrayList containing tasks
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }
}