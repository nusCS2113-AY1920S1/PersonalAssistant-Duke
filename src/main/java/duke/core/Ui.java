package duke.core;

import java.util.Scanner;

/**
 * Represents the necessary ui elements for user interaction.
 */
public class Ui {
    /**
     * A Scanner to read user input.
     */
    private Scanner scanner;

    /**
     * Constructs a singleton Ui design pattern by using lazy initialization.
     */

    private Ui() {
        scanner = new Scanner(System.in);
    }

    private static Ui ui;

    public static Ui getUi() {
        if (ui == null) {
            ui = new Ui();
        }
        return ui;
    }

    /**
     * Reads user instruction.
     *
     * @return A string that represents the user instruction.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String e) {
        System.out.println("☹" + e);
    }

    /**
     * Shows that a Task has been added, and displays the number
     * of current tasks in the list.
     *
     * @param t    The Task that is added to the list.
     * @param size The number of tasks stored in the TaskList.
     */
//    public void taskAdded(Task t, int size) {
//        System.out.println("Got it. I've added this task: \n  " + t.toString() + "\nNow you have "
//                + size + " tasks in the list.");
//    }


    /**
     * Shows that a Task has been marked as done.
     *
     * @param t The Task that is marked as done.
     */
//    public void markedAsDone(Task t) {
//        System.out.println("Nice! I've marked this task as done: \n  " + t.printStatus());
//    }

    /**
     * Shows that a Task has been removed, and displays the number
     * of current tasks in the list.
     *
     * @param t The Task that is deleted from the list.
     */
//    public void taskRemoved(Task t, int size) {
//        System.out.println("Noted. I've removed this task: \n  " + t.toString() + "\nNow you have "
//                + size + " tasks in the list.");
//    }

    /**
     * Shows that a Task has been rescheduled to a new date and time.
     *
     * @param t The Task that is rescheduled in the list.
     */
//    public void taskRescheduled(Task t) {
//        System.out.println("Noted. I've rescheduled this task: \n  " + t.toString());
//    }

    /**
     * Find and display a specific task stored in the list.
     *
     * @param a    TaskList used to store tasks.
     * @param name name of the task to be found
     */
//    public void taskFound(ArrayList<Task> a, String name) {
//        System.out.println("Here are the matching tasks in your list:");
//        int count = 1;
//        for (Task x : a) {
//            if (x.getDescription().contains(name)) {
//                System.out.println(count + "." + x.toString());
//                count++;
//            }
//        }
//    }

    /**
     * Find and display a specific task stored in the list.
     *
     * @param clashTasksInTheList  TaskList used to store tasks.
     * @param  newTask  the task to be added
     * @return the user's answer to whether he want to add or about the task
     */
//    public void showClashWarning (ArrayList<Task> clashTasksInTheList, Task newTask) {
//        System.out.println("Here are the tasks that fall on the same day:");
//        int count = 1;
//        for (Task t : clashTasksInTheList) {
//            System.out.println(count + ": " + t);
//            count++;
//        }
//        showLine();
//        System.out.println("Do you still want to add your task anyway? Y/N");
//    }



    /**
     * Print out the tasks in the task list which is reaching
     * the deadline.
     * @param a  TaskList used to store tasks.
     */
//    public void taskReminder(ArrayList<Task> a) {
//        System.out.println("The following tasks are reaching your deadline:");
//        System.out.println("Mark it as done or reschedule them to stop the reminder");
//        int count = 1;
//        for (Task x : a) {
//            System.out.println(count + "." + x.toString());
//            count++;
//        }
//    }

    /**
     * Print out the schedules on a specific date.
     * @param a TaskList used to store tasks.
     * @param date selected date of the schedule.
     */
//    public void showSchedules (ArrayList<Task> a , String date) {
//        System.out.println("This is your schedules on " + date);
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
//            int count = 1;
//            for (Task task : a) {
//                String currentTaskDate = task.getDateTime().format(formatter);
//                if (currentTaskDate.equals(date)) {
//                    System.out.println(count + ": " + task);
//                }
//                count++;
//            }
//        } catch (DateTimeParseException e) {
//            System.out.println("Invalid format. Please Enter Date and Time in the the format of dd/MM/yyyy");
//
//        }
//    }

//    public void makeRecurring(Task t) {
//        System.out.println("Okay. This task has been marked as recurring:\n"
//                            + t.toString());
//
//    }

    /**
     * Shows a divider line.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays all tasks currently stored in the list.
     *
     * @param tasks The TaskList used to store tasks.
     */
//    public void listTasks(TaskList tasks) throws DukeException {
//        System.out.println("Here are the tasks in your list:");
//        for (int i = 1; i <= tasks.getSize(); i++) {
//            System.out.println(i + "." + tasks.getTask(i).toString());
//        }
//    }

    /**
     * Shows bye message to user.
     */
    public void exitInformation() {
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
        System.out.println("Hello! I'm Duke\nWhat can I do for you?\n\n");
        System.out.println("Enter 'help' to show a list of commands ");
    }

    public void showHelpCommand() {
        System.out.println("Here are the things you can do with Dukepital:\n");
        System.out.println("1. list - to show a list of all the tasks\n");
        System.out.println("2. delete - to delete a task in the list\n");
        System.out.println("3. done - to set a task as completed\n");
        System.out.println("4. find - to find a task based on keywords from the list\n");
        System.out.println("5. todo - to add a todo task\n");
        System.out.println("6. fixeddurationtask - to add a task with fixed duration\n");
        System.out.println("7. deadline - to add a task with a deadline\n");
        System.out.println("8. event - to add a task with date ad time\n");
        System.out.println("9. period - to add a period task\n");
        System.out.println("10. reschedule  - to reschedule a task\n");
        System.out.println("11. view - to check a task of a specific date\n");
        System.out.println("12. recurring - to make a task recurring\n");
        System.out.println("13. bye - to exit Dukepital\n");
        System.out.println("If you have any further enquiries, please contact us directly.\n");
    }

    /**
     * Shows an error in loading the file where past tasks are stored.
     */
    public void showLoadingError() {
        System.out.println("Failed to Load from local text file!");
    }
}
