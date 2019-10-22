package duke.ui;

import duke.task.Todo;
import duke.task.Deadline;
import duke.task.ContactList;
import duke.task.FixedDuration;
import duke.task.DoAfter;
import duke.task.Repeat;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.PriorityList;
import duke.task.Event;

import javafx.util.Pair;

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
    private static final int ZERO = 0;
    private static final int ONE = 1;

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
        int currentIndex = ONE;
        for (Task remaining: taskList) {
            remaining.isTriggerReminder();
            System.out.println("     " + currentIndex + "." + remaining.toString());
            currentIndex += ONE;
        }
        System.out.println(LINE);
    }

    /**
     * Outputs task that is successfully sets a reminder to the user (GUI).
     *
     * @param num indicated number of days to set the reminder
     * @param items The task list that contains a list of tasks.
     * @return String of the task that is completed.
     */
    public static String showReminderGui(Task num, TaskList items) {
        String str = ("     You will get a reminder for this task in "
                + num.getReminder()
                + " days\n" + items.toString());
        return str;
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


    //@@author Dou-Maokang
    /**
     * Outputs all the tasks of the task list to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param priorities The list of priorities associated with each task.
     */
    public void showTaskListWithPriority(TaskList items, PriorityList priorities) {
        ArrayList<Pair> pair = PriorityList.sortPriority(items, priorities);
        out.println("     Here are the tasks in your list with priority shown:\n");
        out.printf("     Priority |\tTask\n");
        for (int i = ZERO; i < items.size() || i < priorities.getSize(); i++) {
            out.printf("        [%d]\t  |\t%s\n", pair.get(i).getKey(), pair.get(i).getValue());
        }
    }
    //@@author


    /**
     * Outputs all the contacts of the contact list to user through CLI.
     *
     * @param contactList The list of contacts.
     */
    public void showContactList(ContactList contactList) {
        out.println("     Here are all your contacts:");
        out.print(contactList.getContactList());
    }

    /**
     * Outputs all the contacts of the contact list to user through GUI.
     *
     * @param contactList The list of contacts.
     * @return String of all contacts from contact list.
     */
    public static String showContactListGui(ContactList contactList) {
        String str = "";
        str += "Here are all your contacts:\n";
        str += contactList.getContactList();
        return str;
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
        out.println("       " + (index + ONE) + "." + items.get(index).toString());
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
                + "       " + (index + ONE) + "." + items.get(index).toStringGui();
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
        out.println("       " + items.get(items.size() - ONE).toString());
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
                + items.get(items.size() - ONE).toStringGui() + "\n     Now you have "
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
        int numFound = ZERO;
        for (int i = ZERO; i < items.size(); i++) {
            if (items.get(i).getDescription().contains(keyword)) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            }
        }
        if (numFound == ZERO) {
            out.println("     No matching tasks found.");
        }
    }


    //@@author Dou-Maokang
    /**
     * Outputs the tasks with the target priority.
     *
     * @param items The task list that contains a list of tasks.
     * @param priorities The list of priorities associated with the task list.
     * @param targetPriority The target priority to search.
     */
    public void showFindTasksByPriority(TaskList items, PriorityList priorities, int targetPriority) {
        out.println("     Here are the matching tasks in your list:");
        int numFound = ZERO;
        for (int i = ZERO; i < items.size(); i++) {
            if (priorities.getPriority(i + 1) == targetPriority) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            }
        }
        if (numFound == ZERO) {
            out.println("     No matching tasks found.");
        }
    }
    //@@author


    /**
     * Outputs the tasks that are matched from the keyword to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param keyword The task that is deleted.
     * @return String of the matching tasks of the task list.
     */
    public static String showFindGui(TaskList items, String keyword) {
        String str = "     Here are the matching tasks in your list:\n";
        int numFound = ZERO;
        for (int i = ZERO; i < items.size(); i++) {
            if (items.get(i).getDescription().contains(keyword)) {
                str += "     " + (i + ONE) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            }
        }
        if (numFound == ZERO) {
            str += "     No matching tasks found.\n";
        }
        return str;
    }

    /**
     * Outputs the tasks that are filtered to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param taskType The task that is deleted.
     */
    public void showFilter(TaskList items, String taskType) {
        out.println("     Here are the filtered tasks in your list:");
        int numFound = ZERO;
        for (int i = ZERO; i < items.size(); i++) {
            if (taskType.equals("todo") && items.get(i) instanceof Todo) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            } else if (taskType.equals("deadline") && items.get(i) instanceof Deadline) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            } else if (taskType.equals("event") && items.get(i) instanceof Event) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            } else if (taskType.equals("repeat") && items.get(i) instanceof Repeat) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            } else if (taskType.equals("doafter") && items.get(i) instanceof DoAfter) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            } else if (taskType.equals("fixedduration") && items.get(i) instanceof FixedDuration) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            }
        }
        if (numFound == ZERO) {
            out.println("     No matching tasks found.");
        }
    }

    /**
     * Outputs the tasks that are filtered to the user. (GUI)
     *
     * @param items The task list that contains a list of tasks.
     * @param taskType The task that is deleted.
     * @return String of the matching tasks of the task list.
     */
    public static String showFilterGui(TaskList items, String taskType) {
        String str = "     Here are the filtered tasks in your list:\n";
        int numFound = ZERO;
        for (int i = ZERO; i < items.size(); i++) {
            if (taskType.equals("todo") && items.get(i) instanceof Todo) {
                str += "     " + (i + ONE) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            } else if (taskType.equals("deadline") && items.get(i) instanceof Deadline) {
                str += "     " + (i + ONE) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            } else if (taskType.equals("event") && items.get(i) instanceof Event) {
                str += "     " + (i + ONE) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            } else if (taskType.equals("repeat") && items.get(i) instanceof Repeat) {
                str += "     " + (i + ONE) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            } else if (taskType.equals("doafter") && items.get(i) instanceof DoAfter) {
                str += "     " + (i + ONE) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            } else if (taskType.equals("fixedduration") && items.get(i) instanceof FixedDuration) {
                str += "     " + (i + ONE) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            }
        }
        if (numFound == ZERO) {
            str += "     No matching tasks found." + "\n";
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
    //@@author e0318465
    public void showDuplicateMsg() {
        out.println("     The same task is already in the list!");
    }
    //@@author

    //@@author maxxyx96
    /**
     * Outputs a message to the user to let it know that it is updating.
     */
    public void showBackupMessage() {
        out.println("     Duke Manager has been backed up!");
    }

    public void showBackupFolderMessage() {
        out.println("     Duke has opened the backup file location in file explorer!");
    }

    public String showBackupMessageGui() {
        return "     Duke Manager has been backed up!";
    }
    //@@author


    //@@author Dou-Maokang
    /**
     * Outputs a message to the user to let it know that it has changed the priority of a task.
     *
     * @param taskList The task list that contains a list of tasks.
     * @param taskNum The index of the task in the task list.
     * @param priority The index of the priority.
     */
    public void showSetPriority(TaskList taskList, int taskNum, int priority) {
        out.println("     Updated the priority of \n\t\t" + taskList.get(taskNum - ONE));
        out.println("     Current priority: " + priority);
    }
    //@@author

    /**
     * Outputs the contact details that are most recently added.
     *
     * @param contactList The list of contacts.
     */
    //@@author e0318465
    public void showAddedContact(ContactList contactList) {
        out.println("     Got it. Contact added:");
        if (contactList.size() == ZERO) {
            out.println("     You have no contacts!");
        } else {
            out.println(contactList.get(contactList.size() - ONE));
            out.println("     Now you have " + contactList.size() + " contacts.");
        }
    }

    /**
    * Show the added contacts in GUI.
    *
    * @param contactList The list of contacts.
    * @return String to output list of contacts to GUI.
     */
    public static String showAddedContactGui(ContactList contactList) {
        String str = "Got it. Contact added:\n";
        if (contactList.size() == ZERO) {
            str += "You have no contacts!";
        } else {
            str += contactList.get(contactList.size() - ONE);
            str += "\nNow you have " + contactList.size() + " contacts.";
        }
        return str;
    }

    /**
     * Outputs contact that is deleted, to the user through CLI.
     *
     * @param contactList The contact list that contains a list of contacts.
     * @param deletedContact The contact that is deleted.
     */
    public void showContactDeleted(ContactList contactList, String deletedContact) {
        out.println("Noted. I've removed this contact:");
        out.println(deletedContact);
        out.println("Now you have " + contactList.size() + " contact(s) in the list.");
    }

    /**
     * Outputs contact that is deleted to the user through GUI.
     *
     * @param contactList The contact list that contains a list of contacts.
     * @param deletedContact The contact that is deleted.
     * @return String of deleted contact to user.
     */
    public static String showContactDeletedGui(ContactList contactList, String deletedContact) {
        String str = "     Noted. I've removed this contact:\n";
        str += deletedContact;
        str += "\n     Now you have " + contactList.size() + " contact(s) in the list.";
        return str;
    }
    //@@author

    //@@author maxxyx96
    /**
     * Shows the current budget of the user.
     *
     * @param amount the budget the user currently has.
     */
    public void showBudget(float amount) {
        out.println("     Your budget is : $" + amount);
    }

    /**
     * Shows the current budget of the user.
     *
     * @param amount the budget the user currently has.
     * @return the message to be shown to the user.
     */
    public String showBudgetGui(float amount) {
        return "     Your current Budget is : $" + amount;
    }

    /**
     * Shows the user the amount that is added/subtracted to the existing budget.
     *
     * @param amount The amount that is to be added/subtracted.
     * @param budget The existing budget of the user.
     */
    public void showAddBudget(float amount, float budget) {
        if (amount > 0) {
            out.println("     You are adding $" + amount + " to your current budget of $" + budget);
        } else {
            out.println("     You are subtracting $" + -amount + " from your current budget of $" + budget);
        }
    }

    /**
     * Shows the user the amount that is added/subtracted to the existing budget. (GUI)
     *
     * @param amount The amount that is to be added/subtracted.
     * @param budget The existing budget of the user.
     * @return String of the text to show user.
     */
    public String showAddBudgetGui(float amount, float budget) {
        if (amount > 0) {
            return "     You are adding $" + amount + " to your current budget of $" + budget;
        } else {
            return "     You are subtracting $" + -amount + " from your current budget of $" + budget;
        }
    }

    /**
     * Shows the budget that the user has before the changes.
     *
     * @param budget The budget of the user.
     */
    public void showResetBudget(float budget) {
        out.println("     Your previous budget of $" + budget + " has been reset.");
    }

    /**
     * Shows the budget that the user has before the changes.
     *
     * @param budget The budget of the user.
     * @return String of the reset message.
     */
    public String showResetBudgetGui(float budget) {
        return "     Your previous budget of " + budget + " has been reset.";
    }
    //@@author
}