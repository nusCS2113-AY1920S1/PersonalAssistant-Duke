package Commands;

import Commons.LookupTable;
import Commons.Storage;
import Commons.Ui;
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
        help = "Here is a guide of how to use the different commands\n";
        help += "1) To add events, enter command in the format below\n " +
                "add/e modCode Description /at (date) /from HHmm /to HHmm\n\n"+
                "2) To add deadlines, enter command in the format below\n " +
                "add/d modCode Description /by (date) HHmm\n\n" +
                "3) To delete events, enter command in the format below\n " +
                "delete/e modCode Description /at (date) /from HHmm /to HHmm\n\n" +
                "4) To delete deadlines, enter command in the format below\n " +
                "delete/d modCode Description /by (date) HHmm\n\n" +
                "5) To filter keywords, enter command in the format below\n " +
                "filter (keyword) \n\n";

         help += "date format can be in the form dd/mm/yyyy or week X day according to NUS academic calender";


        return ui.showHelp(help);
    }
}
