package myTasks;

import myTasks.Task;

import java.util.ArrayList;

/**
 * Method to store and manage multiple tasks at once.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
 */
public class TaskList {

    protected ArrayList<Task> myList;

    /**
     * Constructor that converts a given arrayList into a taskList.
     *
     * @param myList The array list to be converted into a taskList.
     */
    public TaskList (ArrayList<Task> myList) {
        this.myList = myList;
    }

    /**
     * Method to display the tasks in the taskList in a format friendly to the user.
     */
    //Method to get the tasks in a list
    public void getList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < myList.size(); i++) { //Standard for-each loop: for (String element: myList)
            System.out.println((i + 1) + "." + myList.get(i).getStatusIcon());
        }
    }

    /**
     * Method to add a new task to the taskList.
     * It will output a message acknowledging that a task has been added successfully.
     *
     * @param taskData The task to be added to the taskList.
     */
    //Method to add a task to a list and output the size of list
    public void addToList(Task taskData) {
        myList.add(taskData);
        System.out.println("Got it. I've added this task:");
        System.out.println(taskData.getStatusIcon());
        System.out.println("Now you have " + myList.size() + " task(s) in the list.");
    }

    /**
     * Method to add a new task to the taskList.
     * This method will not output any messages regarding the addition of the task.
     * Its more for background additions without spamming the user with texts.
     *
     * @param taskData The task to be added to the taskList.
     */
    //Adds to list from save data without spamming "got it..."
    public void addToListQuietly(Task taskData) {
        myList.add(taskData);
    }

    /**
     * Method to remove a task corresponding to the task number from the taskList.
     * The parser class has accounted for and updated this taskNumber to fit computing standards.
     *
     * @param taskNumber The number of the task to be removed from the taskList.
     */
    //method to remove task from list
    public void removeFromList(int taskNumber) {
        myList.remove(taskNumber);
    }

    /**
     * Method to update the status of a task in the taskList.
     * The parser class has accounted for and updated this taskNumber to fit computing standards.
     *
     * @param taskNumber The number of the task to be updated.
     */
    //method to update the status of a task
    public void updateTask(int taskNumber) {
        myList.get(taskNumber).markAsDone();
    }

    /**
     * Method to return and display the data of a task corresponding to the task number.
     *
     * @param taskNumber The number of the task to be displayed
     * @return The entire data of the task as a task type.
     */
    //Method to display task description and stats as a task
    public Task getTask(int taskNumber) {
        return myList.get(taskNumber);
    }

    /**
     * Method to return the number of tasks in the taskList as an integer.
     * For tracking the number of tasks in the taskList.
     *
     * @return The size of the taskList.
     */
    //method to return size of list
    public int getSize() {
        return myList.size();
    }
}
