package command;

import exception.DukeException;
import parser.CommandParams;
import storage.Storage;
import task.Event;
import task.TaskList;
import ui.Ui;

public class ConflictCommand extends Command {


    /**
     * Constructs a {@code Command} object with commandType.
     */
    public ConflictCommand() {
        super(null, null, null, null);
    }

    /**
     * Checks if there are overlapping Events for a given index of an Event.
     * @param taskList The {@code TaskList} of Duke
     * @param ui The ui of Duke.
     * @param storage The storage of Duke.
     * @throws DukeException If the index given is out of range, invalid, does not exist, or not an Event class.
     */
    @Override
    public void execute(CommandParams commandParams, TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (commandParams.getMainParam() == null) {
            throw new DukeException("☹ OOPS!!! I don't know which task to search conflicts for!");
        } else {
            try {
                int index = Integer.parseInt(commandParams.getMainParam()) - 1; // 0-based
                if (taskList.getTasks().get(index) instanceof Event) {
                    Event event = (Event) taskList.getTasks().get(index);
                    TaskList overlappingTasks = event.overlappingWithOtherEvents(taskList);
                    if (overlappingTasks.getSize() > 1) {
                        ui.println("These are the overlapping tasks:");
                        ui.printTaskList(overlappingTasks);
                    } else {
                        ui.println("There are no overlapping tasks :D");
                    }
                } else {
                    throw new DukeException("☹ OOPS!!! The item you have selected is not an event!");
                }

            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("☹ OOPS!!! The index should be in range.");
            } catch (NumberFormatException e) {
                throw new DukeException("☹ OOPS!!! The index be a number.");
            }
        }
    }
}
