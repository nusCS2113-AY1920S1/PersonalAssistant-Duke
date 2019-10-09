package leduc.command;

import leduc.Ui;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.DeadlinesTask;
import leduc.task.Task;
import leduc.task.TaskList;


/**
 * Represents Snooze command which snooze the deadline of a deadline task.
 */
public class SnoozeCommand extends Command{

    public static String snoozeShortcut = "snooze";
    /**
     * Constructor of SnoozeCommand.
     * @param user user String which represent the input string of the user.
     */
    public SnoozeCommand(String user){
        super(user);
    }

    /**
     * Allows to snooze the deadline of a deadline task.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws NonExistentTaskException Exception caught when the task does not exist.
     * @throws DeadlineTypeException Exception caught when the task is not a deadline task.
     * @throws FileException Exception caught when the file doesn't exist or cannot be created or cannot be opened.
     */
    public void execute(TaskList tasks, Ui ui , Storage storage) throws NonExistentTaskException, DeadlineTypeException, FileException {
        int index = Integer.parseInt(user.substring(7)) - 1;
        if (index > tasks.size() - 1 || index < 0) {
            throw new NonExistentTaskException();
        }
        else { // the tasks exist
            Task snoozeTask = tasks.get(index);
            if (!snoozeTask.isDeadline()){
                throw new DeadlineTypeException();
            }
            DeadlinesTask snoozeDeadlineTask = (DeadlinesTask) snoozeTask;
            snoozeDeadlineTask.snoozeDeadline();
            storage.save(tasks.getList());
            ui.display("\t Noted. I've snoozed this task: \n" +
                    "\t\t "+snoozeDeadlineTask.getTag() + snoozeDeadlineTask.getMark() + " " + snoozeDeadlineTask.getTask()+
                    " by:" + snoozeDeadlineTask.getDeadlines() + "\n");
        }
    }

    /**
     * Returns a boolean false as it is a SnoozeCommand.
     * @return a boolean false.
     */
    public boolean isExit(){
        return false;
    }

    public static String getSnoozeShortcut() {
        return snoozeShortcut;
    }

    public static void setSnoozeShortcut(String snoozeShortcut) {
        SnoozeCommand.snoozeShortcut = snoozeShortcut;
    }
}
