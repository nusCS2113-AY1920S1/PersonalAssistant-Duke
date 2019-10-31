package compal.logic.command;

import compal.commons.LogUtils;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class ImportCommand extends Command {

    public static final String MESSAGE_SUCCESS = "You have successfully imported your schedule!\n";

    public static final String MESSAGE_FILE_NON_EXIST = "Error: File specified to import does not exist!";
    public static final String MESSAGE_FILE_NON_ICS = "Error: File is not a ICS file format that can be read from!";


    private String fileName;
    private static final Logger logger = LogUtils.getLogger(ImportCommand.class);

    /**
     * Construct the ExportCommand class.
     *
     * @param fileName the file to be exported to
     */
    public ImportCommand(String fileName) {
        this.fileName = fileName.concat(".ics");
    }

    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {
        logger.info("Attempting to execute export command");

        if (!checkIfIcsFile()) {
            throw new CommandException(MESSAGE_FILE_NON_ICS);
        }

        readFromFile(taskList);

        logger.info("Successfully executed export command");
        return new CommandResult(MESSAGE_SUCCESS, true);
    }

    /**
     * Read from file to import from.
     *
     * @throws CommandException if file does not exist.
     */
    private void readFromFile(TaskList taskList) throws CommandException {
        final String icsBeginEvent = "BEGIN:VEVENT";
        final String icsEndEvent = "END:VEVENT";

        BufferedReader reader;
        StringBuffer eventString = new StringBuffer();

        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                if (icsBeginEvent.equals(line)) {
                    while (!icsEndEvent.equals(line)) {
                        eventString.append(line).append("\n");
                        line = reader.readLine();
                    }
                }
                createTasks(eventString.toString(), taskList);
                eventString.delete(0, eventString.length());
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_NON_EXIST);
        }
    }

    /**
     * Check if file reading from is truly a ics file format.
     *
     * @throws CommandException if file is not a ics file
     */
    private boolean checkIfIcsFile() throws CommandException {

        final String icsHeader = "BEGIN:VCALENDAR";
        final String icsProdId = "PRODID";
        final String icsVersion = "VERSION";
        final String icsCalGre = "CALSCALE:GREGORIAN";
        BufferedReader reader;

        try {

            reader = new BufferedReader(new FileReader(fileName));

            String lineOne = reader.readLine();
            String lineTwo = reader.readLine();
            String lineThree = reader.readLine();
            String lineFour = reader.readLine();
            reader.close();

            if (lineOne.equals(icsHeader) && lineTwo.contains(icsProdId)
                && lineThree.contains(icsVersion) && lineFour.equals(icsCalGre)) {
                return true;
            }
        } catch (IOException | NullPointerException e) {
            throw new CommandException(MESSAGE_FILE_NON_ICS);
        }
        return false;
    }

    /**
     * Create a task object from eventString.
     *
     * @param eventString The string of the event read from ics
     */
    public void createTasks(String eventString, TaskList taskList) {

        if (eventString.isEmpty()) {
            return;
        }

        String taskDesc = getDesc(eventString);
        Task.Priority taskPriority = getPriority(eventString);

        final String startToken = "DTSTART:";
        String taskStartTime = "";
        String taskStartDate = "";
        if (eventString.contains(startToken)) {
            int startPoint = eventString.indexOf(startToken);
            String statusStartInput = eventString.substring(startPoint);
            Scanner scanner = new Scanner(statusStartInput);
            String temp = scanner.nextLine();
            String[] parts = temp.split(":");
            String[] dateTime = parts[1].split("T");

            String year = dateTime[0].substring(0, 4);
            String month = dateTime[0].substring(4, 6);
            String day = dateTime[0].substring(6);
            taskStartTime = dateTime[1].substring(0, 4);
            taskStartDate = day + "/" + month + "/" + year;
        }

        final String endToken = "DTEND:";
        String taskEndTime = "";
        String taskEndDate = "";
        if (eventString.contains(endToken)) {
            int startPoint = eventString.indexOf(endToken);
            String statusStartInput = eventString.substring(startPoint);
            Scanner scanner = new Scanner(statusStartInput);
            String temp = scanner.nextLine();
            String[] parts = temp.split(":");
            String[] dateTime = parts[1].split("T");


            String year = dateTime[0].substring(0, 4);
            String month = dateTime[0].substring(4, 6);
            String day = dateTime[0].substring(6);
            taskEndTime = dateTime[1].substring(0, 4);
            taskEndDate = day + "/" + month + "/" + year;
        }


        if (taskStartDate.isEmpty() || taskStartTime.isEmpty()) {
            return;
        }


        if (taskEndDate.isEmpty()) {
            taskEndDate = taskStartDate;
        }

        if (taskEndTime.isEmpty()) {
            taskEndTime = taskStartTime;
        }

        if ((taskStartDate.equals(taskEndDate) && taskStartTime.equals(taskEndTime))) {
            Deadline isDeadline = new Deadline(taskDesc, taskPriority, taskStartDate, taskStartTime);
            taskList.addTask(isDeadline);
        } else {
            Event isEvent = new Event(taskDesc, taskPriority, taskStartDate, taskEndDate, taskStartTime, taskEndTime);
            taskList.addTask(isEvent);
        }
    }

    private String getDesc(String eventString) {
        final String summaryToken = "SUMMARY:";
        String taskDesc = "";
        if (eventString.contains(summaryToken)) {
            int startPoint = eventString.indexOf(summaryToken);
            String statusStartInput = eventString.substring(startPoint);
            Scanner scanner = new Scanner(statusStartInput);
            String temp = scanner.nextLine();
            String[] parts = temp.split(":");
            taskDesc = parts[1];
        }
        return taskDesc;
    }

    private Task.Priority getPriority(String eventString){
        final String descToken = "DESCRIPTION:";
        final String priorityToken = "Priority:";
        Task.Priority taskPriority = Task.Priority.low;
        if (eventString.contains(descToken) && eventString.contains(priorityToken)) {
            int startPoint = eventString.indexOf(priorityToken);
            String statusStartInput = eventString.substring(startPoint);
            Scanner scanner = new Scanner(statusStartInput);
            String temp = scanner.nextLine();
            String[] parts = temp.split(":");

            String tempPrio = parts[1];
            if (tempPrio.equals("high")) {
                taskPriority = Task.Priority.high;
            } else if (tempPrio.equals("medium")) {
                taskPriority = Task.Priority.medium;
            } else if (tempPrio.equals("low")) {
                taskPriority = Task.Priority.low;
            }
        } else if (eventString.contains(descToken)) {
            taskPriority = Task.Priority.low;
        }
        return taskPriority;
    }
}
