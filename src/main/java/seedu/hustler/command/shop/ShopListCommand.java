package seedu.hustler.command.shop;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;

/**
 * Command to execute the listing of items in the shop.
 */
public class ShopListCommand extends Command {
    /**
     * Lists the items in the shop.
     */
    public void execute() {
        Hustler.shopList.list();
    }
}
