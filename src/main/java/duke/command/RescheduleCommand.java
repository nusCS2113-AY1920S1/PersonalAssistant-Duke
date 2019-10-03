package duke.command;

import duke.exceptions.DukeInvalidTimeException;
import duke.exceptions.DukeInvalidTimePeriodException;
import duke.exceptions.DukeNoTimeException;
import duke.tasks.Deadline;
import duke.tasks.DoWithin;
import duke.tasks.Events;
import duke.tasks.Task;
import duke.util.*;
import duke.exceptions.DukeInvalidIndexException;

import java.util.Objects;

public class RescheduleCommand extends Command {

    private int index;
    private String time;
    private String begin;
    private String end;

    /**
     * Overloaded constructor for Reschedule Command.
     * @param index of selected task.
     * @param time new time of selected task.
     */
    public RescheduleCommand(int index, String time) {
        this.index = index - 1;
        this.time = time;
    }

    /**
     * Overloaded constructor for Reschedule Command.
     * @param index of desired task to be Rescheduled.
     * @param begin New start time.
     * @param end New end time.
     */
    public RescheduleCommand(int index, String begin, String end) {
        this.index = index - 1;
        this.begin = begin;
        this.end = end;
    }

    private int getIndex() {
        return index;
    }

    /**
     * Execute the reschedule command.
     * @param tasks TaskList object containing current active taskList.
     * @param ui Ui object containing all output methods to user.
     * @param store Storage object which updates stored data.
     * @throws DukeInvalidIndexException If user inputs invalid index.
     * @throws DukeInvalidTimeException If user inputs invalid date/time.
     * @throws DukeNoTimeException If user inputs time based tasks without any time.
     * @throws DukeInvalidTimePeriodException If user inputs an invalid time period.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage store, Reminder reminder) throws
            DukeInvalidIndexException,
            DukeInvalidTimeException,
            DukeNoTimeException,
            DukeInvalidTimePeriodException {
        if (index >= tasks.getTasks().size() || index < 0) {
            throw new DukeInvalidIndexException();
        } else {
            Task task = tasks.access(index);
            if (task instanceof Deadline) {
                ((Deadline)task).setDateTime(DateTimeParser.getStringToDate(this.time));
                ui.rescheduleTaskMsg(task, this.time);
            } else if (task instanceof Events) {
                ((Events)task).setDateTime(DateTimeParser.getStringToDate(this.time));
                ui.rescheduleTaskMsg(task, this.time);
            } else if (task instanceof DoWithin) {
                ((DoWithin)task).setDateTime(DateTimeParser.getStringToDate(this.begin),
                        DateTimeParser.getStringToDate(this.end));
                ui.rescheduleTaskMsg(task, "between " + this.begin + " and " + this.end);
            } else {
                throw new DukeNoTimeException();
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RescheduleCommand)) {
            return false;
        }
        RescheduleCommand otherCommand;
        otherCommand = (RescheduleCommand) obj;
        return otherCommand.getIndex() == otherCommand.getIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(index,time,begin,end);
    }
}
