/**
 * Represents  an abstract Command that could be an add, delete, exit, done, find or list
 */
public abstract class Command {

    abstract public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException;

    /**
     * Returns the boolean indicating that it is( not) an {@link ExitCommand}
     * @return false by default
     */
    public boolean isExit() {
        return false;
    }
}
