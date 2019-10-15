package duke.ui;

import duke.task.PriorityList;
import duke.task.ContactList;
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

    protected static final String BACKUP_FILENAME = "duke.txt";
    protected static final String LINE = "    ____________________________________________________________";
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
        out.println(LINE);
    }

    /**
     * Show the help page.
     *
     * @param items The task list that contains a list of tasks.
     * @return an event requesting to view the help page
     */
    public static String helpRequest(TaskList items) {
        String str = "     Here are the commands available in Duke Manager:";
        return  str;
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
        System.out.println(LINE);
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
     * Outputs all the tasks of the task list to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param priorities The list of priorities associated with each task.
     */
    public void showTaskListWithPriority(TaskList items, PriorityList priorities) {
        out.println("     Here are the tasks in your list with priority shown:\n");
        out.printf("     Priority |\tTask\n");
        for (int i = 0; i < items.size() && i < priorities.getSize(); i++) {
            out.printf("        [%d]\t  |\t%d.%s\n", priorities.getList().get(i), i + 1, items.get(i));
        }
    }

    /**
     * Outputs all the contacts of the contact list to the user.
     *
     * @param contactList The list of contacts.
     */
    public void showContactList(ContactList contactList) {
        out.println("     Here are all your contacts:");
        out.print(contactList.getContactList());
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
     * Outputs task that is updated to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     */
    public void showUpdate(TaskList items, int index) {
        out.println("     Nice! I've updated this task ^^:");
        out.println("       " + (index + 1) + "." + items.get(index).toString());
    }

    /**
     * Outputs task that is updated to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     * @return String of the task that is updated.
     */
    public static String showUpdateGui(TaskList items, int index) {
        String str = "     Nice! I've updated this task ^^:\n"
                + "       " + (index + 1) + "." + items.get(index).toString();
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
        String str = LINE + "\n     Hello! I'm Duke\n     What can I do for you?\n" + LINE;
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
    public void showDuplicateMsg() {
        out.println("     The same task is already in the list!");
    }

    /**
     * Outputs a message to the user to let it know that it is updating.
     */
    public void showBeforeBackupMsg() {
        out.println("     Updating duke.txt...");
    }

    /**
     * Outputs a message to the user to let it know that it has finished updating,
     * and the file is shown in a folder.
     */
    public void showAfterBackupMsg() {
        out.println("     Updated " + BACKUP_FILENAME + " with the current items in Duke Manager!");
        out.println("     Directory of the file opened in explorer!");
    }

    /**
     * Outputs a message to the user to let it know that it has changed the priority of a task.
     *
     * @param taskList The task list that contains a list of tasks.
     * @param taskNum The index of the task in the task list.
     * @param priority The index of the priority.
     */
    public void showSetPriority(TaskList taskList, int taskNum, int priority) {
        out.println("     Updated the priority of \n\t\t" + taskList.get(taskNum));
        out.println("     Current priority: " + priority);
    }

    /**
     * Outputs the contact details that are most recently added.
     *
     * @param contactList The list of contacts.
     */
    public void showAddedContact(ContactList contactList) {
        out.println("     Got it. Contact added:");
        if (contactList.size() == 0) {
            out.println("     You have no contacts!");
        } else {
            out.println(contactList.get(contactList.size() - 1));
            out.println("     Now you have " + contactList.size() + " contacts.");
        }
    }

    public void showBudget(float amount) {
        out.println("     Your budget is : $" + amount);
    }

    public void rejectBudgetResetMessage() {
        out.println("     Budget reset cancelled.");
    }

    /**
     * Checks if the user is certain about resetting the budget by prompting the user to confirm his/her actions.
     *
     * @return returns true if user pressed Y, and false otherwise.
     */
    public boolean isBudgetResetTrue() {
        out.println("     You have an existing budget, are you sure you want to do this? Y/N");
        String choice = readCommand();
        if (choice.equals("Y") || choice.equals("y")) {
            return true;
        } else {
            return false;
        }
    }
}