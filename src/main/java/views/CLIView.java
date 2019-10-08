package views;

import controllers.ConsoleInputController;
import models.task.Task;
import models.temp.commands.RescheduleCommand;
import models.data.IProject;
import models.member.Member;
import models.temp.tasks.ITask;
import models.temp.tasks.TaskList;
import models.temp.tasks.Tentative;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class CLIView {
    private static final String horiLine = "\t____________________________________________________________";
    private static final String indentation = "\t";

    private ConsoleInputController consoleInputController;

    public CLIView() {
        this.consoleInputController = new ConsoleInputController(this);
    }

    /**
     * Prints an indented and formatted message with a top and bottom border.
     * @param lines The lines to be printed in between the border.
     */
    public void consolePrint(String... lines) {
        System.out.println(horiLine);
        for (String message : lines) {
            System.out.println(indentation + message);
        }
        System.out.println(horiLine);
    }

    /**
     * Method to call when View model is started.
     */
    public void start() {
        Scanner sc = new Scanner(System.in);
        consoleInputController.readData();

        consolePrint("Hello! I'm Duke", "What can I do for you?");

        while (true) {
            String command = sc.nextLine();
            consoleInputController.onCommandReceived(command);
        }
    }

    /**
     * Method to be called when user says bye to exit the program.
     */
    public void end() {
        consolePrint("Bye. Hope to see you again soon!");
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
            consolePrint("Anomalies with the schedule detected.");
            return;
        }
        String grammarTasks = taskList.getNumOfTasks() == 1 ? "task" : "tasks";
        consolePrint("Got it. I've added this task:", newTask.getFullDescription(),
            "Now you have " + taskList.getNumOfTasks() + " " + grammarTasks + " in your list.");
    }

    /**
     * Method to be called when user calls "list".
     * Prints to View all the current tasks added.
     * @param taskList : List of tasks.
     */
    public void printAllTasks(TaskList taskList) {
        ArrayList<String> toPrint = new ArrayList<>();
        toPrint.add("Here are the tasks in your list:");
        for (int i = 0; i < taskList.getAllTasks().size(); i++) {
            toPrint.add("" + (i + 1) + "." + taskList.getAllTasks().get(i).getFullDescription());
        }
        consolePrint(toPrint.toArray(new String[0]));
    }

    /**
     * Method to be called when user prompts for a task to be marked as done.
     * @param taskList : list of tasks.
     * @param input : Input containing task numbers to be marked as done by user.
     */
    public void markDone(TaskList taskList, String input) {
        String[] allInputs = input.split(" ");
        ArrayList<String> toPrint = new ArrayList<>();
        toPrint.add("Nice! I've marked the following task(s) as done:");
        for (String i : allInputs) {
            if (!("done").equals(i)) {
                int index = Integer.parseInt(i) - 1;
                ITask chosenToDos = taskList.getTask(index);
                chosenToDos.markAsDone();
                toPrint.add(chosenToDos.getFullDescription());
            }
        }
        consolePrint(toPrint.toArray(new String[0]));
    }

    /**
     * Method to be called when a Invalid Command is input by the user.
     * @param newException : Exception that is thrown when an Invalid Command is detected
     */
    public void invalidCommandMessage(Exception newException) {
        consolePrint(newException.getMessage());
    }

    /**
     * Method that is called when user wishes to delete a task.
     * This method is responsible for handling printing of horizontal lines.
     * @param taskList : List of tasks from which the chosen task should be deleted from.
     * @param input : Input containing task numbers to delete as given by the user.
     */
    public void deleteTask(TaskList taskList, String input) {
        ArrayList<String> toPrint = new ArrayList<>();
        toPrint.add("Noted. I've removed the following task(s):");
        String[] allInputs = input.split(" ");
        for (String i : allInputs) {
            if (!("delete").equals(i)) {
                int index = Integer.parseInt(i) - 1;
                ITask chosenTask = taskList.getTask(index);
                toPrint.add(chosenTask.getFullDescription());
                taskList.deleteFromList(chosenTask);
            }
        }
        String grammarTasks = taskList.getNumOfTasks() == 1 ? "tasks" : "task";
        toPrint.add("Now you have " + taskList.getNumOfTasks() + " " + grammarTasks + " in the list.");
        consolePrint(toPrint.toArray(new String[0]));
    }

    /**
     * Method that is called when user wishes to find a specific task.
     * This method updates the UI (in this case CLI) with relevant print messages and information.
     * @param taskList : Current list of tasks. Users will enter a keyword to search for a task residing in this list.
     * @param input : Full command that user has keyed into CLI.
     */
    public void findTask(TaskList taskList, String input) {
        ArrayList<String> toPrint = new ArrayList<>();
        toPrint.add("Here are the matching tasks in your list:");
        ArrayList<ITask> results = taskList.getSearchedTasks(input);
        for (int i = 0; i < results.size(); i++) {
            toPrint.add("" + (i + 1) + "." + results.get(i).getFullDescription());
        }
        consolePrint(toPrint.toArray(new String[0]));
    }

    /**
     * Method that is called when user wants to find upcoming tasks within a time limit of their choice.
     * @param taskList Current list of tasks.
     * @param input User command including time limit before which to find upcoming tasks.
     *              If left blank, it will be seven days from current date by default.
     * @throws ParseException : Parsing error (If the date and time is not entered in dd/MM/yyyy HHmm)
     */
    public void remindTask(TaskList taskList, String input) throws ParseException {
        String limit;
        Scanner sc = new Scanner(input);
        sc.next();
        if (sc.hasNext()) {
            limit = sc.nextLine();
        } else {
            limit = "";
        }

        ArrayList<String> toPrint = new ArrayList<>();
        toPrint.add("Here are the upcoming tasks in your list:");
        ArrayList<ITask> results = taskList.getUpcomingTasks(limit);
        for (int i = 0; i < results.size(); i++) {
            toPrint.add("" + (i + 1) + "." + results.get(i).getFullDescription());
        }
        consolePrint(toPrint.toArray(new String[0]));
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
        sc.next();
        String tempDate = sc.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(tempDate);
        String formattedDate = new SimpleDateFormat("d MMMM yyyy").format(date);
        ArrayList<ITask> results = taskList.getSchedule(formattedDate);

        ArrayList<String> toPrint = new ArrayList<>();
        if (results.isEmpty()) {
            toPrint.add("Your schedule for " + formattedDate + " is empty.");
        } else {
            toPrint.add("Here is the schedule for the specified date:");
            for (int i = 0; i < results.size(); i++) {
                toPrint.add("" + (i + 1) + "." + results.get(i).getFullDescription());
            }
        }
        consolePrint(toPrint.toArray(new String[0]));
    }

    /**
     * Prints tasks sorted by deadline/event time with free time slots in between.
     * @param taskList Current list of tasks.
     * @param input The full command by the user.
     * @throws ParseException parsing error if date and time are not in correct format.
     */
    public void findFreeSlots(TaskList taskList, String input) throws ParseException {
        Scanner sc = new Scanner(input);
        String tempDate;
        if (sc.hasNext()) {
            tempDate = sc.nextLine();
        } else {
            tempDate = "";
        }

        ArrayList<String> toPrint = new ArrayList<>();
        ArrayList<String> freeTimeSlots = taskList.findFreeSlots(tempDate);
        toPrint.add("Here are the free time slots you have between your tasks:");
        toPrint.addAll(freeTimeSlots);
        consolePrint(toPrint.toArray(new String[0]));
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
     * Method called when users wishes to view all Projects that are currently created or stored.
     * @param allProjects List of Projects returned to View model by the Controller from the Repository
     */
    public void viewAllProjects(ArrayList<IProject> allProjects) {
        ArrayList<String> toPrint = new ArrayList<>();
        toPrint.add("Here are all the Projects you are managing:");
        for (int i = 0; i < allProjects.size(); i++) {
            toPrint.add("" + (i + 1) + ". " + allProjects.get(i).getDescription() + " "
                + allProjects.get(i).getMembers());
        }
        consolePrint(toPrint.toArray(new String[0]));
    }

    /**
     * Method called to confirm the date and time of a tentative task.
     * @param taskList list of all tasks
     * @param input index of tentative task in taskList
     * @throws ClassCastException Exception thrown if input is not of correct class (Tentative class)
     * @throws ArrayIndexOutOfBoundsException Exception thrown if index given is not within what is available
     */
    public void confirmTentativeTask(TaskList taskList, String input) throws ClassCastException,
            ArrayIndexOutOfBoundsException {
        Scanner sc = new Scanner(input);
        String dummy = sc.next();
        int taskIndex = Integer.parseInt(sc.next()) - 1;
        Tentative taskToBeConfirmed = (Tentative) taskList.getTask(taskIndex);
        String[] tentativeDateTimeStrings = taskToBeConfirmed.getTentativeDateTimeStrings();
        System.out.println(horiLine);
        System.out.println("\tWhich timing do you want to confirm?");
        for (int i = 0; i < tentativeDateTimeStrings.length; i++) {
            System.out.println((i + 1) + ". " + tentativeDateTimeStrings[i]);
        }
        System.out.println(horiLine);

        Scanner sc1 = new Scanner(System.in);
        String inputForChosenTiming = sc1.nextLine();
        String description = taskToBeConfirmed.getDescriptionOnly();

        int indexOfChosenTiming = Integer.parseInt(inputForChosenTiming) - 1;
        Date[] tentativeDateTimeObjects = taskToBeConfirmed.getTentativeDateTimeObjects();
        Date chosenDateTimeObject = tentativeDateTimeObjects[indexOfChosenTiming];
        taskList.deleteFromList(taskToBeConfirmed);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        String formattedInputDate = formatter.format(chosenDateTimeObject);
        String newEventInput = "event " + description + " /at " + formattedInputDate;
        consoleInputController.onCommandReceived(newEventInput);
    }

    /**
     * Method called to inform the user that the project has exited.
     * @param projectName The name of the project exited.
     */
    public void exitProject(String projectName) {
        consolePrint("Exited project: " + projectName);
    }

    /**
     * Adds a member to the project.
     * @param projectToManage The project specified by the user.
     * @param newMember A new member with details specified by the user.
     */
    public void addMember(IProject projectToManage, Member newMember) {
        projectToManage.addMember(newMember);
        consolePrint("Added new member to: " + projectToManage.getDescription(), ""
            + "Member details: " + newMember.getDetails());
    }

    /**
     * Adds a member to the project.
     * @param projectToManage The project specified by the user.
     * @param newTask A new task with details specified by the user.
     */
    public void addTask(IProject projectToManage, Task newTask) {
        projectToManage.addTask(newTask);
        consolePrint("Added new task to the list.");
    }

    /**
     * Shows the details of all the members in the project.
     * Can be updated later on to include more information (tasks etc).
     * @param projectToManage The project specified by the user.
     */
    public void viewAllMembers(IProject projectToManage) {
        ArrayList<String> allMemberDetails = projectToManage.getMembers().getAllMemberDetails();
        consolePrint(allMemberDetails.toArray(new String[0]));
    }

    /**
     * Shows the details of all the task in the project.
     * @param projectToManage The project specified by the user.
     */
    public void viewAllTasks(IProject projectToManage) {
        ArrayList<String> allTaskDetails = projectToManage.getTasks().getAllTaskDetails();
        consolePrint(allTaskDetails.toArray(new String[0]));
    }
}
