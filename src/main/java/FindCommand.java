/**
 * Represent the command to find a task in the TaskList object
 */
public class FindCommand extends Command {

    protected String key;

    /**
     * Creates a FindCommand object.
     * @param key The keyword that the user wants to use to find in teh TaskList object
     */
    public FindCommand(String key){
        this.key = key;
    }

    /**
     * Executes the finding a task inside the TaskList object with the given keyword.
     * @param list The TaskList object used to find a task with the given keyword
     * @param ui The Ui object to display the find message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display find message
     */
    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        return ui.showFind(list.findTask(key));
    }
}
