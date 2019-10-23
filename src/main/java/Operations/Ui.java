package Operations;

import Enums.TimeUnit;

import java.io.IOException;

/**
 * class to tell user about errors and completion of operations
 */
public class Ui {
    /**
     * Constructor for Ui class
     */
    public Ui() {
    }

    /**
     * Shows the startup logo for RoomShare
     */
    public void startUp() {
        String logo = "******   ******\n"
                    + "**  **   **\n"
                    + "**  **   **\n"
                    + "******   ******\n"
                    + "******       **\n"
                    + "**   **      **\n"
                    + "**    ** ******";
        System.out.println("Hello from RoomShare!\n" + logo);
        System.out.println("How may I serve you?");
        System.out.println("Enter 'help' if you require assistance");
    }


    public void helpAdd() {
        System.out.println("adds a Meeting or Assignment to the list");
        System.out.println("You must specify the description, type of task, and time of the task");
        System.out.println("Each field has a particular format of entry");
        System.out.println("Type of task must be either meeting or assignment, wrapped in '#'\n" +
                "\te.g #meeting# # assignment#");
        System.out.println("Description must be wrapped in parentheses\n " +
                "\te.g (description)");
        System.out.println("Priority must be either high medium or low, wrapped in asterisks '*'\n " +
                "\te.g *low*");
        System.out.println("Time must be specified, wrapped in '&'\n" +
                "\te.g &22/12/2019 18:00&  &this friday 13:00&  &next monday 14:00&  &tmr 16:00&");
        System.out.println("If time isn't specified, then the duration of the task must at least be specified");
        System.out.println("Duration can be specified by wrapping in '^', in terms of number of hours or number of minutes");
        System.out.println("\te.g ^2 hours^ ^1 minutes^");
        System.out.println("Recurrence of the task can be specified by wrapping either days, weeks or months" +
                "\nin '%'\n\te.g %day% %week% %month%");
        System.out.println("Task can also be assigned to a name, by wrapping the name in '@'\n" +
                "\te.g @Alice@");
        System.out.println("You must specify the task type, description, and either time or duration");
        System.out.println("The rest of the fields can still be changed later using other commands");
    }

    public void helpDelete() {
        System.out.println("Deletes the tasks in the index or the specified range");
        System.out.println("\te.g delete 1");
        System.out.println("\te.g delete 3 - 5");
    }

    /**
     * Prints an error message telling the user that the data file cannot be loaded and an empty list is generated instead
     */
    public void showLoadError() {
        System.out.println("Error in loading data file, initialising empty task list...");
    }

    /**
     * Prints a message telling the user that the task at the index has been deleted.
     *
     * @param index Index of task to be deleted.
     */

    public void showDeleted(int[] index) {
        if (index.length == 1)
            System.out.println("Deleted task number " + (index[0] + 1) + "!");
        else
            System.out.println("Deleted task number " + (index[0] + 1) + " to " + (index[1] + 1) + " !");
    }

    /**
     * Tells the user that the search operation is executing
     */
    public void showFind() {
        System.out.println("Searching for item in task list...");
    }

    /**
     * Tells the user that the task of index has been done and the list has been updated.
     */
    public void showDone() {
        System.out.println("Completed task! Your task list has been updated");
    }

    /**
     * tells the user that a task has been added into the list
     */
    public void showAdd() {
        System.out.println("Your task has been added into the list!");
    }

    /**
     * tells the user goodbye
     */
    public void showBye() {
        System.out.println("Goodbye!");
    }

    /**
     * tells the user that RoomShare is listing the tasks
     */
    public void showList() {
        System.out.println("Listing tasks in your task list...");
    }

    /**
     * Tells the user that an invalid command has been input into RoomShare.
     */
    public void showCommandError() {
        System.out.println("Sorry, I don't understand this command...");
        System.out.println("Try typing todo, deadline or event followed by the task description to add tasks to your todo list");
        System.out.println("    Type list, find, done, delete to perform operations on your todo list");
    }

    /**
     * tells the user that recurring tasks have appeared.
     */
    public void showChangeInTaskList() {
        System.out.println("You have some recurring tasks that need to be cleared, please check them:");
    }

    /**
     * tells the user that the requested task has been snoozed
     */
    public void showSnoozeComplete(int index, int amount, TimeUnit unit) {
        System.out.println("Great I've snoozed task " + index + " by " + amount + " " + unit.name());
    }

    /**
     * Provides user with instructions to prioritise task
     */
    public void priorityInstruction() {
        System.out.println("Enter task index followed by priority (high, medium, low)");
        System.out.println("\te.g. 1 high");
    }

    /**
     * Notifies the user that their task's priority has been set
     */
    public void prioritySet() {
        System.out.println("Your task's priority has been set");
    }

    public void promptSecondIndex() {
        System.out.println("Please enter the index to swap to");
    }

    public void showReordering() {
        System.out.println("Reordering the task list...");
    }

    public void helpList() {
        System.out.println("Here are a list of commands you can input: \n add \n list \n find \n delete \n" +
                "For more information about a specific command you can \nEnter help followed by a command, eg. help add");
    }

    public void showError(Exception e){
        System.out.println(e);
    }
}
