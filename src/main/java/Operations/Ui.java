package Operations;

/**
 * class to tell user about errors and completion of operations
 */
public class Ui {
    /**
     * Constructor for Ui class
     */
    public Ui () {   }

    /**
     * Shows the startup logo for RoomShare
     */
    public void startUp() {
        String logo = " __________         _\n"
                + "|          \\       / \\\n"
                + "|    ___    \\     /   \\\n"
                + "|   |___|    |   |  |  | \n"
                + "|          _/    |  |  | \n"
                + "|    ___    \\    \\  \\  /\n"
                + "|   |   \\    \\    \\  \\/\n"
                + "|   |    \\    \\   /\\  \\\n"
                + "|___|     \\____\\ /  \\  \\\n"
                + "                 |  |  |\n"
                + "                 |  |  |\n"
                + "                 \\     /\n"
                + "                  \\   /\n"
                + "                   \\_/";
        System.out.println("Hello from RoomShare!\n" + logo);
        System.out.println("How may I serve you?");
        System.out.println("Enter 'help' if you require assistance");
    }

    public void help() {
        System.out.println("Enter any of the commands below to see the detailed information on how to use them");
        System.out.println("Input 'exit' to leave help mode");
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

    public void showDeleted (int[] index) {
        if (index.length == 1)
            System.out.println("Deleted task number " + (index[0] + 1) + "!");
        else
            System.out.println("Deleted task number " + (index[0] + 1) + " to " + (index[1] + 1) + " !");
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
     * tells the user that RoomShare is listing the tasks
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
     * Tells the user that an invalid command has been input into RoomShare.
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
        System.out.println("\tE.g : todo buy groceries");
        System.out.println("\tE.g : todo buy groceries");
        System.out.println("\tE.g : deadline homework 22/12/2019 18:00");
        System.out.println("\tE.g : deadline homework 22/12/2019 18:00");
        System.out.println("\tE.g : event meeting 22/12/2019 18:00");
        System.out.println("\tE.g : event meeting 22/12/2019 18:00");
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

    /**
     * Tells the user to input valid amount of time
     */
    public void showTimeError(){
        System.out.println("Please indicate a valid amount of time");
    }
    /**
     * Provides user with instructions to prioritise task
     */
    public void priority() {
        System.out.println("Enter task index followed by priority (high, medium, low)");
        System.out.println("\te.g. 1 high");
    }

    /**
     * Notifies the user that their task's priority has been set
     */
    public void prioritySet() { System.out.println("Your task's priority has been set"); }

    public void promptSecondIndex() {
        System.out.println("Please enter the index to swap to");
    }

    public void showReordering() {
        System.out.println("Reordering the task list...");
    }


    public void promptForAssigning() {
        System.out.println("Do you want to assign this task? (if yes enter the assignee's name)");
    }

    public void explainBye() {
        System.out.println("Entering 'bye' will exit RoomShare and save all your data");
        System.out.println("\te.g : bye");
    }
    public void explainList() {
        System.out.println("Entering list will cause RoomShare to list out all the existing tasks in the task list");
        System.out.println("\te.g : list");
    }

    public void explainDelete() {
        System.out.println("Deletes the tasks in the specific indices from the task list");
        System.out.println("\te.g : delete 1");
        System.out.println("\te.g : 'delete 1 - 2'");
    }

    public void explainFind() {
        System.out.println("Searches the current task list for any tasks that contain the input keyword");
        System.out.println("\te.g : find homework");
        System.out.println("\te.g : find math quiz");
    }

    public void explainDone() {
        System.out.println("Sets the tasks in the specific indices as done");
        System.out.println("\te.g : done 1");
        System.out.println("\te.g : 'done 1 - 2'");
    }

    public void explainToDo() {
        System.out.println("Adds a ToDo task into RoomShare");
        System.out.println("Requires you to have a task description");
        System.out.println("\te.g : todo homework");
    }

    public void explainDeadline() {
        System.out.println("Adds a Deadline task into RoomShare");
        System.out.println("Requires you to have a task description and deadline");
        System.out.println("The description must be separated from the deadline by a '/'");
        System.out.println("The time format must be of dd/MM/yyyy HH:mm");
        System.out.println("\te.g : deadline math assignment/22/12/2019 18:00");
    }

    public void explainEvent() {
        System.out.println("Adds an Event task into RoomShare");
        System.out.println("Requires you to have a task description and happening time");
        System.out.println("The description must be separated from the time by a '/'");
        System.out.println("The time format must be of dd/MM/yyyy HH:mm");
        System.out.println("\te.g : event market sale/22/12/2019 18:00");
        System.out.println("Will also ask if event has fixed duration to start creation of fixed duration tasks");
    }

    public void explainRecur() {
        System.out.println("brings RoomShare into the Recurring tasks mode where you can add recurring tasks");
        System.out.println("\te.g : recur");
    }

    public void explainSnooze() {
        System.out.println("Snoozes the task specified for a set amount of time");
        System.out.println("\te.g : snooze 1");
    }

    public void explainPriority() {
        System.out.println("Allows you to set the priority of the task");
        System.out.println("Input 'priority' to start the process of setting priority");
    }

    public void explainReorder() {
        System.out.println("Swaps the tasks with a specified index");
        System.out.println("\te.g : reorder 1");
        System.out.println("RoomShare will prompt you to input a new index to swap the task to");
    }

    public void reportTasks() {
        System.out.println("Here's your report for the task list:");
    }

    public void showListError() {
        System.out.println("Seems like your task list is empty... Try adding a task to your list before listing!");
    }

    public void reportFinished() {
        System.out.println("\nHere's all the tasks that you have done");
    }

    public void reportDonePercentage(double percentage) {
        System.out.println("You've finished " + percentage + " of your tasks");
        if (percentage < 25.0) {
            System.out.println("Keep working at it!");
        } else if (percentage < 50.0) {
            System.out.println("On track to the halfway mark!");
        } else if (percentage == 50.0) {
            System.out.println("Halfway there!");
        } else if (percentage < 75.0) {
            System.out.println("Good progress!");
        } else if (percentage < 100.0) {
            System.out.println("That's a lot of work done!");
        } else if (percentage >= 100) {
            System.out.println("Congratulations! All done!");
        }
    }

    public void reportUnfinished() {
        System.out.println("\nHere's all the tasks that you have NOT done");
    }

    public void reportNotDonePercentage(double percentage) {
        System.out.println("You haven't finished " + percentage + " of your tasks...");
        if (percentage < 25.0) {
            System.out.println("Keep up the good work");
        } else if (percentage < 50.0) {
            System.out.println("Good progress!");
        } else if (percentage == 50.0) {
            System.out.println("Halfway there!");
        } else if (percentage < 75.0) {
            System.out.println("Good try, let's hit the halfway mark");
        } else if (percentage < 100.0) {
            System.out.println("Isn't it time to start work soon?");
        } else if (percentage >= 100) {
            System.out.println("Better to start work than never....");
        }
    }

    public void reportUpcoming() {
        System.out.println("\nThese are your upcoming tasks: ");
    }

    public void reportUpcomingCount(int upcoming) {
        System.out.println("You have " + upcoming + " tasks that are upcoming");
    }

    public void reportRecurring() {
        System.out.println("\nThese are the recurring tasks in your list");
    }

    public void reportRecurringCount(int recurring) {
        System.out.println("You have " + recurring + " recurring tasks");
    }
}
