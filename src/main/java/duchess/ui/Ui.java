package duchess.ui;

import duchess.model.Module;
import duchess.model.task.Task;

import java.util.List;
import java.util.Scanner;

public class Ui {
    /**
     * Reference to Scanner.
     */
    private Scanner sc;

    /**
     * Instantiates Scanner class.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Prints an indented line.
     */
    public void beginBlock() {
        printHR();
    }

    /**
     * Prints an indented line.
     */
    public void endBlock() {
        printHR();
    }

    /**
     * Prints welcome message.
     */
    public void showWelcome() {
        beginBlock();
        printIndented("Hello! I'm Duke");
        printIndented("What can I do for you?");
        endBlock();
    }

    /**
     * Prints farewell message.
     */
    public void showBye() {
        printIndented("Bye. Hope to see you again soon!");
    }

    /**
     * Prints error to user.
     *
     * @param message Error message to be displayed
     */
    public void showError(String message) {
        printIndented(message);
    }

    /**
     * Displays the newly added module as well as other modules.
     *
     * @param module  newly added module
     * @param modules existing modules
     */
    public void showModuleAdded(Module module, List<Module> modules) {
        printIndented("I've added this module:");
        printIndented("  " + module);
        printIndented("Here are all your modules:");
        showModules(modules);
    }

    /**
     * Prints the task that was added.
     *
     * @param tasks List of all tasks
     * @param task  Added task
     */
    public void showTaskAdded(List<Task> tasks, Task task) {
        printIndented("Got it . I've added this task:");
        printIndented("  " + task);
        showNumTasks(tasks);
    }

    /**
     * Displays Task objects in list given.
     *
     * @param tasks List of Task objects
     */
    public void showTaskList(List<Task> tasks) {
        printIndented("Here are the tasks in your list:");
        showTasks(tasks);
    }

    /**
     * Displays the user's modules.
     *
     * @param modules list of modules
     */
    public void showModuleList(List<Module> modules) {
        if (modules.size() == 0) {
            printIndented("You've no modules.");
            printIndented("You can add modules using the `add module` command.");
            return;
        }

        printIndented("Here are your modules:");
        showModules(modules);
    }

    /**
     * Displays search results to user.
     *
     * @param tasks List containing tasks from user
     */
    public void showSearchResult(List<Task> tasks) {
        printIndented("Here are the matching tasks in your list:");
        showTasks(tasks);
    }

    /**
     * Displays schedule of a single day to user.
     * Informs user if there are ongoing events.
     * @param tasks List of tasks to show
     * @param date  Date
     */
    public void showScheduleResult(List<Task> tasks, String date, String academicContext) {
        printIndented(academicContext);
        printIndented("Here is your schedule for " + date + ":");
        int counter = 1;
        for (Task t : tasks) {
            printIndented(counter++ + ". " + t.toString());
        }
    }

    /**
     * Shows the task that was just snoozed.
     *
     * @param task The task that was just snoozed
     */
    public void showSnoozedTask(Task task) {
        printIndented("Noted. I've snoozed this task:");
        printIndented("  " + task);
    }

    /**
     * Shows the task that was just deleted.
     *
     * @param tasks List of all tasks
     * @param task  The task that was just deleted
     */
    public void showDeletedTask(List<Task> tasks, Task task) {
        printIndented("Noted. I've removed this task:");
        printIndented("  " + task);
        showNumTasks(tasks);
    }

    /**
     * Prints message for task completion.
     *
     * @param task Completed task
     */
    public void showDoneTask(Task task) {
        printIndented("Nice! I've marked this task as done:");
        printIndented("  " + task);
    }

    /**
     * Gets next line from user inputs.
     *
     * @return String containing user input
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Prints a string accompanied with indentation.
     *
     * @param line String containing description to be indented
     */
    private void printIndented(String line) {
        System.out.println("    " + line);
    }

    /**
     * Displays the tasks in a list.
     *
     * @param tasks List containing user tasks
     */
    private void showNumTasks(List<Task> tasks) {
        printIndented("Now you have "
                + tasks.size()
                + (tasks.size() > 1 ? " tasks" : " task")
                + " in the list.");
    }

    /**
     * Displays tasks in a list.
     *
     * @param tasks List containing user tasks
     */
    private void showTasks(List<Task> tasks) {
        int counter = 1;
        for (Task task : tasks) {
            printIndented(counter++ + ". " + task);
        }
    }

    /**
     * Displays modules in a list.
     *
     * @param modules list containing modules
     */
    private void showModules(List<Module> modules) {
        int counter = 1;
        for (Module module : modules) {
            printIndented(counter++ + ". " + module);
        }
    }

    /**
     * Shows no deadlines present.
     */
    public void showNoDeadlines() {
        printIndented("You have no pending deadlines.");
    }

    /**
     * Displays the list of deadlines present in user list.
     *
     * @param tasks List of all tasks
     */
    public void showDeadlines(List<Task> tasks) {
        printIndented("You currently have these deadlines:");
        showTasks(tasks);
    }

    /**
     * Displays user history.
     *
     * @param log list of user log
     */
    public void showUserHistory(List<String> log) {
        printIndented("These are the commands you entered:");
        int counter = 1;
        for (String userInput : log) {
            printIndented(counter++ + ". " + userInput);
        }
    }

    /**
     * Displays number of undone actions to user.
     *
     * @param undoCounter number of undo operations
     */
    public void showUndo(int undoCounter) {
        if (undoCounter == 1) {
            printIndented("The last command has been undone.");
        } else if (undoCounter == 0) {
            printIndented("There's nothing to undo.");
        } else {
            printIndented("The last few commands have been undone.");
        }
    }

    /**
<<<<<<< HEAD
     * Displays number of undone actions to user.
     *
     * @param redoCounter number of undo operations
     */
    public void showRedo(int redoCounter) {
        if (redoCounter == 1) {
            printIndented("I redid the last command.");
        } else {
            printIndented("I redid the last few commands.");
        }
    }

    /**
     * Displays an error message indicating why the module can't be deleted.
     *
     * <p>The function also prints the tasks that are associated with the module
     * being deleted.</p>
     *
     * @param tasks list of tasks belonging to the module being deleted
     */
    public void showUnableToDeleteModuleMsg(List<Task> tasks) {
        printIndented("You can't delete the module without deleting all of the associated tasks.");
        printIndented("The following tasks are associated with the module.");
        showTasks(tasks);
    }

    /**
     * Displays a confirmation message stating that the module has been deleted.
     *
     * @param module the module that was just deleted
     */
    public void showDeletedModule(Module module) {
        printIndented("You've deleted " + module + ".");
    }

    /**
     * Prints a straight line.
     */
    private void printHR() {
        printIndented("_______________________________________________________________");
    }
}