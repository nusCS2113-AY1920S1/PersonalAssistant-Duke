/**
 * Abstract class which represent a Command.
 * The command asked by the user will be represented by one of the subclasses of Command.
 */
public abstract class Command {
    protected String user;

    /**
     * Constructor of Command.
     * @param user String which represent the input string of the user.
     */
    public Command(String user ){
        this.user=user;
    }

    /**
     * Abstract method which returns a boolean: True if the input string of the user is "bye", False if not.
     * @return a boolean: True if the input string of the user is "bye", False if not.
     */
    public abstract boolean isExit();

    /**
     * Abstract method: the operation of the specified command will be implemented.
     * @param tasks TaskList which is the list of task.
     * @param ui Ui which deals with the interactions with the user.
     * @param storage Storage which deals with loading tasks from the file and saving tasks in the file.
     * @param parser Parser which deals with making sense of the user command.
     * @throws DukeException one of the subclass of DukeException could be caught in the execution of this method.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage, Parser parser) throws DukeException;
}
