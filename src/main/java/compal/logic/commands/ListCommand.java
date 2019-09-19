package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;

public class ListCommand extends Command implements CommandParser {

    public ListCommand(Duke d) {
        super(d);
    }

    @Override
    public void Command(String userIn) {
        duke.ui.listTasks();
    }
}
