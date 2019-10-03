package eggventory.commands;

import eggventory.StockType;
import eggventory.Ui;
import eggventory.Storage;
import eggventory.enums.CommandType;
import eggventory.items.Stock;

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
    public void execute(StockType list, Ui ui, Storage storage) throws IndexOutOfBoundsException {
        try {
            Stock item = list.getStock(itemIndex);
            list.deleteStock(itemIndex);
            ui.print("Okay! I've deleted this task:\n" + item.toString());

        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("That task doesn't exist! Please check"
                    + " the available tasks again: ");
        }
    }
}
