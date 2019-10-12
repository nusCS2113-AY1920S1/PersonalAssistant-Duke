package leduc.command;

import leduc.exception.DukeException;
import leduc.exception.EmptyArgumentException;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.TaskList;

/**
 * Abstract class which represent a Command.
 * The command asked by the user will be represented by one of the subclasses of leduc.command.Command.
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
     * Method that return false except for the subclass ByeCommand
     * @return a boolean: True if the input string of the user is "bye", False if not.
     */
    public boolean isExit(){
        return false;
    }

    /**
     * Abstract method: the operation of the specified command will be implemented.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws DukeException one of the subclass of leduc.exception.DukeException could be caught in the execution of this method.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, EmptyArgumentException;
}
