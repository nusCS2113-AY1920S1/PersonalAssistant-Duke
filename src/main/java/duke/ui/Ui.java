package duke.ui;

import duke.models.locker.Locker;

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
        String logo = "SpongeBob";
        printSpaces(" Hello from " + logo);
        printDash();
        printSpaces(" Hello! I am SpongeBob. I am here to manage lockers for you!");
        printSpaces(" What can I do for you?");
        printDash();
    }

    /**
     * This function prints the tasks stored in the list.
     * @param printData stores the list of tasks to be printed.
     */
    public void printList(List<Locker> printData) {
        printSpaces(" Here are the lockers in your list:");
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
    public void printAddTask(List<Locker> listOfTasks, String taskA) {
        printSpaces(" Got it. I have added this task:");
        printSpaces("  " + taskA);
        showNumTasks(listOfTasks);
    }

    /**
     * This function tells the user that SpongeBob has added the locker into the list.
     * @param listOfLockers used for printing the number of lockers in the list
     * @param lockerA stores the locker that is added to the list
     */
    public void printAddLocker(List<Locker> listOfLockers, String lockerA) {
        printSpaces(" Got it. I have added this locker: ");
        printSpaces(" " + lockerA);
        printSpaces(" Now, Spongebob is managing " + listOfLockers.size() + " lockers");
        printDash();
    }

    /**
     * This function tells the user that SpongeBob has added a batch of lockers to the list.
     * @param num  stores the number of lockers that are added
     */
    public void printBatch(int num) {
        printSpaces(" Got it. I have added " + num + " lockers");
        printDash();
    }

    /**
     * This function is used to notify the user that SpongeBob has successfully assigned
     * the locker for the user.
     * @param locker stores the string to show the locker that has been assigned
     */
    public void printSuccessfulAllocation(String locker) {
        printSpaces(" I have successfully assigned a locker to the student. "
                + "Here are the details:");
        printSpaces(" " + locker);
        printDash();
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

    private void showNumTasks(List<Locker> listOfTasks) {
        printSpaces(" Now you have " + listOfTasks.size()
                + ((listOfTasks.size() == 1) ? " locker in the list." : " lockers in "
                + "the list"));
        printDash();
    }

    private void showList(List<Locker> listOfTasks) {
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
     *  This function is used to notify the user that there were no available lockers in his list
     *  of preferences.
     */
    public void showNoLockersFoundInPreferences() {
        printSpaces(" Unable to find any free lockers in the preferences stated.");
        printSpaces(" Looking for free lockers in the entire system ...");
        printDash();
    }

    /**
     * This function tells the user that SpongeBob has deleted the lockers from the list.
     * @param lockerList used for showing the number of lockers left in the list.
     * @param lockerA stores the locker that is deleted.
     */
    public void deleteMessage(List<Locker> lockerList, String lockerA) {
        printSpaces(" Noted. I have removed this locker:");
        printSpaces("  " + lockerA);
        showNumTasks(lockerList);
    }

    /**
     * This function tells the user that SpongeBob has edited the locker from the list.
     * @param lockerA stores the locker that is edited
     */
    public void editMessage(String lockerA) {
        printSpaces("Noted. I have edited the locker to:");
        printSpaces(" " + lockerA);
        printDash();
    }

    /**
     * This function prints the tasks that match a given keyword.
     * @param taskList stores the tasks that match a given keyword.
     */
    public void keywordPrint(List<Locker> taskList) {
        printSpaces(" Here are the matching tasks in your list:");
        showList(taskList);
    }

    public void getRemindersList(List<Locker> taskList) {
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