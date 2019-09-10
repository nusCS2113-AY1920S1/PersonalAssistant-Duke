import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the necessary ui elements for user interaction
 */
public class Ui {
    /**
     * A <code>Scanner</code> to read user input.
     */
    private Scanner sc;
    /**
     * Constructs a <code>Ui</code> object and initializes the
     * <code>Scanner</code> to read user input from the system.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }
    /**
     * Reads user instruction.
     * @return A string that represents the user instruction.
     */
    public String readCommand() {
        return sc.nextLine();
    }

    public void showError(String e) {
        System.out.println( "☹  OOPS!!!" + e);
    }
    /**
     * Shows that a <code>Task</code> has been added, and displays the number
     * of current tasks in the list.
     * @param t The <code>Task</code> that is added to the list.
     * @param size The number of tasks stored in the <code>TaskList</code>.
     */
    public void taskAdded(Task t, int size) {
        System.out.println("Got it. I've added this task: \n  " + t.toString() + "\nNow you have "
                + size + " tasks in the list.");
    }
    /**
     * Shows that a <code>Task</code> has been marked as done.
     * @param t The <code>Task</code> that is marked as done.
     */
    public void markedAsDone(Task t) {
        System.out.println("Nice! I've marked this task as done: \n  " + t.printStatus());
    }
    /**
     * Shows that a <code>Task</code> has been removed, and displays the number
     * of current tasks in the list.
     * @param t The <code>Task</code> that is deleted from the list.
     */
    public void taskRemoved(Task t, int size) {
        System.out.println("Noted. I've removed this task: \n  " + t.toString() + "\nNow you have "
                + size + " tasks in the list.");
    }
    /**
     * Find and display a specific task stored in the list.
     * @param  a <code>TaskList</code> used to store tasks.
     * @param name name of the task to be found
     */
    public void taskFound(ArrayList<Task> a, String name){
        System.out.println("Here are the matching tasks in your list:");
        int count = 1;
        for (Task x : a) {
            if (x.getDescription().contains(name)) {
                System.out.println(count + "." + x.toString());
                count++;
            }
        }
    }
    /**
     * Shows a divider line.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }
    /**
     * Displays all tasks currently stored in the list.
     * @param tasks The <code>TaskList</code> used to store tasks.
     */
    public void listTasks(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 1; i <= tasks.getSize() ; i++)
        {
            System.out.println( i + "." + tasks.getTask(i).toString());
        }
    }
    /**
     * Shows bye message to user.
     */
    public void exitInformation(){
        System.out.println("Bye. Hope to see you again soon!");
    }
    /**
     * Shows Duke logo and welcome message, and user input instructions.
     */
    public void showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm Duke\nWhat can I do for you?");
    }
    /**
     * Shows an error in loading the file where past tasks are stored.
     */
    public void showLoadingError() {
        System.out.println("Failed to Load from local text file!");
    }
}
