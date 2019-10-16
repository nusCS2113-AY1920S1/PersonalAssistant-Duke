package seedu.hustler.command.shopCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.data.ShopStorage;

import java.io.IOException;

public class buyCommand extends Command {

    private int index;

    public buyCommand(int index) {
        this.index = index;
    }


    @Override
    public void execute() throws IOException {
        Hustler.shopList.buy(this.index - 1);
        ShopStorage.update();
    }
}
