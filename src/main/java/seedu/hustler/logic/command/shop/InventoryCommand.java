package seedu.hustler.command.shop;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.game.avatar.Inventory;

public class InventoryCommand extends Command {
    public void execute() {
        Hustler.inventory.list();
    }
}
