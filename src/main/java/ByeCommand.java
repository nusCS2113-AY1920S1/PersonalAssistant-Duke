/**
 * Represents a bye command when the user input "bye".
 */
public class ByeCommand extends Command {

    /**
     * Constructor of ByeCommand
     * @param user String which represent the input string of the user.
     */
    public  ByeCommand(String user){
        super(user);
    }

    /**
     * Execution of ByeCommand: the execution of Duke is ending and the Ui display a bye message.
     * @param tasks TaskList which is the list of task.
     * @param ui Ui which deals with the interactions with the user.
     * @param storage Storage which deals with loading tasks from the file and saving tasks in the file.
     * @param parser Parser which deals with making sense of the user command.
     */
    public void execute(TaskList tasks, Ui ui , Storage storage, Parser parser){
        ui.showBye();
    }

    /**
     * Returns is true for a ByeCommand.
     * @return True
     */
    public boolean isExit(){
        return true;
    }
}
