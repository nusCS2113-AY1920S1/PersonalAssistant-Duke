package command;

import degree.DegreeManager;
import storage.Storage;
import ui.UI;
import task.TaskList;
import exception.DukeException;
import list.DegreeList;

import java.util.HashMap;

/**
 * AddCommand Class extends the abstract Command class.
 * Called when items should be ADDED to tasks.
 * @author Kane Quah
 * @version 1.0
 * @since 09/19
 */
public class HelpCommand extends Command {
    private String arguments;
    private String command;

    private HashMap<String, String> helpMap = new HashMap<>();

    /**
     * Creates HelpCommand for a particular command.
     * @param command command type to be used.
     * @param arguments to be added.
     */
    public HelpCommand(String command, String arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    /**
     * Creates HelpCommand and display help for all commands.
     * @param command command type to be used.
     */
    public HelpCommand(String command) {
        this.command = command;
        this.arguments = "";
    }

    /**
     * overwrites default execute to add tasks.
     * @param tasks TasksList has tasks.
     * @param ui UI prints messages.
     * @param storage Storage loads and saves files.
     * @param degreesManager
     * @throws DukeException DukeException throws exception.
     */
    public void execute(TaskList tasks, UI ui, Storage storage, DegreeList lists, DegreeManager degreesManager) {
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
                + "Will also switch to the \"Help\" tab containing all the commands compatible with help.\n"
                + "Can be used on its own to simply switch tabs and display help for all commands.\n\n"
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
                + "Format: compare| compare <Degree> <Degree>\n"
                + "Examples: compare bme come | compare ise ee\n");

        //Todoo Command.
        helpMap.put("todo", "Adds a Todo task to your list of tasks.\n"
                + "Todo tasks do not require deadlines.\n"
                + "Optional priorities can be set when adding tasks from: low, normal, high, very high.\n"
                + "This is done by adding /priority <priority> behind the task.\n\n"
                + "Format: todo <Description> | todo <Description> /priority <priority>\n"
                + "Examples: todo Sleep | todo Eat /priority high");

        //event Command.
        helpMap.put("event", "Adds an event task to your list of tasks.\n"
                + "Event tasks require deadlines in the following format: DD-MM-YYYY HHmm.\n"
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
                + "Format: deadline <Description> /by <DD-MM-YYYY HHmm>\n"
                + "deadline <Description> /by <DD-MM-YYYY HHmm> /priority <priority> \n"
                + "Examples: deadline Sleep /by 01-01-1970 2359 | deadline Eat /by 01-02-2019 1500 /priority very high\n");

        //cohort_size Command.
        description = "Displays cohort size for a given degree.\n"
                + "This produces a bar graph in a separate window. \n\n"
                + "Examples: cohort_size bme | cohort_size ise";
        this.dataHelp.add(new HelpFX("cohort_size <Degree>", description));

        //find Command.
        description = "Checks your task list and searches for a tasks matching the input string.\n"
                + "This command is case sensitive. \n\n"
                + "Examples: find application | find Sleep";
        this.dataHelp.add(new HelpFX("find <String>", description));

        //snooze Command.
        description = "Changes the deadline of the task corresponding to the input ID.\n"
                + "Format of deadline is DD-MM-YYYY HHmm. \n\n"
                + "Examples: snooze 1 /to 01-01-1970 2030 | snooze 02 /to 11-11-2019 2030";
        this.dataHelp.add(new HelpFX("snooze <ID> /to \n<DD-MM-YYYY HHmm>", description));

        //schedule Command.
        description = "Retrieves and displays all tasks happening on the given date.\n"
                + "Format of date is DD-MM-YYYY. \n\n"
                + "Examples: schedule 01-01-1970 | schedule 18-05-2019";
        this.dataHelp.add(new HelpFX("schedule <DD-MM-YYYY>", description));

                /*
        if (this.arguments.matches("")) {
            System.out.println("help: Displays a full list of possible commands.\n"
                            + "detail DEGREE|MODULE: View detailed information about a degree or module\n"
                            + "compare DEGREE DEGREE: Lists the module similarities and differences "
                    + "between two degree programs given their keywords.\n"
                            + "add DEGREE [t/TAG]...: To add a newly chosen degree programme into "
                    + "your personalised degree list.\n"
                            + "degreelist: Shows a list of all degrees in the list.\n"
                            + "swap INDEX INDEX: Swaps the position of two degrees in the degree list.\n"
                            + "replace INDEX NEWDEGREE: Replaces and existing degree in the list with a new one.\n"
                            + "delete INDEX: Deletes the specified item in the list.\n"
                            + "clear: Clears all degrees from the list.\n"
                            + "undo: Undo the previous change to the task or degree list.\n"
                            + "redo: Redo the command that was previously undone.\n"
                            + "custom KEYWORD KEYPHRASE: Customize a word to be evaluated as a "
                    + "phrase to be executed with additional parameters.\n"
                            + "bye: Exits the program.");
        } else {
            if (this.arguments.matches("detail")) {
                System.out.println("detail DEGREE: View detailed information about a degree "
                        + "by using any of its aliases");
            } else if (this.arguments.matches("compare")) {
                System.out.println("compare DEGREE DEGREE: Lists the module similarities and differences "
                        + "between two degree programs given their keywords.");
            } else if (this.arguments.matches("add")) {
                System.out.println("add DEGREE [t/TAG]...: To add a newly chosen degree programme into "
                        + "your personalised degree list.");
                System.out.println("Here are the possible degrees that you can add to your personalised degree list:\n" +
                        "Biomedical Engineering\n" +
                        "Chemical Engineering\n" +
                        "Civil Engineering\n" +
                        "Computer Engineering\n" +
                        "Electrical Engineering\n" +
                        "Environmental Engineering\n" +
                        "Industrial Systems Engineering\n" +
                        "Mechanical Engineering\n" +
                        "Materials Science and Engineering");
            } else if (this.arguments.matches("degreelist")) {
                System.out.println("degreelist: Shows a list of all degrees in the list.");
            } else if (this.arguments.matches("swap")) {
                System.out.println("swap INDEX INDEX: Swaps the position of two degrees in the degree list.");
            } else if (this.arguments.matches("replace")) {
                System.out.println("replace INDEX NEWDEGREE: Replaces and existing degree in the list with a new one.");
            } else if (this.arguments.matches("delete")) {
                System.out.println("delete INDEX: Deletes the specified item in the list.");
            } else if (this.arguments.matches("clear")) {
                System.out.println("clear: Clears all degrees from the list.");
            } else if (this.arguments.matches("custom")) {
                System.out.println("custom KEYWORD KEYPHRASE: Customize a word to be evaluated as a "
                        + "phrase to be executed with additional parameters.");
            } else if (this.arguments.matches("bye")) {
                System.out.println("bye: Exits the program");
            } else if (this.arguments.matches("help")) {
                System.out.println("help: Displays a full list of possible commands.");
            } else if (this.arguments.matches("undo")) {
                System.out.println("undo: Undo the previous change to the task or degree list.");
            } else if (this.arguments.matches("redo")) {
                System.out.println("redo: Redo the command that was previously undone.");
            }
        }

                 */
    }
}

