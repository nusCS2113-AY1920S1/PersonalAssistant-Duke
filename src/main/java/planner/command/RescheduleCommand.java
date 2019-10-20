package planner.command;

import planner.exceptions.ModInvalidTimeException;
import planner.exceptions.ModInvalidTimePeriodException;
import planner.exceptions.ModNoTimeException;
import planner.modules.Deadline;
import planner.modules.DoWithin;
import planner.modules.Events;
import planner.modules.Task;
import planner.exceptions.ModInvalidIndexException;

import planner.util.DateTimeParser;
import planner.util.Reminder;
import planner.util.Storage;
import planner.util.TaskList;
import planner.util.Ui;
import java.time.LocalDateTime;
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
     * @throws ModInvalidIndexException If user inputs invalid index.
     * @throws ModInvalidTimeException If user inputs invalid date/time.
     * @throws ModNoTimeException If user inputs time based tasks without any time.
     * @throws ModInvalidTimePeriodException If user inputs an invalid time period.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage store, Reminder reminder) throws
            ModInvalidIndexException,
            ModInvalidTimeException,
            ModNoTimeException,
            ModInvalidTimePeriodException {
        if (index >= tasks.getTasks().size() || index < 0) {
            throw new ModInvalidIndexException();
        } else {
            Task task = tasks.access(index);
            if (task instanceof Deadline) {
                ((Deadline)task).setPeriod(LocalDateTime.now(), DateTimeParser.getStringToDate(this.time));
                ui.rescheduleTaskMsg(task, this.time);
            } else if (task instanceof Events) {
                LocalDateTime dateTime = DateTimeParser.getStringToDate(this.time);
                ((Events)task).setPeriod(dateTime, dateTime);
                ui.rescheduleTaskMsg(task, this.time);
            } else if (task instanceof DoWithin) {
                ((DoWithin)task).setPeriod(DateTimeParser.getStringToDate(this.begin),
                        DateTimeParser.getStringToDate(this.end));
                ui.rescheduleTaskMsg(task, "between " + this.begin + " and " + this.end);
            } else {
                throw new ModNoTimeException();
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
