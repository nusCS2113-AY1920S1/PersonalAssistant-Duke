package duke.command;

import duke.task.DukeException;
import duke.task.Storage;
import duke.task.TaskList;
import duke.task.Ui;
import duke.task.Deadline;
import duke.task.Event;

import java.time.LocalDateTime;

/**
 * Represents a command to snooze a command.
 */
public class SnoozeCommand extends Command {

    private int taskNumber;
    private LocalDateTime tillValue;

    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input given by the user
     */
    public SnoozeCommand(boolean isExit, String input, int taskNumber, LocalDateTime tillValue) {
        super(isExit, input);
        this.taskNumber = taskNumber;
        this.tillValue = tillValue;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        super.execute(taskList, ui, storage);
        if (TaskList.getTask(taskNumber).toString().contains("[D]")) {
            ((Deadline)TaskList.getTask(taskNumber)).setBy(tillValue);
        } else if (TaskList.getTask(taskNumber) instanceof Event) {
            ((Event)TaskList.getTask(taskNumber)).setAt(tillValue);
        } else {
            throw new DukeException("Cannot snooze a ToDo task");
        }
        ui.output = TaskList.getTask(taskNumber).toString();
    }
}
