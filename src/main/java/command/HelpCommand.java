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

        //Display help for all commands
        if (this.arguments.matches("")) {
            ui.getAllHelp();
        } else { //Display help for the specified command.
            ui.getHelp(this.arguments);
        }
    }
}

