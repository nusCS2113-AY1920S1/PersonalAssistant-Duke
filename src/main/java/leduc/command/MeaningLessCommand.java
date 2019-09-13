package leduc.command;

import leduc.Parser;
import leduc.exception.MeaningLessException;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.TaskList;

/**
 * Represents a meaning less command which is not understood.
 */
public class MeaningLessCommand extends Command {
    /**
     * Constructor of MeaningLessCommand.
     * @param user String which represent the input string of the user.
     */
    public MeaningLessCommand(String user){
        super(user);
    }

    /**
     * Throw the exception to tell the user that the input string is not understood.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @param parser leduc.Parser which deals with making sense of the user command.
     * @throws MeaningLessException Exception caught when the input string could not be interpreted.
     */
    public void execute(TaskList tasks, Ui ui , Storage storage, Parser parser) throws MeaningLessException {
        throw new MeaningLessException(ui);
    }

    /**
     * Returns a boolean false as it is a Unmeaning command.
     * @return a boolean false.
     */
    public boolean isExit(){
        return false;
    }
}
