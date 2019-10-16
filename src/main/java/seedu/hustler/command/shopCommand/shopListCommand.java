package seedu.hustler.command.shopCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import java.io.IOException;

/**
 * Command to execute the listing of items in the shop.
 */
public class shopListCommand extends Command {

    @Override
    public void execute() throws IOException {
        Hustler.shopList.list();
    }
}
