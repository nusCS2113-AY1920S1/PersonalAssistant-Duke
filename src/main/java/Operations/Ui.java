package Operations;

/**
 * class to tell user about errors and completion of operations
 */
public class Ui {
    /**
     * Constructor for Ui class
     */
    public Ui () {
    }

    /**
     * Shows the startup logo for Duke
     */
    public void startUp() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("How may I serve you?");
        System.out.println("Enter 'help' if you require assistance");
    }

    public void help() {
        System.out.println("List of Commands:\n" +
                "todo\n" + "deadline\n" + "event\n" + "recur\n" + "snooze\n" + "list\n" + "done\n" + "find\n" + "bye" );
    }

    /**
     * Prints an error message telling the user that the data file cannot be loaded and an empty list is generated instead
     */
    public void showLoadError () {
        System.out.println("Error in loading data file, initialising empty task list...");
    }

    /**
     * Prints a message telling the user that the task at the index has been deleted.
     * @param index Index of task to be deleted.
     */
    public void showDeleted (int index) {
        System.out.println("Deleted task number " + index + "!");
    }

    /**
     * Tells the user that the search operation is executing
     */
    public void showFind () {
        System.out.println("Searching for item in task list...");
    }

    /**
     * Tells the user that the task of index has been done and the list has been updated.
     */
    public void showDone () {
        System.out.println("Completed task! Your task list has been updated");
    }

    /**
     * tells the user that a task has been added into the list
     */
    public void showAdd () {
        System.out.println("Your task has been added into the list!");
    }

    public void showAddRecur () {
        System.out.println("Your task has been added into the recurring list!");
    }
    /**
     * tells the user goodbye
     */
    public void showBye() {
        System.out.println("Goodbye!");
    }
    public void showExit() {
        System.out.println("You have exited recurring list mode");
    }
    /**
     * tells the user that Duke is listing the tasks
     */
    public void showList() {
        System.out.println("Listing tasks in your task list...");
    }
    public void showListRecur() {
        System.out.println("Listing tasks in your recurring task list...");
    }
    /**
     * tells the user that there is an error in writing the data.txt file.
     */
    public void showWriteError() {
        System.out.println("Error in writing file, cancelling write process...");
    }

    /**
     * tells the user that the index specified doesn't exist
     */
    public void showIndexError() {
        System.out.println("Index you've entered doesn't exist, try with another index!");
    }

    /**
     * Tells the user that an invalid command has been input into Duke.
     */
    public void showCommandError() {
        System.out.println("Sorry, I don't understand this command...");
        System.out.println("Try typing todo, deadline or event followed by the task description to add tasks to your todo list");
        System.out.println("    Type list, find, done, delete to perform operations on your todo list");
    }

    /**
     * reminds user to put description for tasks
     */
    public void showEmptyDescriptionError() {
        System.out.println("Looks like your task descriptor is empty! Please add an item description!");
    }

    /**
     * tells user how to format date due to error in date format
     */
    public void showDateError() {
        System.out.println("please input date format as dd/mm/yyyy HH:mm");
        System.out.println("example: deadline meeting/30/12/2019 18:00");
        System.out.println("example: event concert/30/12/2019 18:00");
    }

    /**
     * Tells the user that they are in recurring mode to add or perform operations on recurring tasks
     *
     */
    public void promptRecurringActions() {
        System.out.println("You have accessed Recurring Mode");
        System.out.println("You are now accessing and performing operations on the Recurring Task List");
        System.out.println("Tasks that appear here will appear based on when you want it to appear again");
        System.out.println("Please input your intended action\n");
        System.out.println("To add a new task into the recurring task list, input 'add'");
        System.out.println("To perform operations like find or list, do it similar to how you would do it normally");
        System.out.println("\tE.g : find homework");
        System.out.println("\tE.g : list");
        System.out.println("To quit recurring mode, input 'exit'.");
    }

    /**
     * Prompts the user to describe the recurrence schedule of the task
     */
    public void promptForRecurrence() {
        System.out.println("Please enter the recurrence of the task, either by day, week or month");
        System.out.println("\tE.g : Day");
        System.out.println("\tE.g : Week");
        System.out.println("\tE.g : Month");
    }

    /**
     * Prompts the user on how to add new recurring tasks
     */
    public void promptForTask() {
        System.out.println("now enter your desired task as usual!");
        System.out.println("\tE.g : todo buy groceries#monday");
        System.out.println("\tE.g : todo buy groceries#month");
        System.out.println("\tE.g : deadline homework 22/12/2019 18:00#monday");
        System.out.println("\tE.g : deadline homework 22/12/2019 18:00#month");
        System.out.println("\tE.g : event meeting 22/12/2019 18:00#monday");
        System.out.println("\tE.g : event meeting 22/12/2019 18:00#month\n");
    }

    /**
     * tells the user that recurring tasks have appeared.
     */
    public void showChangeInTaskList() {
        System.out.println("You have some recurring tasks that need to be cleared, please check them:");
    }

    /**
     * tells the user to input the amount of time to snooze the task
     */
    public void showSnooze() {
        System.out.println("Please indicate the amount of time you want to snooze this task");
    }

    /**
     * tells the user that the requested task has been snoozed
     */
    public void showSnoozeComplete() {
        System.out.println("Great I've snoozed your task");
    }

    /**
     * Asks the user for the duration of the task
     */
    public void promptForDuration() {
        System.out.println("How long is the duration for this event?");
        System.out.println("Please specify hours, minutes");
        System.out.println("\tE.g : hours 2");
    }

    /**
     * Asks the user whether the task has a duration
     */
    public void promptForReply() {
        System.out.println("Does this task have a duration? (yes/no)");
    }

    public void promptForTime() {
        System.out.println("Enter the amount of time");
    }

    public void showTimeError(){
        System.out.println("Please indicate a valid amount of time");
    }
}
