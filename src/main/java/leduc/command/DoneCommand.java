package leduc.command;

import leduc.exception.FileException;
import leduc.exception.NonExistentTaskException;
import leduc.storage.ConfigStorage;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.TaskList;

/**
 * Represents a Delete Command.
 */
public class DoneCommand extends Command {

    /**
     * static variable used for shortcut
     */
    private static String doneShortcut = "done";
    /**
     * Constructor of DoneCommand
     * @param user String which represent the input string of the user.
     */
    public  DoneCommand(String user){
        super(user);
    }

    /**
     * Change the mark of a task to done ("[✓]").
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @param configStorage
     * @throws NonExistentTaskException Exception caught when the task which is done does not exist.
     * @throws FileException Exception caught when the file can't be open or read or modify
     */
    public void execute(TaskList tasks, Ui ui, Storage storage, ConfigStorage configStorage) throws NonExistentTaskException, FileException {
        int index = Integer.parseInt(user.substring(DoneCommand.doneShortcut.length() + 1)) - 1;
        if (index > tasks.size() - 1 || index < 0) {
            throw new NonExistentTaskException();
        }
        else { // to change the mark, the whole file is rewritten ( probably a better way to do it)
            tasks.get(index).taskDone();
            //get the String with the index task marked done
            storage.save(tasks.getList());
            ui.display("\t Nice! I've marked this task as done:\n\t " + tasks.get(index).toString());
        }
    }
    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getDoneShortcut() {
        return doneShortcut;
    }
    /**
     * used when the user want to change the shortcut
     * @param doneShortcut the new shortcut
     */
    public static void setDoneShortcut(String doneShortcut) {
        DoneCommand.doneShortcut = doneShortcut;
    }
}
