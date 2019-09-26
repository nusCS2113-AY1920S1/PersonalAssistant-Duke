package duke.commands;

import duke.DateFormatter;
import duke.constant.DukeResponse;
import duke.Storage;
import duke.exception.DukeException;
import duke.task.Task;
import duke.task.TaskList;
import duke.Ui;

import java.time.LocalDateTime;

public class SnoozeCommand extends Command {
    private int index;
    private int value;
    private String units;
    /**
     * Creates SnoozeCommand with the specified index
     * to be snoozed. Index starts from 1.
     * @param index Index of task to be snoozed.
     * @param value Amount of units to snooze by
     * @param units Type of time units. e.g. month, day, minutes
     */
    public SnoozeCommand(int index, int value, String units) {
        this.index = index;
        this.value = value;
        this.units = units;
    }

    /**
     * Snooze the task specified by user. Sets message of Ui
     * to show if command is successfully carried out.
     * Can only snooze events and deadlines.
     * @param tasks The list of task stored by duke
     * @param ui The user interface that handles messages
     * @param storage The database to read files and write txt files
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        String message = "";
        if (index <= tasks.size() && index > 0) {
            Task task = tasks.getTask(index);
            // if the task is a todo then do not allow
            if (task.getSymbol().equals("[T]")) {
                message = new DukeResponse().WRONG_TASKTYPE;
            }
            String currDate = task.getDateTime();
            LocalDateTime currLocalDate = new DateFormatter(currDate).toLocalDateTime(currDate);
            try {
                // increment the time by (value) amount of (units).| units e.g. year, month, minute.| value e.g. 1,2,3.
                LocalDateTime newTime = new DateFormatter(currDate).changeDate(currLocalDate, value, units);
                // get toString of newTime, converted to format used in command line input i.e. dd/MM/yyyy HHmm
                String stringNewTime = new DateFormatter(currDate).formatLocalDateTime(newTime);
                // set the date in the task. for events it is the (at) param. for deadlines it is the (by) param.
                task.setDate(stringNewTime);
                tasks.set(index-1, task);
                message = new DukeResponse().SNOOZE_SUCCESS + task.toString() + "\n";
            } catch (DukeException e) {
                ui.setMessage(e.getMessage());;
            }
        } else {
            message = new DukeResponse().NOT_FOUND;
        }
        ui.setMessage(message);
    }
}
