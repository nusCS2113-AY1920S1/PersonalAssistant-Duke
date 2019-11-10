package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

/**
 * Allows the user to change themes.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class ThemeCommand extends Command  {
    private int choiceOfTheme;

    public ThemeCommand(int choiceOfTheme) {
        this.choiceOfTheme = choiceOfTheme;
    }
    
    /**
     * Updates the theme according to either light or dark.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @param history Allows the history features to be done.
     */
    
    @Override
    public void execute(TaskList tasks, Storage storage, ChronologerStateList history) throws ChronologerException {
        UiMessageHandler.outputMessage(tasks.updateTheme(choiceOfTheme));
    }

}
