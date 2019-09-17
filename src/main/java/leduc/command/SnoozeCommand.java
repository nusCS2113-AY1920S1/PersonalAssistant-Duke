package leduc.command;

import leduc.Parser;
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
     * @param parser leduc.Parser which deals with making sense of the user command.
     * @throws NonExistentTaskException Exception caught when the task does not exist
     * @throws SnoozeTypeException Exception caught when the task is not a deadline task
     */
    public void execute(TaskList tasks, Ui ui , Storage storage, Parser parser) throws NonExistentTaskException, SnoozeTypeException {
        int index = Integer.parseInt(user.substring(7)) - 1;
        if (index > tasks.size() - 1 || index < 0) {
            throw new NonExistentTaskException(ui);
        }
        else { // the tasks exist
            Task snoozeTask = tasks.get(index);
            if (!(snoozeTask instanceof DeadlinesTask)){
                throw new SnoozeTypeException(ui);
            }
            DeadlinesTask snoozeDeadlineTask = (DeadlinesTask) snoozeTask;
            snoozeDeadlineTask.snoozeDeadline();
            String text = storage.getSnoozeTaskString(snoozeDeadlineTask,index,ui,tasks.size());
            //rewriter of file by replacing the whole file
            storage.rewriteFile(text,ui);
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


}
