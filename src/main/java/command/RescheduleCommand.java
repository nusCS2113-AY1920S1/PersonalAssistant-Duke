package command;

import exception.DukeException;
import parser.CommandParams;
import storage.Storage;
import task.TaskList;
import ui.Ui;

public class RescheduleCommand extends Command {

    public RescheduleCommand() {
        super(null, null, null, null);
    }

    /**
     * Reschedules a command in the task list.
     *
     * @param tasks The taskList of Duke.
     * @param ui The ui of Duke.
     * @param storage The storage of Duke.
     * @throws DukeException if the task cannot be found, or does not contain
     */
    public void execute(CommandParams commandParams, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (commandParams.getMainParam() == null) {
            throw new DukeException("☹ OOPS!!! I don't know which task to reschedule!");
        }
        try {
            int index = Integer.parseInt(commandParams.getMainParam()) - 1; // 0 - based
            if (commandParams.containsParams("by")) {
                tasks.reschedule(index, commandParams.getParam("by"));
            } else {
                tasks.reschedule(index, commandParams.getParam("start"),
                        commandParams.getParam("end"));
            }
        } catch (NumberFormatException e) {
            throw new DukeException("☹ OOPS!!! The index be a number.");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("☹ OOPS!!! The index should be in range.");
        }
        storage.update(tasks.toStorageStrings());

        ui.println("The task has been rescheduled.");
    }

}
