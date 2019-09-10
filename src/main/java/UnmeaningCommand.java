/**
 * Represents a Command which is not understood.
 */
public class UnmeaningCommand extends Command {
    /**
     * Constructor of UnmeaningCommand.
     * @param user String which represent the input string of the user.
     */
    public  UnmeaningCommand(String user){
        super(user);
    }

    /**
     * Throw the exception to tell the user that the input string is not understood.
     * @param tasks TaskList which is the list of task.
     * @param ui Ui which deals with the interactions with the user.
     * @param storage Storage which deals with loading tasks from the file and saving tasks in the file.
     * @param parser Parser which deals with making sense of the user command.
     * @throws UnmeaningException Exception caught when the input string could not be interpreted.
     */
    public void execute(TaskList tasks, Ui ui , Storage storage, Parser parser) throws UnmeaningException{
        throw new UnmeaningException(ui);
    }

    /**
     * Returns a boolean false as it is a Unmeaning command.
     * @return a boolean false.
     */
    public boolean isExit(){
        return false;
    }
}
