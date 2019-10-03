package eggventory.commands;

import eggventory.TaskList;
import eggventory.Ui;
import eggventory.Storage;
import eggventory.enums.CommandType;

public class DoneCommand extends Command {
    private int itemIndex;

    public DoneCommand(CommandType type, int index) {
        super(type);
        this.itemIndex = index - 1;
    }

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        try {
            list.getTask(itemIndex).markAsDone();
            ui.print("Nice! I've marked this task as done:\n"
                    + list.getTask(itemIndex).toString());
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("That task doesn't exist! Please check"
                    + " the available tasks again: ");
        }
    }
}
