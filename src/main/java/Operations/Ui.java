package Operations;

import Enums.SortType;
import Enums.TimeUnit;
import Enums.Color;
import Enums.HelpMessage;

import java.io.IOException;

/**
 * Class to tell user about errors and completion of operations.
 */
public class Ui {
    public static final String LOGO = Color.BLUE
            + "    ******\n"
            + "    ***  ****\n"
            + "    ***     ***\n"
            + "    *****    ***\n"
            + "    ***  ********\n"
            + "    ***    *****  ******\n"
            + "     **    ***   ****   ****\n"
            + "            ***   ***     * \n"
            + "             ***    ***\n"
            + "              ***      ***\n"
            + "                  **     ***\n"
            + "                    ***    ***\n"
            + "                       *******\n"
            + Color.RESET;

    /**
     * Shows the startup logo for RoomShare.
     */
    public void startUp() {
        System.out.println("Hello from RoomShare!\n" + LOGO);
        System.out.println("Enter 'help' if you require assistance");
    }

    /**
     * Print out a help message corresponding to the user's query
     * @param message
     */
    public void showHelpMessage(HelpMessage message) {
        System.out.println(message);
    }

    /**
     * Prints a message telling the user that the task at the index has been deleted.
     *
     * @param index Index of task to be deleted.
     */

    public void showDeleted(int[] index) {
        if (index.length == 1) {
            System.out.println("Deleted task number " + (index[0] + 1) + "!");
        } else {
            System.out.println("Deleted task number " + (index[0] + 1) + " to " + (index[1] + 1) + " !");
        }
    }

    /**
     * Tells the user that the search operation is executing.
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
     * tells the user that a task has been added into the list.
     */
    public void showAdd() {
        System.out.println("Your task has been added into the list!");
    }

    /**
     * tells the user goodbye.
     */
    public void showBye() {
        System.out.println("Goodbye!");
    }

    /**
     * tells the user that RoomShare is listing the tasks.
     */
    void showList() {
        System.out.println("Listing tasks in the common task list...");
    }

    /**
     * Tells the user that an invalid command has been input into RoomShare.
     */
    public void showCommandError() {
        System.out.println("Invalid command! Type \"help\" to find out more about available commands");
    }

    /**
     * tells the user that recurring tasks have appeared.
     */
    public void showChangeInTaskList() {
        System.out.println("You have some recurring tasks that need to be cleared, please check them:");
    }

    /**
     * tells the user that the requested task has been snoozed.
     */
    public void showSnoozeComplete(int index, int amount, TimeUnit unit) {
        System.out.println("Great I've snoozed task " + index + " by " + amount + " " + unit.name());
    }

    /**
     * Provides user with instructions to prioritise task.
     */
    public void priorityInstruction() {
        System.out.println("Enter task index followed by priority (high, medium, low)");
        System.out.println("\te.g. 1 high");
    }

    /**
     * Notifies the user that their task's priority has been set.
     */
    public void prioritySet() {
        System.out.println("Your task's priority has been set");
    }

    /**
     * Prompt user to enter the second index.
     */
    public void promptSecondIndex() {
        System.out.println("Please enter the index to swap to");
    }

    public void showReordering() {
        System.out.println("Reordering the task list...");
    }

    /**
     * Show the message of an error encountered.
     * @param e the encountered error
     */
    public void showError(Exception e) {
        System.out.println(Color.RED.toString() + e + Color.RESET.toString());
    }

    public void showLogSuccess(String filePath) {
        System.out.println("Log has been successfully written to " + filePath);
    }

    public void showUpdated(int index) {
        System.out.println("Great! I've updated task " + index);
    }

    public static void clearScreen() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    public void showBar(String bar) {
        System.out.println(bar);
    }

    public void showChangeInPriority(SortType sortType) {
        System.out.println("Your sorting preferences have been set to " + sortType.toString());
    }

    void showSort() {
        System.out.print("sort: ");
        if (TaskList.getSortType().equals(SortType.priority)) {
            System.out.println("Priority");
        } else if (TaskList.getSortType().equals(SortType.alphabetical)) {
            System.out.println("Alphabetical");
        } else if (TaskList.getSortType().equals(SortType.deadline)) {
            System.out.println("Deadline");
        } else {
            System.out.println("Type");
        }
    }

    public void showRestoreList() {
        System.out.println("This are the items in your restore list");
    }

    public void showOverdueList() {
        System.out.println("Here are your overdue tasks: ");
    }

    public void showTagged(String user) {
        System.out.println("These are the tasks assigned to " + user + ":");
    }

    public void showTaggedPercentage(String user) {
        System.out.println("The completion status for '" + user + "' is:");
    }

    public void showDeletedList() {
        System.out.println("Here are the tasks that you have deleted and are in temporary storage");
    }

    public void showDoneList() {
        System.out.println("These are the tasks that you have already done:");
    }
}
