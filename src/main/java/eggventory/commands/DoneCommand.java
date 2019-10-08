package eggventory.commands;

import eggventory.StockType;
import eggventory.Ui;
import eggventory.Storage;
import eggventory.enums.CommandType;

/**
 * Currently not in use.
 * TODO: Modify this into the loan or lost command?
 */
public class DoneCommand extends Command {
    private int itemIndex;

    public DoneCommand(CommandType type, int index) {
        super(type);
        this.itemIndex = index - 1;
    }

    @Override
    public void execute(StockType list, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        try {
            //list.getStock(itemIndex).markAsDone();
            ui.print("Nice! I've marked this task as done:\n"
                    + list.getStock(itemIndex).toString());
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("That task doesn't exist! Please check"
                    + " the available tasks again: ");
        }
    }
}
