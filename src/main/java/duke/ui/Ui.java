package duke.ui;

import duke.task.Task;
import duke.task.TaskList;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a ui that informs the user.
 */
public class Ui {

    protected static final String line = "    ____________________________________________________________";
    protected final Scanner in;
    protected final PrintStream out;

    /**
     * Creates an empty ui using default scanner and print stream.
     */
    public Ui() {
        this(System.in, System.out);
    }

    /**
     * Creates an ui using specified scanner and print stream.
     *
     * @param in The input stream.
     * @param out The print stream.
     */
    public Ui(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    /**
     * Outputs an horizontal line to the user.
     */
    public void showLine() {
        out.println(line);
    }

    /**
     * Outputs an horizontal line to the user (GUI).
     *
     * @return String of the horizontal line.
     */
    public static String showLineGui() {
        return line + "\n";
    }

    /**
     * Reads the user input, and converts it into string.
     *
     * @return String of the user input.
     */
    public String readCommand() {
        return in.nextLine();
    }

    /**
     * Outputs all the reminders of the user.
     *
     * @param tasks The task list that contains all reminders.
     */
    public static void showReminder(TaskList tasks) {
        ArrayList<Task> taskList = tasks.getTasks();
        System.out.println("     You currently have these upcoming tasks:\n");
        int currentIndex = 1;
        for (Task remaining: taskList) {
            remaining.isTriggerReminder();
            System.out.println("     " + currentIndex + "." + remaining.toString());
            currentIndex += 1;
        }
        System.out.println(line);
    }

    /**
     * Outputs all the tasks of the task list to the user.
     *
     * @param items The task list that contains a list of tasks.
     */
    public void showTaskList(TaskList items) {
        out.println("     Here are the tasks in your list:");
        out.print(items.getList());
    }

    /**
     * Outputs all the tasks of the task list to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @return String of all tasks from the task list.
     */
    public static String showTaskListGui(TaskList items) {
        String str = "     Here are the tasks in your list:\n" + items.getListGui();
        return str;
    }

    /**
     * Outputs task that is completed to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     */
    public void showDone(TaskList items, int index) {
        out.println("     Nice! I've marked this task as done:");
        out.println("       " + items.get(index).toString());
    }

    /**
     * Outputs task that is completed to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     * @return String of the task that is completed.
     */
    public static String showDoneGui(TaskList items, int index) {
        String str = "     Nice! I've marked this task as done:\n       " + items.get(index).toStringGui() + "\n";
        return str;
    }

    /**
     * Outputs task that is deleted to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param deletedTask The task that is deleted.
     */
    public void showDelete(TaskList items, String deletedTask) {
        out.println("     Noted. I've removed this task:");
        out.println(deletedTask);
        out.println("     Now you have " + items.size() + " tasks in the list.");
    }

    /**
     * Outputs task that is deleted to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param deletedTask The task that is deleted.
     * @return String of the task that is deleted.
     */
    public static String showDeleteGui(TaskList items, String deletedTask) {
        String str = "     Noted. I've removed this task:\n" + deletedTask
                + "\n     Now you have " + items.size() + " tasks in the list.\n";
        return str;
    }

    /**
     * Outputs task that is added to the user.
     *
     * @param items The task list that contains a list of tasks.
     */
    public void showAdd(TaskList items) {
        out.println("     Got it. I've added this task:");
        out.println("       " + items.get(items.size() - 1).toString());
        out.println("     Now you have " + items.size() + " tasks in the list.");
    }

    /**
     * Outputs task that is added to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @return String of the task that is added.
     */
    public static String showAddGui(TaskList items) {
        String str = "     Got it. I've added this task:\n       "
                + items.get(items.size() - 1).toStringGui() + "\n     Now you have "
                + items.size() + " tasks in the list.\n";
        return str;
    }

    /**
     * Outputs a welcome message to the user.
     */
    public void showWelcome() {
        showLine();
        out.println("     Hello! I'm Duke");
        out.println("     What can I do for you?");
        showLine();
    }

    /**
     * Outputs a welcome message to the user (GUI).
     *
     * @return String of the welcome message.
     */
    public static String showWelcomeGui() {
        String str = line + "\n     Hello! I'm Duke\n     What can I do for you?\n" + line;
        return str;
    }

    /**
     * Outputs a bye message to the user.
     */
    public void showBye() {
        out.println("     Bye. Hope to see you again soon!");
    }

    /**
     * Outputs a bye message to the user (GUI).
     *
     * @return String of the bye message.
     */
    public static String showByeGui() {
        String str = "     Bye. Hope to see you again soon!\n";
        return str;
    }

    /**
     * Outputs the tasks that are matched from the keyword to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param keyword The task that is deleted.
     */
    public void showFind(TaskList items, String keyword) {
        out.println("     Here are the matching tasks in your list:");
        int numFound = 0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getDescription().contains(keyword)) {
                out.println("     " + (i + 1) + "." + items.get(i).toString());
                numFound++;
            }
        }
        if (numFound == 0) {
            out.println("     No matching tasks found.");
        }
    }

    /**
     * Outputs the tasks that are matched from the keyword to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param keyword The task that is deleted.
     * @return String of the matching tasks of the task list.
     */
    public static String showFindGui(TaskList items, String keyword) {
        String str = "     Here are the matching tasks in your list:\n";
        int numFound = 0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getDescription().contains(keyword)) {
                str += "     " + (i + 1) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            }
        }
        if (numFound == 0) {
            str += "     No matching tasks found.\n";
        }
        return str;
    }

    /**
     * Outputs the error when loading the file to the user.
     */
    public void showLoadingError() {
        out.println("File not found, creating an empty list");
    }

    /**
     * Outputs the error message during operating actions to the user.
     *
     * @param message The error message to be outputted.
     */
    public void showErrorMsg(String message) {
        out.println(message);
    }

    /**
     * Outputs the error message during operating actions to the user (GUI).
     *
     * @param message The error message to be outputted.
     * @return String of the error message.
     */
    public static String showErrorMsgGui(String message) {
        return message + "\n";
    }

    /**
     * Outputs an alert when a duplicated inout is detected.
     */
    public void showDuplicateMsg(){
        out.println("     The same task is already in the list!");
    }
}
