package duke.command;

import duke.exceptions.DukeScheduleException;
import duke.tasks.Deadline;
import duke.tasks.DoWithin;
import duke.tasks.Events;
import duke.tasks.FixedDurationTasks;
import duke.tasks.RecurringTask;
import duke.tasks.Task;
import duke.tasks.Todo;
import duke.util.Reminder;
import duke.util.TaskList;
import duke.util.TimePeriod;
import duke.util.Ui;
import duke.util.Storage;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;


public class AddCommand extends Command {

    private Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    private Task getTask() {
        return task;
    }

    /**
     * Takes in TaskList, Ui and Storage objects which then adds
     * a new task at the end of the TaskList.
     * @param tasks TaskList object containing current active taskList.
     * @param ui Ui object containing all output methods to user.
     * @param store Storage object which updates stored data.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage store, Reminder reminder) throws DukeScheduleException {
        if (task instanceof Todo || task instanceof RecurringTask || task instanceof FixedDurationTasks) {
            tasks.add(task);
        } else {
            HashSet<LocalDateTime> dateTimeSet = new HashSet<>();
            HashSet<TimePeriod> timePeriodSet = new HashSet<>();
            for (Task temp : tasks.getTasks()) {
                if (temp instanceof Deadline) {
                    Deadline hold = (Deadline) temp;
                    dateTimeSet.add(hold.getDateTime());
                } else if (temp instanceof Events) {
                    Events hold = (Events) temp;
                    dateTimeSet.add(hold.getDateTime());
                }  else if (temp instanceof DoWithin) {
                    DoWithin hold = (DoWithin) temp;
                    timePeriodSet.add(hold.getPeriod());
                }
            }
            LocalDateTime taskDateTime = null;
            TimePeriod taskTimePeriod = null;
            if (task instanceof Deadline) {
                Deadline hold = (Deadline) task;
                taskDateTime = hold.getDateTime();
            } else if (task instanceof Events) {
                Events hold = (Events) task;
                taskDateTime = hold.getDateTime();
            } else if (task instanceof DoWithin) {
                DoWithin hold = (DoWithin) task;
                taskTimePeriod = hold.getPeriod();
            }
            if (taskTimePeriod == null) {
                if (dateTimeSet.contains(taskDateTime)) {
                    throw new DukeScheduleException();
                }
                for (TimePeriod timePeriod : timePeriodSet) {
                    if (timePeriod.isClashing(taskDateTime)) {
                        throw new DukeScheduleException();
                    }
                }
            } else {
                if (timePeriodSet.contains(taskTimePeriod)) {
                    throw new DukeScheduleException();
                }
                for (LocalDateTime dateTime : dateTimeSet) {
                    if (taskTimePeriod.isClashing(dateTime)) {
                        throw new DukeScheduleException();
                    }
                }
                for (TimePeriod timePeriod : timePeriodSet) {
                    if (taskTimePeriod.isClashing(timePeriod)) {
                        throw new DukeScheduleException();
                    }
                }
            }
            tasks.add(task);
        }
        ui.addedTaskMsg();
        ui.printTask(task);
        ui.currentTaskListSizeMsg(tasks.getSize());
        store.writeData(tasks.getTasks());
        reminder.forceCheckReminder();
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
        if (!(obj instanceof AddCommand)) {
            return false;
        }
        AddCommand otherCommand = (AddCommand) obj;
        return otherCommand.getTask() == otherCommand.getTask();
    }

    @Override
    public int hashCode() {
        return Objects.hash(task);
    }
}
