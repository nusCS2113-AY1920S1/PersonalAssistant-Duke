package command;

import storage.Storage;
import ui.UI;
import task.TaskList;
import exception.DukeException;

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
     * @throws DukeException DukeException throws exception.
     */
    public void execute(TaskList tasks, UI ui, Storage storage) throws DukeException {
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
                            + "custom KEYWORD KEYPHRASE: Customize a word to be evaluated as a "
                    + "phrase to be executed with additional parameters.\n"
                            + "bye: Exits the program.");
        } else {
            if (this.arguments.matches("detail")) {
                System.out.println("detail DEGREE|MODULE: View detailed information about a degree or module");
            } else if (this.arguments.matches("compare")) {
                System.out.println("compare DEGREE DEGREE: Lists the module similarities and differences "
                        + "between two degree programs given their keywords.");
            } else if (this.arguments.matches("add")) {
                System.out.println("add DEGREE [t/TAG]...: To add a newly chosen degree programme into "
                        + "your personalised degree list.");
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
            }
        }

    }

}

