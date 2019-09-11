package leduc.command;

import leduc.Parser;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.TaskList;

/**
 * Represents a bye command when the user input "bye".
 */
public class ByeCommand extends Command {

    /**
     * Constructor of leduc.command.ByeCommand
     * @param user String which represent the input string of the user.
     */
    public  ByeCommand(String user){
        super(user);
    }

    /**
     * Execution of leduc.command.ByeCommand: the execution of leduc.Duke is ending and the leduc.Ui display a bye message.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @param parser leduc.Parser which deals with making sense of the user command.
     */
    public void execute(TaskList tasks, Ui ui , Storage storage, Parser parser){
        ui.showBye();
    }

    /**
     * Returns is true for a leduc.command.ByeCommand.
     * @return True
     */
    public boolean isExit(){
        return true;
    }
}
