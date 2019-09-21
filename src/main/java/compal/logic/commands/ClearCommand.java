package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;

public class ClearCommand extends Command implements CommandParser {

    public ClearCommand(Duke d) {
        super(d);
    }

    @Override
    public void Command(String userIn) {
        duke.ui.clearPrimary();
    }
}
