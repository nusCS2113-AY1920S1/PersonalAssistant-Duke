package planner.util;

import planner.modules.Task;

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

    /**
     * Prints the information for the main report message.
     */
    public void reportListMsg() {
        System.out.println(
                "Hello there! Welcome to the report page."
                        + "Please enter 'Report <NUMBER>' to view the specific reports\n"
                        + "1) Overall module credit report\n"
                        + "2) SU-ed modules report\n"
                        + "3) Core modules report\n"
                        + "4) Unrestricted elective modules report\n"
                        + "5) General education modules report\n"
        );
    }

    /**
     * Prints the information for the first report.
     *
     * @param taskList to be printed to user.
     */
    public void printReportOne(List<Task> taskList) {
        int requiredMCs = 160;
        int mcsLeft = requiredMCs - taskList.size();
        int numOfSem = 1; // Update this
        System.out.println(
                "1) Overall module credit report\n"
                        + "Required amount of MCs: " + requiredMCs + "\n"
                        + "Amount of MCs taken: " +  taskList.size() + "\n"
                        + "Amount of MCs left to fulfil: " +  mcsLeft + "\n"
                        + "Projected amount of MCs to take per semester: " + mcsLeft / numOfSem + "\n"
        );
    }

    /**
     * Prints the information for the second report.
     *
     * @param taskList to be printed to user.
     */
    public void printReportTwo(List<Task> taskList) {
        int availableSUs = 0;
        /*if (sem[sem.length - 1] == 1 || sem[sem.length -1] == 2) {
            availableSUs = 20;
        } else {
            availableSUs = 12;
        }*/
        int suLeft = availableSUs - taskList.size();
        int numOfSem = 1; // Update this
        System.out.println(
                "2) SU-ed modules report\n"
                        + "Amount of SUs used: " +  taskList.size() + "\n"
                        + "Amount of SUs available: " +  suLeft + "\n"
                        + "Projected amount of SUs available per semester: " + suLeft / numOfSem + "\n"
        );
    }

    /**
     * Prints the information for the third report.
     *
     * @param taskList to be printed to user.
     */
    public void printReportThree(List<Task> taskList) {
        int requiredCoreMod = 108;
        int coreModLeft = requiredCoreMod - taskList.size();
        int numOfSem = 1; // Update this
        System.out.println(
                "3) Core modules report\n"
                        + "Required amount of core modules: " + requiredCoreMod + "\n"
                        + "Amount of core modules taken: " +  taskList.size() + "\n"
                        + "Amount of core modules left to take: " +  coreModLeft + "\n"
                        + "Projected amount of core modules to take per semester: " + coreModLeft / numOfSem + "\n"
        );
    }

    /**
     * Prints the information for the fourth report.
     *
     * @param taskList to be printed to user.
     */
    public void printReportFour(List<Task> taskList) {
        int requiredUE = 20;
        int ueLeft = requiredUE - taskList.size();
        int numOfSem = 1; // Update this
        System.out.println(
                "4) Unrestricted elective modules report\n"
                        + "Required amount of UE modules: " + requiredUE + "\n"
                        + "Amount of UE modules taken: " +  taskList.size() + "\n"
                        + "Amount of UE modules left to take: " +  ueLeft + "\n"
                        + "Projected amount of UE modules to take per semester: " + ueLeft / numOfSem + "\n"
        );
    }

    /**
     * Prints the information for the fifth report.
     *
     * @param taskList to be printed to user.
     */
    public void printReportFive(List<Task> taskList) {
        int requiredGE = 32;
        int geLeft = requiredGE - taskList.size();
        int numOfSem = 1; // Update this
        System.out.println(
                "4) Unrestricted elective modules report\n"
                        + "Required amount of UE modules: " + requiredGE + "\n"
                        + "Amount of UE modules taken: " +  taskList.size() + "\n"
                        + "Amount of UE modules left to take: " +  geLeft + "\n"
                        + "Projected amount of UE modules to take per semester: " + geLeft / numOfSem + "\n"
        );
    }


    /**
     * Prints the number of each tasks that is completed.
     *
     * @param taskList to be printed to user.
     */
    public void printReportTask(List<Task> taskList, String taskName) {
        int numOfTasks = taskList.size();
        System.out.println("Total number of " + taskName + " tasks completed:\n" + numOfTasks);
    }

    public void overallCapMsg() {
        System.out.println("Here is your overall CAP: ");
    }

    public void specificCapMsg(String module) {
        System.out.println("Here is your CAP for the module:" + module);
    }
}
