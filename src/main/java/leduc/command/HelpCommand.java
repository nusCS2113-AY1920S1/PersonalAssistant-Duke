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
     * static variable used for shortcut
     */
    private static String helpShortcut = "help";

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
    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getHelpShortcut() {
        return helpShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param helpShortcut the new shortcut
     */
    public static void setHelpShortcut(String helpShortcut) {
        HelpCommand.helpShortcut = helpShortcut;
    }
}
