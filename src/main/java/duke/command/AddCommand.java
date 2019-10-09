package duke.command;

import duke.exceptions.ModScheduleException;
import duke.modules.Deadline;
import duke.modules.DoWithin;
import duke.modules.Events;
import duke.modules.FixedDurationTasks;
import duke.modules.RecurringTask;
import duke.modules.Task;
import duke.modules.Todo;
import duke.util.TaskList;
import duke.util.TimePeriod;
import duke.util.Ui;
import duke.util.Storage;
import duke.util.Reminder;

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
    public void execute(TaskList tasks, Ui ui, Storage store, Reminder reminder) throws ModScheduleException {
        if (task instanceof Todo || task instanceof RecurringTask || task instanceof FixedDurationTasks) {
            tasks.add(task);
        } else {
            checkForScheduleConflicts(tasks);
        }
        ui.addedTaskMsg();
        ui.printTask(task);
        ui.currentTaskListSizeMsg(tasks.getSize());
        store.writeData(tasks.getTasks());
        reminder.forceCheckReminder();
    }

    private void checkForScheduleConflicts(TaskList tasks) throws ModScheduleException {
        HashSet<LocalDateTime> dateTimeSet = new HashSet<>();
        HashSet<TimePeriod> timePeriodSet = new HashSet<>();
        for (Task temp : tasks.getTasks()) {
            if (temp instanceof Deadline
                    || temp instanceof Events
                    || temp instanceof  DoWithin) {
                timePeriodSet.add(temp.getPeriod());
            } else if (temp instanceof FixedDurationTasks) {
                FixedDurationTasks hold = (FixedDurationTasks) temp;
                dateTimeSet.add(hold.getTimePeriod());
            }
        }
        LocalDateTime taskDateTime = null;
        TimePeriod taskTimePeriod = null;
        if (task instanceof Deadline
                || task instanceof Events
                || task instanceof  DoWithin) {
            taskTimePeriod = task.getPeriod();
        } else if (task instanceof FixedDurationTasks) {
            FixedDurationTasks hold = (FixedDurationTasks) task;
            taskDateTime = hold.getTimePeriod();
        }
        if (taskTimePeriod == null) {
            if (dateTimeSet.contains(taskDateTime)) {
                throw new ModScheduleException();
            }
            for (TimePeriod timePeriod : timePeriodSet) {
                if (timePeriod.isClashing(taskDateTime)) {
                    throw new ModScheduleException();
                }
            }
        } else {
            if (timePeriodSet.contains(taskTimePeriod)) {
                throw new ModScheduleException();
            }
            for (LocalDateTime dateTime : dateTimeSet) {
                if (taskTimePeriod.isClashing(dateTime)) {
                    throw new ModScheduleException();
                }
            }
            for (TimePeriod timePeriod : timePeriodSet) {
                if (taskTimePeriod.isClashing(timePeriod)) {
                    throw new ModScheduleException();
                }
            }
        }
        tasks.add(task);
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
