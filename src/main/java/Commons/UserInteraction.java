package Commons;

import Tasks.Assignment;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents the user interface which displays the messages to
 * respond to the user based on the user's input.
 */
public class UserInteraction {
    private static final String NO_FIELD = "void";
    private final String byeMessage = "Bye. Hope to see you again soon!\n";

    /**
     * Displays the exit message when Duke Program ends.
     */
    public String showBye() {
        return byeMessage;
    }

    /**
     * Displays add task message when user wants to add a task.
     */
    public String showAdd(Assignment task, int listSize) {
        return "Got it. I've added this task:\n" + task.displayString() + "\n"
                + "Now you have " + listSize + (listSize > 1 ? " tasks in the list.\n" : " task in the list.\n");
    }

    /**
     * Displays done task message when user marks a task as done.
     */
    public String showDone(Assignment task) {
        return "Nice! I've marked this task as done:\n" + task.displayString() + "\n";
    }

    /**
     * Displays the delete task message when user wants to delete a task.
     */
    public String showDelete(Assignment task, int listSize) {
        return "Noted. I've removed this task:\n" + task.displayString() + "\n" + "Now you have "
                + listSize  + (listSize > 1 ? " tasks in the list.\n" : " task in the list.\n");
    }

    /**
     * Displays the free time found with the template to be shown.
     * @param message The free times found
     * @return The output to be shown to the user
     */
    public String showFreeTimes(String message) {
        return ("You are available at: \n" + message);
    }

    /**
     * Displays the invalid chosen duration message.
     * @param message The chosen free time
     * @return The invalid free time with the proper format
     */
    public String showFreeTimesInvalidDuration(String message) {
        return "Invalid duration\n" + "Please enter the command in the format:\n"
                + "find 'x' hours, where 'x' is between 1 - 16";
    }

    public String showSelectionOption(Integer option, String selectedOption) {
        return "Selected option " + option + ":\n" + selectedOption;
    }

    /**
     * Displays the message which shows the list of reminders for all the tasks.
     */
    public String showListOfReminder(ArrayList<String> remindList) {
        if (remindList.isEmpty()) {
            return "There are no reminders set.";
        }
        String remindMessage = "Here is the list of reminders set:";
        for (String string : remindList) {
            remindMessage = remindMessage + "\n\n";
            Integer index = remindList.indexOf(string);
            remindMessage = remindMessage + (index + 1) + ". " + string;
        }
        return remindMessage;
    }

    /**
     * Displays the show reminder message when user sets a reminder for a task.
     */
    public String showReminder(Assignment task, String time) {
        return "Reminder has been set for " + task.getModCode() + " " + task.getDescription() + "at: " + time;
    }

    /**
     * Displays the show cancel reminder message when user sets a reminder for a task.
     */
    public String showCancelReminder(Assignment task, String time) {
        return "Reminder has been removed for " + task.getModCode() + " " + task.getDescription() + "on: " + time;
    }

    /**
     * Displays the error message if a file is not found.
     */
    public String showLoadingError(Exception e) {
        return "File not found" + e.getMessage() + "\n";
    }

    /**
     * Displays any of the DukeException error message caught throughout the program.
     */
    public String getError(Exception e) {
        return e.getMessage() + "\n";
    }

    /**
     * Display recurring tasks that are added.
     * @param description description of recurring task
     * @param startDate start of recurrence
     * @param endDate end of recurrence
     */
    public String showRecurring(String description, String startDate, String endDate,
                                boolean isBiweekly, boolean isRecur) {
        if (isRecur && isBiweekly) {
            return "Biweekly recurring task: " + description + " has been added between " + startDate
                    + " and " + endDate + "\n";
        } else if (isRecur) {
            return "Weekly recurring task: " + description + " has been added between " + startDate
                    + " and " + endDate + "\n";
        } else if (isBiweekly) {
            return "Biweekly recurring task: " + description + " has been removed between " + startDate
                    + " and " + endDate + "\n";
        } else {
            return "Weekly recurring task: " + description + " has been removed between " + startDate
                    + " and " + endDate + "\n";
        }
    }

    /**
     * Displays conflicting recurring task.
     * @param conflictList The list of conflicting tasks
     */
    public String showConflictRecurring(ArrayList<String> conflictList) {
        String out = "Sorry, you have conflicting events \n";
        for (int i = 0; i < conflictList.size(); i++) {
            out += (i + 1) + ". " + conflictList.get(i) + "\n";
        }
        return out;
    }

    /**
     * Display task with instance of keyword.
     * @param list List of task with keyword
     * @param keyword keyword entered by user
     */
    public String showFilter(ArrayList<String> list,String keyword) {

        if (list.size() == 0) {
            return "There are no task(s) matching your keyword.";
        } else {
            String message = "Here are the following events/deadline with the keyword " + keyword + "\n";
            for (int i = 1; i <= list.size(); i++) {
                message +=  i + ". " + list.get(i - 1) + "\n";
            }
            return message;
        }
    }

    /**
     * Display a guide to commands.
     */
    public String showHelp(String help) {
        return help;
    }

    /**
     * Display recommended weekly workload.
     * @param workloadMap map of weekly workload
     * @return This returns the string of workload
     * @throws ParseException on wrong date format
     */
    public String showWorkload(TreeMap<String, ArrayList<Assignment>> workloadMap, String workloadWeek)
            throws ParseException {
        String workloadSchedule = "Here is your recommended schedule for " + workloadWeek + ":\n";
        if (workloadMap.isEmpty()) {
            return "You have no tasks scheduled for " + workloadWeek + "!\n";
        } else {
            for (Map.Entry<String, ArrayList<Assignment>> workload: workloadMap.entrySet()) {
                Date tempDay = DukeConstants.EVENT_DATE_INPUT_FORMAT.parse(workload.getKey());
                String day = DukeConstants.DAY_FORMAT.format(tempDay);
                workloadSchedule = workloadSchedule + day + ": \n";
                for (Assignment task: workload.getValue()) {
                    workloadSchedule = workloadSchedule + task.getType() + " " + task.getModCode() + " "
                            + task.getDescription() + "\n";
                }
            }
        }
        return workloadSchedule;
    }

    /**
     * Display the previous list of commands requested by the user.
     * @param outputList list of all the commands user request
     * @return the list requested by user
     */
    public String showPrevious(ArrayList<String> outputList) {
        int size = outputList.size();
        if (size == 0) {
            String message = "There are no such input type in previous command";
            return message;
        } else {
            String output = "";
            for (int i = 0; i < size; i++) {
                output += (i + 1) + ". " + outputList.get(i);
            }
            return output;
        }
    }

    /**
     * Display the chosen previous input requested by the user.
     */
    public String showChosenPreviousChoice(String chosenInput) {
        String message = "Your chosen previous input is: \n" + chosenInput;
        return message;
    }
}