package Commands;

import Commons.LookupTable;
import Commons.Storage;
import Commons.Ui;
import Tasks.TaskList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the command to display a guide to all commands
 */
public class HelpCommand extends Command{
    private static final Logger LOGGER = Logger.getLogger(LookupTable.class.getName());
    private static  String help = new String();
    /**
     * Executes the displaying of guide to all commands
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the delete task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display delete task message
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        try {
        String line;
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("documents/Help.txt");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        while ((line = reader.readLine()) != null) {
         help += line + "\n";
        }
        reader.close();
        isr.close();
        is.close();
    } catch (
    IOException e) {
        LOGGER.log(Level.SEVERE, e.toString(), e);
    }

        return ui.showHelp(help);
    }
}
