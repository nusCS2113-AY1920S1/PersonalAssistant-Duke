package duke.command;

import duke.exceptions.DukeInvalidTimeException;
import duke.exceptions.DukeNoTimeException;
import duke.tasks.Deadline;
import duke.tasks.Events;
import duke.tasks.Task;
import duke.util.DateTimeParser;
import duke.util.TaskList;
import duke.util.Storage;
import duke.util.Ui;
import duke.exceptions.DukeInvalidIndexException;

import java.util.Objects;

public class RescheduleCommand extends Command {

    private int index;
    private String time;

    public RescheduleCommand(int index, String time) {
        this.index = index - 1;
        this.time = time;
    }

    private int getIndex() {
        return index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage store) throws
            DukeInvalidIndexException,
            DukeInvalidTimeException,
            DukeNoTimeException {
        if (index >= tasks.getTasks().size() || index < 0) {
            throw new DukeInvalidIndexException();
        }
        else {
            Task task = tasks.access(index);
            if (task instanceof Deadline) {
                ((Deadline)task).setDateTime(DateTimeParser.getStringToDate(this.time));
                ui.rescheduleTaskMsg(task, this.time);
            }
            else if (task instanceof Events) {
                ((Events)task).setDateTime(DateTimeParser.getStringToDate(this.time));
                ui.rescheduleTaskMsg(task, this.time);
            }
            else {
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
        RescheduleCommand otherCommand = (RescheduleCommand) obj;
        return otherCommand.getIndex() == otherCommand.getIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
