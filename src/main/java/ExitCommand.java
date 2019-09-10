/**
 * Represents a command to exit Duke. The <code>ExitCommand</code> class
 * extends from the <code>Command</code> class for the user to quit the
 * program
 */
public class ExitCommand extends Command {
    /**
     * Constructs a <code>ExitCommand</code> object.
     */
    public ExitCommand() {
        super();
    }
    /**
     * Indicates whether Duke should exist
     * @return A boolean. True if the command tells Duke to exit, false
     *          otherwise.
     */
    @Override
    public boolean isExit() {
        return true;
    }
    /**
     * run the command with the respect TaskList, UI, and storage.
     * @param tasks The task list where tasks are saved.
     * @param ui The user interface.
     * @param storage object that handles local text file update
     */
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) {
        ui.exitInformation();
    }
}