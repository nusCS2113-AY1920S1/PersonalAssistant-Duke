package seedu.hustler.command.shopCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;

import java.io.IOException;

public class shopListCommand extends Command {


    public shopListCommand() {

    }
    @Override
    public void execute() throws IOException {
        Hustler.shopList.list();
    }
}
