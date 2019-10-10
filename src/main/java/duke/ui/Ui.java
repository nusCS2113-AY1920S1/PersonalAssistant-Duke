package duke.ui;

import duke.tasks.Task;

import java.util.Scanner;
import java.util.List;

public class Ui {
    private Scanner sc = new Scanner(System.in);

    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * This function responsible for printing a line.
     */
    public void printDash() {
        String str = "";
        for (int i = 0; i < 75; i++) {
            str += "_";
        }
        printSpaces(str);
    }

    private void printSpaces(String printStr) {
        System.out.println("    " + printStr);
    }

    /**
     * This function prints the hello message every time Duke is initiated.
     */
    public void showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        printSpaces(" Hello from\n" + logo);
        printDash();
        printSpaces(" Hello! I am Duke");
        printSpaces(" What can I do for you?");
        printDash();
    }

    /**
     * This function prints the tasks stored in the list.
     * @param printData stores the list of tasks to be printed.
     */
    public void printList(List<Task> printData) {
        printSpaces(" Here are the tasks in your list:");
        showList(printData);
    }

    /**
     * This function tells the user that Duke has marked the task as done.
     * @param printStr stores the task that has been marked as done.
     */
    public void printMarkAsDone(String printStr) {
        printSpaces(" Nice! I have marked this task as done:");
        printSpaces(" " + printStr);
        printDash();
    }

    /**
     * This function tells the user that Duke has added the task to the list.
     * @param listOfTasks used for printing the number of tasks in the list.
     * @param taskA stores the task that is to be added to the list.
     */
    public void printAddTask(List<Task> listOfTasks, String taskA) {
        printSpaces(" Got it. I have added this task:");
        printSpaces("  " + taskA);
        showNumTasks(listOfTasks);
    }

    /**
     * This function tells the user that Duke has snoozed/postponed/rescheduled the task.
     * @param taskA stores the task that is to be added to the list.
     * @param type stores whether that task is an event or a deadline
     * @param command stores whether user command is snooze or postpone or reschedule
     */
    public void printSnoozeTask(String taskA, String type, String command) {
        switch (type) {
        case "E":
            printSpaces(" I have successfully " + command + "d" + " the data and time of this event:");
            printSpaces("  " + taskA);
            printDash();
            break;
        case "D":
            printSpaces(" I have successfully " + command + "d" + " the data and time of this deadline:");
            printSpaces("  " + taskA);
            printDash();
            break;
        default:
            printSpaces(" Invalid command");
        }

    }

    private void showNumTasks(List<Task> listOfTasks) {
        printSpaces(" Now you have " + listOfTasks.size()
                + ((listOfTasks.size() == 1) ? " task in the list." : " tasks in "
                + "the list"));
        printDash();
    }

    private void showList(List<Task> listOfTasks) {
        for (int i = 0; i < listOfTasks.size(); i++) {
            printSpaces(" " + (i + 1) + ". " + listOfTasks.get(i).toString());
        }
        printDash();
    }

    /**
     * This function prints the exit message every time Duke is closed.
     */
    public void exitDuke() {
        printSpaces(" Bye.Hope to see you again soon.");
        printDash();
    }

    /**
     * This function prints the error message.
     * @param errorMessage stores the error message.
     */
    public void showError(String errorMessage) {
        printSpaces(errorMessage);
        printDash();
    }

    /**
     * This function tells the user that Duke has deleted the task from the list.
     * @param taskList used for showing the number of tasks left in the list.
     * @param taskA stores the task that is deleted.
     */
    public void deleteMessage(List<Task> taskList, String taskA) {
        printSpaces(" Noted. I have removed this task:");
        printSpaces("  " + taskA);
        showNumTasks(taskList);
    }

    /**
     * This function prints the tasks that match a given keyword.
     * @param taskList stores the tasks that match a given keyword.
     */
    public void keywordPrint(List<Task> taskList) {
        printSpaces(" Here are the matching tasks in your list:");
        showList(taskList);
    }

    public void getRemindersList(List<Task> taskList) {
        printSpaces(" Here are your deadlines:");
        showList(taskList);
    }


    /**
     * This function prints the message when there is an error in loading data from the file.
     * @param message stores the error message.
     */
    public void showLoadingError(String message) {
        printDash();
        printSpaces(message);
        printDash();
    }
}