package Commands;

import Interface.LookupTable;
import Interface.Storage;
import Interface.Ui;
import Tasks.TaskList;
/**
 * Represents the command to display a guide to all commands
 */
public class HelpCommand extends Command{
    /**
     * Executes the displaying of guide to all commands
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the delete task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display delete task message
     */
    @Override
    public String execute(LookupTable LT,TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        String help;
        help = "Here is a guide of how to use the different commands";
        return ui.showHelp(help);
    }
}
