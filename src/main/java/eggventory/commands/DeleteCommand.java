package eggventory.commands;

import eggventory.items.Task;
import eggventory.TaskList;
import eggventory.Ui;
import eggventory.Storage;
import eggventory.enums.CommandType;

/**
 * Command objects for marking tasks as done, or deleting them.
 * Requires the index of the task.
 */
public class DeleteCommand extends Command {

    private int itemIndex;

    public DeleteCommand(CommandType type, int index) {
        super(type);
        this.itemIndex = index - 1;
    }

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        try {
            Task item = list.getTask(itemIndex);
            list.deleteTask(itemIndex);
            ui.print("Okay! I've deleted this task:\n" + item.toString());

        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("That task doesn't exist! Please check"
                    + " the available tasks again: ");
        }
    }
}
