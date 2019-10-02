package duke.commands;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.enums.CommandType;

/**
 * Command objects for marking tasks as done, or deleting them.
 * Requires the index of the task.
 */
public class NumCommand extends Command {

    private int itemIndex;

    public NumCommand(CommandType type, int index) {
        super(type);
        this.itemIndex = index - 1; //Because of 0-indexing, user's request for item 1 means item 0.
    }

    public int getItemIndex() {
        return itemIndex;
    }

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        if (type == CommandType.SNOOZE) {
            /*list.snoozeTask(itemIndex);*/
        }
    }
}
