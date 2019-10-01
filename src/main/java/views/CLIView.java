package views;

import controllers.ConsoleInputController;
import exceptions.DukeException;
import models.commands.DeleteCommand;
import models.commands.DoneCommand;
import models.commands.RescheduleCommand;
import models.tasks.ITask;
import models.tasks.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class CLIView {
    private final String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
    public static final String horiLine = "\t____________________________________________________________";

    private ConsoleInputController consoleInputController;

    public CLIView() {
        this.consoleInputController = new ConsoleInputController(this);
    }

    /**
     * Method to call when View model is started.
     */
    public void start() {
        Scanner sc = new Scanner(System.in);
        consoleInputController.readData();

        System.out.println(horiLine);
        System.out.println("\tHello! I'm Duke");
        System.out.println("\tWhat can I do for you?");
        System.out.println(horiLine);

        while (true) {
            String command = sc.nextLine();
            consoleInputController.onCommandReceived(command);
        }
    }

    /**
     * Method to be called when user says bye to exit the program.
     */
    public void end() {
        System.out.println("\t Bye. Hope to see you again soon!");
        System.exit(0);
    }

    /**
     * Method to be called when user wishes to add a new Task.
     * @param newTask : A new task that is added by the user. Task is created by Factory.
     * @param taskList : List of tasks holding all the tasks.
     * @param anomaly : Boolean value which gives status of anomaly detection.
     */
    public void addMessage(ITask newTask, TaskList taskList, boolean anomaly) {
        if (anomaly) {
            System.out.println(CLIView.horiLine);
            System.out.println("\tAnomalies with the schedule detected.");
            System.out.println(CLIView.horiLine);
            return;
        }
        System.out.println(horiLine);
        System.out.println("\tGot it. I've added this task:");
        System.out.print("\t  ");
        System.out.println("[" + newTask.getInitials() + "]"
                + "[" + newTask.getStatusIcon() + "] "
                + newTask.getDescription()
        );
        String grammerTasks = taskList.getNumOfTasks() > 1 ? "tasks" : "task";
        System.out.println("\tNow you have " + taskList.getNumOfTasks() + " " + grammerTasks + " in your list.");
        System.out.println(horiLine);
    }

    /**
     * Method to be called when user calls "list".
     * Prints to View all the current tasks added.
     * @param taskList : List of tasks.
     */
    public void printAllTasks(TaskList taskList) {
        System.out.println(horiLine);
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < taskList.getAllTasks().size(); i++) {
            System.out.print("\t" + (i + 1));
            System.out.println(".[" + taskList.getAllTasks().get(i).getInitials() + "]"
                    + "[" + taskList.getAllTasks().get(i).getStatusIcon() + "] "
                    + taskList.getAllTasks().get(i).getDescription()
            );
        }
        System.out.println(horiLine);
    }

    /**
     * Method to be called when user prompts for a task to be marked as done.
     * @param taskList : list of tasks.
     */
    public void markDone(TaskList taskList, DoneCommand doneCommand) {
        System.out.println(horiLine);
        doneCommand.execute(taskList);
        System.out.println(horiLine);
    }

    /**
     * Method to be called when a Invalid Command is input by the user.
     * @param newException : Exception that is thrown when an Invalid Command is detected
     */
    public void invalidCommandMessage(Exception newException) {
        System.out.println(horiLine);
        System.out.println("\t" + newException.getMessage());
        System.out.println(horiLine);
    }

    /**
     * Method that is called when user wishes to delete a task.
     * This method is responsible for handling printing of horizontal lines.
     * However, certain printing has been abstracted to DeleteCommand
     * @param taskList : List of tasks from which the chosen task should be deleted from.
     * @param deleteCommand : Command that holds the logic for searching and printing certain delete messages.
     */
    public void deleteTask(TaskList taskList, DeleteCommand deleteCommand) {
        System.out.println(horiLine);
        deleteCommand.execute(taskList);
        System.out.println(horiLine);
    }

    /**
     * Method that is called when user wishes to find a specific task.
     * This method updates the UI (in this case CLI) with relevant print messages and information.
     * @param taskList : Current list of tasks. Users will enter a keyword to search for a task residing in this list.
     * @param input : Full command that user has keyed into CLI.
     */
    public void findTask(TaskList taskList, String input) {
        System.out.println(horiLine);
        System.out.println("\tHere are the matching tasks in your list:");
        ArrayList<ITask> results = taskList.getSearchedTasks(input);
        for (int i = 0; i < results.size(); i++) {
            System.out.print("\t" + (i + 1));
            System.out.println(".[" + results.get(i).getInitials() + "]"
                    + "[" + results.get(i).getStatusIcon() + "] "
                    + results.get(i).getDescription()
            );
        }
        System.out.println(horiLine);
    }

    /**
     * Method that is called when user wants to find upcoming tasks within a time limit of their choice.
     * @param taskList Current list of tasks.
     * @param input User command including time limit before which to find upcoming tasks.
     *              If left blank, it will be seven days from current date by default.
     * @throws ParseException : Parsing error (If the date and time is not entered in dd/MM/yyyy HHmm)
     */
    public void remindTask(TaskList taskList, String input) throws ParseException {
        System.out.println(horiLine);
        System.out.println("\tHere are the upcoming tasks in your list:");
        String limit;
        Scanner sc = new Scanner(input);
        String dummy = sc.next();
        if (sc.hasNext()) {
            limit = sc.nextLine();
        } else {
            limit = "";
        }

        ArrayList<ITask> results = taskList.getUpcomingTasks(limit);
        for (int i = 0; i < results.size(); i++) {
            System.out.print("\t" + (i + 1));
            System.out.println(".[" + results.get(i).getInitials() + "]"
                + "[" + results.get(i).getStatusIcon() + "] "
                + results.get(i).getDescription()
            );
        }
        System.out.println(horiLine);
    }

    /**
     * Prints out the schedule for the date input by the user.
     * Format - schedule DD/MM/YYYY
     * @param taskList : Current list of tasks.
     * @param input : The date of the schedule
     * @throws ParseException : Parsing error
     */
    public void listSchedule(TaskList taskList, String input) throws ParseException {
        // Correct format as 2 December 2019 6 PM
        Scanner sc = new Scanner(input);
        String dummy = sc.next();
        String tempDate = sc.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(tempDate);
        String formattedDate = new SimpleDateFormat("d MMMM yyyy").format(date);

        System.out.println(horiLine);
        ArrayList<ITask> results = taskList.getSchedule(formattedDate);
        if (results.isEmpty()) {
            System.out.println("\tYour schedule for " + formattedDate + " is empty.");
        } else {
            System.out.println("\tHere is the schedule for the specified date:");
            for (int i = 0; i < results.size(); i++) {
                System.out.print("\t" + (i + 1));
                System.out.println(".[" + results.get(i).getInitials() + "]"
                        + "[" + results.get(i).getStatusIcon() + "] "
                        + results.get(i).getDescription()
                );
            }
        }
        System.out.println(horiLine);
    }

    /**
     * Prints tasks sorted by deadline/event time with free time slots in between.
     * @param taskList Current list of tasks.
     * @param input The full command by the user.
     * @throws ParseException parsing error if date and time are not in correct format.
     */
    public void findFreeSlots(TaskList taskList, String input) throws ParseException {
        System.out.println(horiLine);
        Scanner sc = new Scanner(input);
        String tempDate;
        if (sc.hasNext()) {
            tempDate = sc.nextLine();
        } else {
            tempDate = "";
        }

        ArrayList<String> freeTimeSlots = taskList.findFreeSlots(tempDate);
        System.out.println("Here are the free time slots you have between your tasks:\n");
        for (String freeTimeSlot : freeTimeSlots) {
            System.out.println(freeTimeSlot);
        }
    }

    /**
     * Method that is called when user wishes to reschedule a task.
     * This method is responsible for handling printing of horizontal lines.
     * However, certain printing has been abstracted to RescheduleCommand
     * @param taskList : Current list of tasks.
     * @param rescheduleCommand : Command that holds the logic for rescheduling tasks and
*                                 printing certain reschedule messages.
     */
    public void rescheduleTask(TaskList taskList, RescheduleCommand rescheduleCommand) {
        System.out.println(horiLine);
        rescheduleCommand.execute(taskList);
        System.out.println(horiLine);
    }

    /**
     * Method that helps print out console messages with the relevant line decorations.
     * @param message : Message that needs to be printed out to the user.
     */
    public void consolePrint(String message) {
        System.out.println(horiLine);
        System.out.println(message);
        System.out.println(horiLine);
    }
}
