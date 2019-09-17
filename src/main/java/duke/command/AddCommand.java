package duke.command;

import duke.exceptions.DukeScheduleException;
import duke.tasks.Deadline;
import duke.tasks.Events;
import duke.tasks.Task;
import duke.util.TaskList;
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
    public void execute(TaskList tasks, Ui ui, Storage store) throws DukeScheduleException {
        if (task.getClass().toString().equals("class duke.tasks.Todo")) {
            tasks.add(task);
        } else {
            HashSet<LocalDateTime> dateTimeSet = new HashSet<>();
            for (Task temp : tasks.getTasks()) {
                if (temp.getClass().toString().equals("class duke.tasks.Deadline")) {
                    Deadline hold = (Deadline) temp;
                    dateTimeSet.add(hold.getDateTime());
                } else if (temp.getClass().toString().equals("class duke.tasks.Events")) {
                    Events hold = (Events) temp;
                    dateTimeSet.add(hold.getDateTime());
                }
            }
            LocalDateTime taskDateTime;
            if (task.getClass().toString().equals("class duke.tasks.Deadline")) {
                Deadline hold = (Deadline) task;
                taskDateTime = hold.getDateTime();
            } else {
                Events hold = (Events) task;
                taskDateTime = hold.getDateTime();
            }
            if (dateTimeSet.contains(taskDateTime)) {
                throw new DukeScheduleException();
            }
            tasks.add(task);
        }
        ui.addedTaskMsg();
        ui.printTask(task);
        ui.currentTaskListSizeMsg(tasks.getSize());
        store.writeData(tasks.getTasks());
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
