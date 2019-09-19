package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;

public class DoneCommand extends Command implements CommandParser {

    public DoneCommand(Duke d) {
        super(d);
    }

    @Override
    public void Command(String userIn) {
        duke.tasklist.taskDone(userIn);
    }
}
