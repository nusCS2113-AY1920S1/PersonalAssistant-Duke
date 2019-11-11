package ui;

import exception.DukeException;
import task.TaskList;

import java.util.HashMap;
import java.util.Scanner;

/**
 * UI class, displays system messages.
 * Collects User input.
 *
 * @author Kane Quah
 * @version 1.0
 * @since 08/19
 */
public class UI {
    private Scanner sc;
    private String loadingError = "Formatting Issues Encountered. New Task List initialized";
    private String line = "_______________________________________________________________________________________"
            + "_______";
    private String goodBye = "Bye! This program will exit in a short while. Hope to see you again soon!";
    private String closeSuccess = "File successfully saved!";
    private String closeFail = "File failed to save";
    private HashMap<String, String> helpMap = new HashMap<>();

    /**
     * Constructor that also creates a hashmap of help commands.
     */
    public UI() {
        helpMap.put("add", "Adds a degree to your choices.\n"
                + "Also adds events related to that degree to your tasks.\n\n"
                + "Format: add <degree>\n"
                + "Examples: add ISE | add Industrial and Systems Engineering\n");

        helpMap.put("bye", "Exits the Program after a short delay.\n");

        //tasks Command.
        helpMap.put("tasks", "Displays the current list of tasks.\n"
                + "Will also switch to the \"Tasks\" tab.\n");

        //choices Command.
        helpMap.put("choices", "Displays your current choices of degree.\n"
                + "Will also switch to the \"Degree Choices\" tab.\n");

        //help Command.
        helpMap.put("help", "Displays help for all commands, or a certain command.\n"
                + "Will switch to the \"Help\" tab when input on its own.\n"
                + "Will NOT switch tabs when looking up a particular command.\n\n"
                + "Format: help | help <command>\n"
                + "Examples: help tasks | help add | help choices\n");

        //detail Command.
        helpMap.put("detail", "Displays all modules and their module credits related to the given degree.\n"
                + "Will also switch to the \"Degree Information\" tab.\n"
                + "Can be used on its own to simply switch tabs. \n\n"
                + "Format: detail | detail <degree>\n"
                + "Examples: detail bme | detail Biomedical Engineering\n");

        //swap Command.
        helpMap.put("swap", "Swaps 2 degrees with the given IDs in your degree choices.\n"
                + "Accepts only integers. \n\n"
                + "Format: swap <ID> <ID>\n"
                + "Examples: swap 1 2 | swap 01 02\n");

        //delete Command.
        helpMap.put("delete", "Deletes a task from your task list corresponding to the ID of the task.\n"
                + "Accepts only integers. \n\n"
                + "Format: delete <ID>\n"
                + "Examples: delete 1 | delete 02\n");

        //remove Command.
        helpMap.put("remove", "Removes a degree corresponding to the ID from your choice of degrees.\n"
                + "Accepts only integers. \n\n"
                + "Format: remove <ID>\n"
                + "Examples: remove 1 | remove 02\n");

        //done Command.
        helpMap.put("done", "Marks a task corresponding to the ID as done.\n"
                + "Accepts only integers. \n\n"
                + "Format: done <ID>\n"
                + "Examples: done 1 | done 02\n");

        //sort Command.
        helpMap.put("sort", "Sorts your tasks according to a given category.\n"
                + "Accepted categories are: priority, date, degree. \n\n"
                + "Format: sort by <Category>\n"
                + "Examples: sort by priority | sort by date | sort by degree\n");

        //view_employment Command.
        helpMap.put("view_employment", "Displays employment rate for a given degree.\n"
                + "This produces a bar graph in a separate window. \n\n"
                + "Format: view_employment <Degree>\n"
                + "Examples: view_employment bme | view_employment ise\n");

        //compare Command.
        helpMap.put("compare", "Compares 2 degrees together and displays the differences in modules and their credits.\n"
                + "Will also switch to the \"Degree Differences\" tab.\n"
                + "Can be used on its own to simply switch tabs.\n\n"
                + "Format: compare | compare <Degree> <Degree>\n"
                + "Examples: compare Biomedical Engineering Computer Engineering | compare ise ee\n");

        //Todoo Command.
        helpMap.put("todo", "Adds a Todo task to your list of tasks.\n"
                + "Todo tasks do not require deadlines.\n"
                + "Optional priorities can be set when adding tasks from: low, normal, high, very high.\n"
                + "This is done by adding /priority <priority> behind the task.\n\n"
                + "Format: todo <Description> | todo <Description> /priority <priority>\n"
                + "Examples: todo Sleep | todo Eat /priority high\n");

        //event Command.
        helpMap.put("event", "Adds an event task to your list of tasks.\n"
                + "Event tasks require deadlines in the following format: DD-MM-YYYY HHmm.\n"
                + "You cannot input start and end times. Only events from adding degrees will have it. \n"
                + "Optional priorities can be set when adding tasks from: low, normal, high, very high.\n"
                + "This is done by adding /priority <priority> behind the task.\n\n"
                + "Format: event <Description> /at <DD-MM-YYYY HHmm> |\n"
                + "event <Description> /at <DD-MM-YYYY HHmm> /priority <priority> \n"
                + "Examples: event Sleep /at 01-01-1970 2359 | event Eat /at 01-02-2019 1500 /priority very high\n");

        //deadline Command.
        helpMap.put("deadline", "Adds a deadline task to your list of tasks.\n"
                + "Deadline tasks require deadlines in the following format: DD-MM-YYYY HHmm.\n"
                + "Optional priorities can be set when adding tasks from: low, normal, high, very high.\n"
                + "This is done by adding /priority <priority> behind the task.\n\n"
                + "Format: deadline <Description> /by <DD-MM-YYYY HHmm> |\n"
                + "deadline <Description> /by <DD-MM-YYYY HHmm> /priority <priority> \n"
                + "Examples: deadline Sleep /by 01-01-1970 2359 | deadline Eat /by 01-02-2019 1500 /priority very high\n");

        //cohort_size Command.
        helpMap.put("cohort_size", "Displays cohort size for a given degree.\n"
                + "This produces a bar graph in a separate window. \n\n"
                + "Format: cohort_size <Degree>\n"
                + "Examples: cohort_size bme | cohort_size ise\n");

        //find Command.
        helpMap.put("find", "Checks your task list and searches for a tasks matching the input string.\n"
                + "This command is case sensitive. \n\n"
                + "Format: find <String>\n"
                + "Examples: find application | find Sleep\n");

        //snooze Command.
        helpMap.put("snooze", "Changes the deadline of the task corresponding to the input ID.\n"
                + "Format of deadline is DD-MM-YYYY HHmm. \n\n"
                + "Format: snooze <ID> /to <DD-MM-YYYY HHmm>\n"
                + "Examples: snooze 1 /to 01-01-1970 2030 | snooze 02 /to 11-11-2019 2030\n");

        //schedule Command.
        helpMap.put("schedule", "Retrieves and displays all tasks happening on the given date.\n"
                + "Format of date is DD-MM-YYYY. \n\n"
                + "Format: schedule <DD-MM-YYYY>\n"
                + "Examples: schedule 01-01-1970 | schedule 18-05-2019\n");

        //undo Command.
        helpMap.put("undo", "Undoes the most recent command.\n"
                + "This only works for commands that modify tasks or choices.\n");

        //redo Command.
        helpMap.put("redo", "Redoes the most recent undone command.\n"
                + "This only works for commands that modify tasks or choices.\n");

        //keywords Command.
        helpMap.put("keywords", "Displays the degrees and their accepted keywords and aliases.\n"
                + "Will also switch to the \"Keywords\" tabs.\n"
                + "These keywords and aliases are compatible with the \"add\", \"detail\" and \"compare\" command.\n"
                + "Only KEYWORDS are compatible with the \"view_employment\" and \"cohort_size\" command.\n");
    }

    /**
     * Displays Welcome message.
     */
    public void showWelcome(TaskList task) {
        this.sc = new Scanner(System.in);
        System.out.println("Hello! Welcome to Degree.NUS\n");

        task.printReminders();
        System.out.println("What can I do for you?\n");
    }

    /**
     * Reads the next line, to be parsed by Parser.
     *
     * @return String next line
     * @throws DukeException DukeException thrown if forced to read when there is nothing
     */
    public String readCommand() throws DukeException {
        if (sc.hasNextLine()) {
            return sc.nextLine();
        } else {
            throw new DukeException("There are no more lines to be read");
        }

    }

    /**
     * Prints out a line of dashes.
     */
    public String showLine() {
        System.out.println(this.line);
        return this.line;
    }

    /**
     * Prints out error.
     *
     * @param error String message from error
     */
    public void showError(String error) {
        System.out.println(error);
    }

    /**
     * Prints default loading error message.
     */
    public void showLoadingError() {
        System.out.println(this.loadingError);
    }

    /**
     * Prints out program termination line.
     */
    public void hastaLaVista() {
        System.out.println(this.goodBye);
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        this.sc.close();
    }

    /**
     * Informs user if files were stored successfully.
     */
    public void closeSuccess() {
        System.out.println(this.closeSuccess);
    }

    /**
     * Informs user that files failed to store.
     */
    public void closeFailure() {
        System.out.println(this.closeFail);
    }

    /**
     * Method to print out the help for one command as specified by the user.
     *
     * @param input The command that the user wants help in.
     */
    public void getHelp(String input) {
        System.out.println(helpMap.get(input));
    }

    /**
     * Method to print out all help commands available.
     */
    public void getAllHelp() {
        helpMap.forEach((key, value) -> {
            System.out.println(key + ":\n\n" + value);
            //ui.showLine();
        });
    }
}
