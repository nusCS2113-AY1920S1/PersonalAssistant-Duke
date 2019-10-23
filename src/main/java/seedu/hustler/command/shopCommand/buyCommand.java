package seedu.hustler.command.shopCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.data.ShopStorage;
import seedu.hustler.game.avatar.Inventory;

import java.io.IOException;

/**
 * Command to purchase an item in the shop with the given index.
 */
public class buyCommand extends Command {

    /**
     * The index of the item desired.
     */
    private int index;

    /**
     * Constructs a buyCommand with the index of the item.
     * @param index the index of the item to purchase.
     */
    public buyCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute() throws IOException {
        Hustler.shopList.buy(this.index - 1);
        ShopStorage.update();
        Hustler.inventory.updateInventory();
    }
}
