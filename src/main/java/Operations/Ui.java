package Operations;

import Enums.SortType;
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
     * Shows the startup logo for RoomShare.
     */
    public void startUp() {
        String logo = "    &@\n"
                + "    #@&@@.\n"
                + "    #&&  .@@,\n"
                + "    %&&     %@,\n"
                + "    #@&@%    %@#\n"
                + "    %&&  @@@&&&&.\n"
                + "    &&&    @@##/  @@@@@#\n"
                + "     (@    .@&   .@@.   &@@.\n"
                + "            /@%   (@(     * \n"
                + "             @@*    #@%\n"
                + "              #@,      @@#\n"
                + "                  /*     *@@\n"
                + "                    %@.    @@.\n"
                + "                       #@@@&@*\n";
        System.out.println("Hello from RoomShare!\n" + logo);
        System.out.println("Enter 'help' if you require assistance");
    }


    void helpAdd() {
        System.out.println("Adds a Meeting or Assignment to the list\n");
        System.out.println("You must specify the description, type of task, and time of the task");
        System.out.println("Each field has a particular format of entry\n");
        System.out.println("Type of task must be either meeting or assignment, wrapped in '#'\n"
                + "\te.g #meeting# # assignment#\n");
        System.out.println("Description must be wrapped in parentheses\n "
                + "\te.g (description)\n");
        System.out.println("Priority must be either high medium or low, wrapped in asterisks '*'\n "
                + "\te.g *low*\n");
        System.out.println("Time must be specified, wrapped in '&'\n"
                + "\te.g &22/12/2019 18:00&  &this friday 13:00&  &next monday 14:00&  &tmr 16:00&\n");
        System.out.println("If time isn't specified, then the duration of the task must at least be specified\n");
        System.out.println("Duration can be specified by wrapping in '^', in terms of number of hours or number of minutes");
        System.out.println("\te.g ^2 hours^ ^1 minutes^\n");
        System.out.println("Recurrence of the task can be specified by wrapping either days, weeks or months"
                + "\nin '%'\n\te.g %day% %week% %month%\n");
        System.out.println("Task can also be assigned to a name, by wrapping the name in '@'\n"
                + "\te.g @Alice@\n");
        System.out.println("You must specify the task type, description, and either time or duration");
        System.out.println("The rest of the fields can still be changed later using other commands");
    }

    void helpDelete() {
        System.out.println("Deletes the tasks in the index or the specified range");
        System.out.println("\te.g delete 1");
        System.out.println("\te.g delete 3 - 5");
    }

    void helperList() {
        System.out.println("Shows the list of task that are currently in the Task list");
        System.out.println("\teg. list");
    }

    void helpDone() {
        System.out.println("Marks the specified task as done/completed");
        System.out.println("\teg. done 1");
        System.out.println("\teg. done 2 - 4");
    }

    void helpRestore() {
        System.out.println("Restores a deleted task back into the task list based on its index");
        System.out.println("\teg. restore 2");
    }

    void helpFind() {
        System.out.println("Finds tasks in the task list based on keyword specified");
        System.out.println("\teg. find maths");
        System.out.println("\treturns all tasks that contains the 'maths' keyword");
    }

    void helpPriority() {
        System.out.println("Changes the priority of the specified task");
        System.out.println("\t3 levels of priority: 1 (High), 2 (Medium), 3 (Low)");
        System.out.println("\teg. priority 1");
        System.out.println("\tThis changes the priority of the task to high");
    }

    void helpSnooze() {
        System.out.println("Snoozes a task for a specified amount of time");
        System.out.println("Different time units include: hours, minutes");
        System.out.println("\teg. snooze 1 2 hours");
        System.out.println("\tThis snoozes task 1 for a period of 2 hours");
    }

    void helpReorder() {
        System.out.println("Reorder 2 different tasks in the task list");
        System.out.println("\teg. reorder 1 - 3");
        System.out.println("\tThis will swap the order task 1 and task 3");
    }


    void helpSubtask() {
        System.out.println("Adds subtasks to an assignment task type");
        System.out.println("\teg. subtask 3 subtask1, subtask2");
        System.out.println("\tThis will add 2 subtasks to the task at index 3, subtask1 and subtask2");
    }

    void helpUpdate() {
        System.out.println("Updates the task details");
        System.out.println("Fields that are updatable: ");
        System.out.println("\tDescription: (new_description)");
        System.out.println("\tDate Time: &20/09/2019 20:00&");
        System.out.println("\tPriority: *high*");
        System.out.println("\tDuration: ^3 hours^");
        System.out.println("\tRecurrence: %day%");
        System.out.println("\tAssignee: @joel@");
    }

    void helpSort() {
        System.out.println("Sorts the tasks in the task list based on, deadline, priority and alphabetical order");
        System.out.println("\teg. sort deadline");
        System.out.println("\tThis will sort the tasks in the task list by their deadlines");
    }

    void helpLog() {
        System.out.println("Logs the current task list into a saved file");
    }

    /**
     * Prints a message telling the user that the task at the index has been deleted.
     *
     * @param index Index of task to be deleted.
     */

    public void showDeleted(int[] index) {
        if (index.length == 1) {
            System.out.println("Deleted task number " + (index[0] + 1) + "!");
        }
        else {
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
        System.out.println("Listing tasks in your task list...");
    }

    /**
     * Tells the user that an invalid command has been input into RoomShare.
     */
    public void showCommandError() {
        System.out.println("Sorry, I don't understand this command...");
        System.out.println("Try type \"help add\" for instructions on how to add new task");
        System.out.println("\tType list, find, done, delete to perform operations on your todo list");
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

    /**
     * Prompt user to enter the second index
     */
    public void promptSecondIndex() {
        System.out.println("Please enter the index to swap to");
    }

    public void showReordering() {
        System.out.println("Reordering the task list...");
    }

    void helpList() {
        System.out.println("Here are a list of commands you can input: " +
                "\n add " + "\n delete " + "\n list " + "\n done "
                + "\n restore " + "\n find " + "\n priority "
                + "\n snooze " + "\n reorder " + "\n subtask "
                + "\n update " + "\n sort " + "\n bye "
                + "\n completed " + "\n overdue " + "\n reschedule "
                + "\n log \n"
                + "For more information about a specific command you can "
                + "\nEnter help followed by a command, eg. help add");
    }

    /**
     * Show the message of an error encountered.
     * @param e the encountered error
     */
    public void showError(Exception e) {
        System.out.println(e);
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

    void helpBye() {
        System.out.println("Typing in 'bye' will exit the program");
    }

    void helpCompleted() {
        System.out.println("Shows the list of completed tasks");
    }

    void helpOverdue() {
        System.out.println("Shows the list of overdue tasks");
    }

    void helpReschedule() {
        System.out.println("Reschedules an overdue task by index to a later date by inputting a new date");
        System.out.println("\teg. reschedule 1 &20/11/2019 10:00&");
        System.out.println("This will reschedule task 1 in the overdue task list to have a deadline of 20th Nov 2019 at 10am");
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
