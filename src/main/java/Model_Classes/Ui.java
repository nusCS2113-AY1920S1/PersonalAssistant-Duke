package Model_Classes;

public class Ui {
    public Ui () {
    }
    public void startUp() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("How may I serve you?");
    }
    public void showLoadError () {
        System.out.println("Error in loading data file, initialising empty task list...");
    }
    public void showDeleted (int index) {
        System.out.println("Deleted task!");
    }
    public void showFind () {
        System.out.println("Searching for item in task list...");
    }
    public void showDone () {
        System.out.println("Completed task! Updating task list...");
    }
    public void showAdd () {
        System.out.println("Adding new task to list...");
    }
    public void showBye() {
        System.out.println("Goodbye!");
    }
    public void showList() {
        System.out.println("Listing tasks in your task list...");
    }
    public void showWriteError() {
        System.out.println("Error in writing file, cancelling write process...");
    }
    public void showIndexError() {
        System.out.println("Index you've entered doesn't exist, try with another index!");
    }
    public void showCommandError() {
        System.out.println("Sorry, I don't understand this command...");
        System.out.println("Try typing todo, deadline or event followed by the task description to add tasks to your todo list");
        System.out.println("    Type list, find, done, delete to perform operations on your todo list");
    }
    public void showEmptyDescriptionError() {
        System.out.println("Looks like your task descriptor is empty! Please add an item description!");
    }
    public void showDateError() {
        System.out.println("please input date format as dd/mm/yyyy HH:mm");
        System.out.println("example: deadline meeting/30/12/2019 18:00");
        System.out.println("example: event concert/30/12/2019 18:00");
    }
}
