package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.Storage;
import chronologer.task.TaskList;

public class ThemeCommand extends Command  {
    int choiceOfTheme;

    public ThemeCommand(int choiceOfTheme) {
        this.choiceOfTheme = choiceOfTheme;
    }
    
    /**
     * Removes the task from the TaskList and saves the updated TaskList to persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    
    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        tasks.updateTheme(choiceOfTheme);
    }

}
