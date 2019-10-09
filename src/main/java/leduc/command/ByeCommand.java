package leduc.command;

import leduc.exception.FileException;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.TaskList;

/**
 * Represents a bye command when the user input "bye".
 */
public class ByeCommand extends Command {

    /**
     * static variable used for shortcut
     */
    private static String byeShortcut = "bye";

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
     * @throws FileException Exception caught when the file can't be open or read or modify.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FileException {
        storage.save(tasks.getList());
        ui.showBye();
    }

    /**
     * Returns is true for a leduc.command.ByeCommand.
     * @return True
     */
    public boolean isExit(){
        return true;
    }

    /**
     * used when the user want to change the shortcut
     * @param byeShortcut the new shortcut
     */
    public static void setByeShortcut(String byeShortcut){
        ByeCommand.byeShortcut = byeShortcut;
    }

    /**
     * getter as the shortcut is private
     * @return the shortcut name
     */
    public static String getByeShortcut(){
        return ByeCommand.byeShortcut;
    }
}
