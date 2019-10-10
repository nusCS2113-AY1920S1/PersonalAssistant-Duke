package leduc.command;

import leduc.exception.FileException;
import leduc.exception.NonExistentTaskException;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.Task;
import leduc.task.TaskList;

/**
 * Represents a Delete Command.
 */
public class DeleteCommand extends Command {
    /**
     * static variable used for shortcut
     */
    private static String deleteShortcut = "delete";
    /**
     * Constructor of DeleteCommand.
     * @param user String which represent the input string of the user.
     */
    public  DeleteCommand(String user){
        super(user);
    }

    /**
     * Delete the task from the task list and rewrite the data file without the deleted task.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws NonExistentTaskException Exception caught when the task to delete does not exist.
     * @throws FileException Exception caught when the file can't be open or read or modify
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws NonExistentTaskException, FileException {
        int index = Integer.parseInt(user.substring(7)) - 1;
        if (index > tasks.size() - 1 || index < 0) {
            throw new NonExistentTaskException();
        }
        else { // the tasks exist
            Task removedTask = tasks.remove(index);
            storage.save(tasks.getList());
            ui.display("\t Noted. I've removed this task: \n" +
                    "\t\t" + removedTask.toString() +
                    "\n\t Now you have "+ tasks.size() +" tasks in the list");
        }
    }
    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getDeleteShortcut() {
        return deleteShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param deleteShortcut the new shortcut
     */
    public static void setDeleteShortcut(String deleteShortcut) {
        DeleteCommand.deleteShortcut = deleteShortcut;
    }
}
