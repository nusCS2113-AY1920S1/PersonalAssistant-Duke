package duke.util;

import duke.modules.Task;

import java.util.List;
import java.util.Scanner;

public class Ui {

    /**
     * Contains the Scanner class, as Ui is where
     * the scanner object is initialised for user input to be read.
     * Constant String LINE such that line spacing is consistent.
     */
    private Scanner scan;
    private static final String LINE = "_______________________________\n";

    public Ui() {
        scan = new Scanner(System.in);
    }

    private void closeScan() {
        scan.close();
    }

    public String readCommand() {
        return scan.nextLine();
    }

    /**
     * Prints hello message to user.
     */
    public void helloMsg() {
        System.out.println(
                LINE
                + "Hello! I'm Duke\n"
                + "What can I do for you?\n"
                + LINE);
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void addedTaskMsg() {
        System.out.println("Got it. I've added this task:");
    }

    /**
     * Prints the current number of items in the task list.
     * @param size Number of items in the task list.
     */
    public void currentTaskListSizeMsg(int size) {
        System.out.println(
                "Now you have "
                + size
                + " tasks in the list."
        );
    }

    public void doneTaskMsg(Task task) {
        System.out.println("Nice! I've marked this task as done:\n" + task);
    }

    /**
     * Prints every item supplied in the taskList parameter.
     * @param taskList to be printed to user.
     */
    public void printTaskList(List<Task> taskList) {
        int count = 1;
        for (Task temp : taskList) {
            System.out.println(count + ". " + temp);
            count++;
        }
    }

    /**
     * Prints all tasks in upcomingTasksList.
     * @param upcomingTasksList contains all upcoming tasks.
     */
    public void printUpcomingTasks(List<Task> upcomingTasksList) {
        if (upcomingTasksList.size() > 0) {
            System.out.println(LINE + "You have " + upcomingTasksList.size() + " upcoming tasks!\nHere's the list:");
            this.printTaskList(upcomingTasksList);
            System.out.println(LINE);
        }
    }

    public void printTask(Task task) {
        System.out.println(task);
    }

    /**
     * Prints every item supplied in the taskList parameter.
     * taskList has been filtered to contain the keyword in the task name.
     * @param taskList to be printed to user.
     */
    public void findMsg(List<Task> taskList) {
        System.out.println("Here are the matching tasks in your list:");
        int count = 1;
        for (Task temp : taskList) {
            System.out.println(count + ". " + temp);
            count++;
        }
    }

    public void listMsg() {
        System.out.println("Here are the tasks in your list:");
    }

    public void goodbyeMsg() {
        System.out.println("Bye. Hope to see you again soon!");
        closeScan();
    }

    public void deleteMsg(Task task) {
        System.out.println("Noted. I've removed this task:\n" + task);
    }

    public void rescheduleTaskMsg(Task task, String time) {
        System.out.println("Got it! I've rescheduled this task to " + time + " :\n" + task);
    }

}
