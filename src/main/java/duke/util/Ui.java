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
     *
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
     *
     * @param taskList to be printed to user.
     */
    public <E extends Task> void printTaskList(List<E> taskList) {
        int count = 1;
        for (Task temp : taskList) {
            System.out.println(count + ". " + temp);
            count++;
        }
    }

    /**
     * Prints all tasks in upcomingTasksList.
     *
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
     *
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
  
    public void timetableMsg() {
        System.out.println("Here is your timetable:");
    }

    /**
     * Prints the timetable by days.
     * @param timetable to be printed to the users.
     */
    public void printTimetable(List<String> timetable) {
        int count = 1;
        for (String temp : timetable) {
            System.out.println(count + ". " + temp); //need to change count into weekdays
            count++;
        }
    }

    public void reportMsg() {
        System.out.println("Number of required tasks to complete:\n" + "30");
    }

    /**
     * Prints the number of tasks left to be completed.
     * Prints the number of each tasks that is completed.
     * @param taskList to be printed to user.
     */
    public void printReportList(List<Task> taskList) {
        int requiredTasks = 30;
        int tasksLeft = requiredTasks - taskList.size();
        System.out.println("Number of tasks still needed to complete:\n" + tasksLeft);
    }

    public void overallCapMsg() {
        System.out.println("Here is your overall CAP: ");
    }

    public void specificCapMsg(String module) {
        System.out.println("Here is your CAP for the module:" + module);
    }
}
