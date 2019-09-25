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
        System.out.println("Deleted task number " + index + " !");
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

    /**
     * tells the user goodbye
     */
    public void showBye() {
        System.out.println("Goodbye!");
    }

    /**
     * tells the user that Duke is listing the tasks
     */
    public void showList() {
        System.out.println("Listing tasks in your task list...");
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
}
