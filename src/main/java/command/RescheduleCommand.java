package command;

import exception.DukeException;
import parser.CommandParams;
import storage.Storage;
import task.TaskList;
import ui.Ui;

public class RescheduleCommand extends Command {

    public RescheduleCommand(CommandParams commandParams) {
        super(commandParams);
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (commandParams.getMainParam() == null) {
            throw new DukeException("☹ OOPS!!! I don't know which task to reschedule!");
        }
        try {
            int index = Integer.parseInt(commandParams.getMainParam()) - 1; // 0 - based
            if (commandParams.containsParam("by")) {
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
