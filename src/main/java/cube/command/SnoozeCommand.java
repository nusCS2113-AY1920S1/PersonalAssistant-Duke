//@@author LL-Pengfei
/**
 * Snooze the task for 24 hours.
 * Task identified by its sequence number.
 */

package cube.command;

import cube.exception.DukeException;
import cube.task.Task;
import cube.task.TaskList;
import cube.ui.Message;
import cube.ui.Ui;
import cube.util.Storage;

import java.util.Date;

public class SnoozeCommand implements Command {
    private String number;
    public SnoozeCommand(String number) {
        this.number = number;
    }

    private boolean isValid(TaskList list) throws DukeException {
        if (number == null) {
            throw new DukeException(Message.EMPTY_TASK_NUMBER);
        }
        try {
            int taskNumber = Integer.parseInt(number);
            if (taskNumber > list.size() || taskNumber <= 0) {
                throw new DukeException(Message.INVALID_TASK_NUMBER);
            }
        } catch (NumberFormatException e) {
            throw new DukeException(Message.INVALID_TASK_NUMBER);
        }
        return true;
    }

    /**
     * Always returns false since this is not an exit command.
     *
     * @return false.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Implement Snooze of a Task for 24 hours.
     *
     * @param tasks the list of tasks.
     * @param ui the user interface to output message.
     * @param storage storage of Duke.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException{
        if (isValid(tasks)) {
            int taskIndex = Integer.parseInt(number) - 1;
            Task t = tasks.get(taskIndex);
            //snooze it
            Date task_date = t.getDate();
            task_date.setTime(task_date.getTime()+24*60*60*1000);
            storage.save(tasks);
        }
    }
}
