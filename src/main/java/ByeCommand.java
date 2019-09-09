/**
 * Represents the command to end the program.
 */
public class ByeCommand extends Command {

    /**
     * Executes the updating of the file with current list of tasks
     * in the TaskList object and displays the goodbye message
     * of the program.
     * @param list The TaskList object to update the file
     * @param ui The Ui object to display the goodbye message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display goodbye message
     * @throws Exception On file not found error
     */
    @Override
    public String execute(TaskList list, Ui ui, Storage storage) throws Exception {
        storage.updateFile(list);
        return ui.showBye();
    }
}
