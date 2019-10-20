package planner.command;

import planner.exceptions.ModScheduleException;

import planner.modules.Task;
import planner.modules.TaskWithInterval;
import planner.modules.TaskWithPeriod;
import planner.modules.TaskWithoutTime;
import planner.util.Reminder;
import planner.util.Storage;
import planner.util.TaskList;
import planner.util.TimePeriod;
import planner.util.Ui;
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
        if (task instanceof TaskWithoutTime || task instanceof TaskWithInterval) {
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
        if (task instanceof TaskWithPeriod) {
            TimePeriod taskTimePeriod = ((TaskWithPeriod)task).getPeriod();
            HashSet<TimePeriod> timePeriods = new HashSet<>();
            for (Task temp : tasks.getTasks()) {
                if (temp instanceof TaskWithPeriod) {
                    timePeriods.add(((TaskWithPeriod) temp).getPeriod());
                }
            }
            for (TimePeriod timePeriod : timePeriods) {
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
