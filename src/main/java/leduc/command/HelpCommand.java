package leduc.command;

import leduc.Ui;
import leduc.exception.DukeException;
import leduc.storage.Storage;
import leduc.task.TaskList;

/**
 * Display every command
 */
public class HelpCommand extends Command{

    /**
     * Constructor
     * @param user user input
     */
    public HelpCommand(String user) {
        super(user);
    }

    /**
     * display every command
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        ui.showHelp();
    }
}
