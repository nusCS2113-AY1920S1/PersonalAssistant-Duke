package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;

public class ByeCommand extends Command implements CommandParser {

    public ByeCommand(Duke d) {
        super(d);
    }

    @Override
    public void Command(String userIn) {
        System.exit(0);
    }
}
