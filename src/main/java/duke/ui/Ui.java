package duke.ui;

import duke.enums.Numbers;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.ContactList;
import duke.task.FixedDuration;
import duke.task.TaskList;
import duke.task.PriorityList;

import javafx.util.Pair;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a ui that informs the user.
 */
public class Ui {

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

    //@@author gervaiseang
    /**
     * Show the help page.
     *
     * @return an event requesting to view the help page
     */
    public static String showHelpGui() {
        String str = "    Help window will pop up:\n    Shows all the commands available in Duke Manager";
        return str;
    }

    //@@author Dou-Maokang
    /**
     * Outputs all the tasks of the task list to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param priorities The list of priorities associated with each task.
     * @return String to be outputted to the user.
     */
    public String showTaskListWithPriorityGui(TaskList items, PriorityList priorities) {
        ArrayList<Pair> pair = PriorityList.sortPriority(items, priorities);
        String str = "";
        str += "     Here are the tasks in your list with priority shown:\n";
        str += "     Priority |\tTask\n";
        for (int i = Numbers.ZERO.value; i < items.size() || i < priorities.getSize(); i++) {
            str  += "        [" + pair.get(i).getKey() + "]\t  |\t" + pair.get(i).getValue() + "\n";
        }

        return str;
    }
    //@@author

    //@@author talesrune
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
     * Outputs notes of the task that is added or updated to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     * @return String of the added notes.
     */
    public String showAddNotesGui(TaskList items, int index) {
        String str = "     Nice! Added/Updated notes of this task ^^:\n"
                + "       " + (index + Numbers.ONE.value) + "." + items.get(index).toString()
                + "\n      | Added Notes: " + items.get(index).getNotes();
        return str;
    }

    /**
     * Outputs notes of the task that is added or updated to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     * @param deletedNotes The deleted notes of the task.
     * @return String of the deleted notes.
     */
    public String showDeleteNotesGui(TaskList items, int index, String deletedNotes) {
        String str = "     Deleted notes of this task ^^:\n"
                + "       " + (index + Numbers.ONE.value) + "." + items.get(index).toString()
                + "\n      | Deleted notes: " + deletedNotes;
        return str;
    }

    /**
     * Outputs notes of the task to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     * @return String of showing notes to the user.
     */
    public String showNotesGui(TaskList items, int index) {
        String str = "     Here is the task and its notes:\n"
                + "       " + (index + Numbers.ONE.value) + "." + items.get(index).toString()
                + "\n      | Notes: " + items.get(index).getNotes();
        return str;
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
                + "       " + (index + Numbers.ONE.value) + "." + items.get(index).toStringGui();
        return str;
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
     * Outputs task that is added to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @return String of the task that is added.
     */
    public static String showAddGui(TaskList items) {
        String str = "     Got it. I've added this task:\n       "
                + items.get(items.size() - Numbers.ONE.value).toStringGui() + "\n     Now you have "
                + items.size() + " tasks in the list.\n";
        return str;
    }

    /**
     * Outputs a welcome message to the user (GUI).
     *
     * @return String of the welcome message.
     */
    public static String showWelcomeGui() {
        String str = LINE + "\n     Hello! I'm Duke\n     What can I do for you?\n"
                + LINE + "\n    Upcoming Reminders in 3 days,\n     refer to Chat Box below:";
        return str;
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

    //@@author Dou-Maokang
    /**
     * Outputs the tasks with the target priority (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param priorities The list of priorities associated with the task list.
     * @param targetPriority The target priority to search.
     * @return String of tasks.
     */
    public String showFindTasksByPriorityGui(TaskList items, PriorityList priorities, int targetPriority) {
        String str = "     Here are the matching tasks in your list:\n";
        int numFound = Numbers.ZERO.value;
        for (int i = Numbers.ZERO.value; i < items.size(); i++) {
            if (priorities.getPriority(i + 1) == targetPriority) {
                str += "     " + (i + Numbers.ONE.value) + "." + items.get(i).toString() + "\n";
                numFound++;
            }
        }
        if (numFound == Numbers.ZERO.value) {
            str += "     No matching tasks found.\n";
        }
        return str;
    }
    //@@author

    //@@author Dou-Maokang
    /**
     * Outputs the tasks with the target date (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param targetDate The target date to search.
     * @return String of the tasks.
     */
    public String showFindTasksByDateGui(TaskList items, String targetDate) {
        String str = "     Here are the tasks on " + targetDate + " :\n";
        int numFound = Numbers.ZERO.value;
        for (int i = Numbers.ZERO.value; i < items.size(); i++) {
            if (items.get(i).toString().contains(targetDate)) {
                str += ("     " + (i + Numbers.ONE.value) + "." + items.get(i).toString()) + "\n";
                numFound++;
            }
        }
        if (numFound == Numbers.ZERO.value) {
            str += "     There're no tasks on " + targetDate + "." + "\n";
        }
        return str;
    }
    //@@author

    //@@author talesrune
    /**
     * Outputs the tasks that are matched from the keyword to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param keyword The keyword to match the tasks.
     * @return String of the matching tasks of the task list.
     */
    public static String showFindGui(TaskList items, String keyword) {
        String str = "     Here are the matching tasks in your list:\n";
        int numFound = Numbers.ZERO.value;

        for (int i = Numbers.ZERO.value; i < items.size(); i++) {
            if (items.get(i).getDescription().contains(keyword)) {
                str += "     " + (i + Numbers.ONE.value) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            }
        }
        if (numFound == Numbers.ZERO.value) {
            str += "     No matching tasks found.\n";
        }
        return str;
    }

    /**
     * Outputs the tasks that are filtered to the user. (GUI)
     *
     * @param items The task list that contains a list of tasks.
     * @param taskType The type of task.
     * @return String of the matching tasks of the task list.
     */
    public static String showFilterGui(TaskList items, String taskType) {
        String str = "     Here are the filtered tasks in your list:\n";
        int numFound = Numbers.ZERO.value;
        for (int i = Numbers.ZERO.value; i < items.size(); i++) {
            if (taskType.equals("todo") && items.get(i) instanceof Todo) {
                str += "     " + (i + Numbers.ONE.value) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            } else if (taskType.equals("deadline") && items.get(i) instanceof Deadline) {
                str += "     " + (i + Numbers.ONE.value) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            } else if (taskType.equals("fixedduration") && items.get(i) instanceof FixedDuration) {
                str += "     " + (i + Numbers.ONE.value) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            }
        }
        if (numFound == Numbers.ZERO.value) {
            str += "     No matching tasks found." + "\n";
        }
        if (!taskType.equals("todo") && !taskType.equals("deadline") && !taskType.equals("fixedduration")) {
            str = "     Invalid type of task.";
        }
        return str;
    }

    /**
     * Outputs the error when loading the file to the user.
     */
    public void showLoadingError() {
        out.println("File not found, creating a sample list");
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

    //@@author maxxyx96
    /**
     * Outputs a message to the user to let it know that it is updating.
     */
    public void showBackupMessage() {
        out.println("     Duke Manager has been backed up!\n"
                + "     Duke has opened the backup file location in file explorer!");
    }

    public static String showBackupMessageGui() {
        return "     Duke Manager has been backed up! \n"
                + "     Duke has opened the backup file location in file explorer!";
    }
    //@@author

    //@@author Dou-Maokang
    /**
     * Outputs a message to the user to let it know that it has changed the priority of a task.
     *
     * @param taskList The task list that contains a list of tasks.
     * @param taskNum The index of the task in the task list.
     * @param priority The index of the priority.
     * @return String of the text to show user.
     */
    public String showSetPriorityGui(TaskList taskList, int taskNum, int priority) {
        String str = "\tUpdated the priority of \n\t" + taskList.get(taskNum - Numbers.ONE.value) + "\n";
        str += "\tCurrent priority: " + priority;
        return str;
    }

    //@@author

    //@@author e0318465
    /**
     * Outputs all the contacts of the contact list to user through GUI.
     *
     * @param contactList The list of contacts.
     * @return String of all contacts from contact list.
     */
    public static String showContactListGui(ContactList contactList) {
        String str = "";
        str += "Here are all your contacts:\n";
        str += contactList.getFullContactList();
        return str;
    }

    /**
     * Outputs an alert when a duplicated inout is detected (GUI).
     *
     * @return String to be outputted to the user.
     */
    public String showDuplicateMsgGui() {
        return "     The same task is already in the list!";
    }

    /**
    * Show the added contacts in GUI.
    *
    * @param contactList The list of contacts.
    * @return String to output list of contacts to GUI.
     */
    public static String showAddedContactGui(ContactList contactList) {
        String str = "";
        if (contactList.size() == Numbers.ZERO.value) {
            str += "     You have no contacts!";
        } else {
            str += "\n     Got it, now you have " + contactList.size() + " contacts. Contact added:\n";
            str += contactList.get(contactList.size() - Numbers.ONE.value);
        }
        return str;
    }

    /**
     * Outputs contact that is deleted to the user through GUI.
     *
     * @param contactList The contact list that contains a list of contacts.
     * @param deletedContact The contact that is deleted.
     * @return String of deleted contact to user.
     */
    public static String showContactDeletedGui(ContactList contactList, String deletedContact) {
        String str = "";
        str += "Now you have " + contactList.size() + " contact(s). I've removed this contact:\n";
        str += deletedContact;
        return str;
    }

    /**
     * Outputs the tasks that are matched from the keyword to the user in GUI.
     *
     * @param contactList The contact list that contains a list of contacts.
     * @param keyword The keyword to match the contacts.
     * @return All contacts that matches with keyword.
     */
    public static String showFoundContactsGui(ContactList contactList, String keyword) {
        String str = "";
        str += "     Here are the matching contacts in your list:\n";
        int numFound = Numbers.ZERO.value;
        for (int i = Numbers.ZERO.value; i < contactList.size(); i++) {
            String details = contactList.getOnlyDetails(i);
            String replacedComma = details.replaceAll(",", " ");
            String stringToFind = replacedComma.toLowerCase();
            if (stringToFind.contains(keyword)) {
                str += "     " + contactList.getSpecificContactList(i) + "\n";
                numFound++;
            }
        }
        if (numFound == Numbers.ZERO.value) {
            str += "     No matching tasks found.";
        }
        return str;
    }

    //@@author maxxyx96
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
     * @return String of the reset message.
     */
    public String showResetBudgetGui(float budget) {
        return "     Your previous budget of " + budget + " has been reset.";
    }

    /**
     * Shows the remarks user input when adding the budget.
     *
     * @param remark the remarks user inputted while adding/subtracting budget.
     * @return String of the remark message.
     */
    public String showRemarkGui(String remark) {
        return "     Remarks entered: " + remark;
    }

    /**
     * Shows the list of budget currently in Duke Manager.
     * @param list List of the budget stored.
     *
     * @return the list of the budget.
     */
    public String showBudgetListGui(String list) {
        return list;
    }

    /**
     * Shows the user that the input limit has been exceeded.
     *
     * @return String of the message to be shown.
     */
    public String showBudgetExceededLimitMessageGui() {
        return "     The limits of budget has been exceeded! \n"
                + "     Budget limits: Between -$999,999 and $999,999."
                + "\n     No action has been done. ";
    }

    /**
     * Shows the user that the budget has been undone and update the user
     * his current budget.
     *
     * @param undoneBudget the budget after undone
     * @return String of the message to be shown
     */
    public String showUndoneBudgetGui(String undoneBudget) {
        return "     The previous entry has been undone, \n"
                + "     your new budget is $ " + undoneBudget;
    }

    /**
     * Shows the user that undo can't be done anymore on budget.
     *
     * @return String of the message to be shown
     */
    public String showBudgetUndoErrorGui() {
        return "     You have no existing budgets to undo!";
    }
    //@@author
}