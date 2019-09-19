package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;

public class DeleteCommand extends Command implements CommandParser {

    public DeleteCommand(Duke d) {
        super(d);
    }

    @Override
    public void Command(String userIn) {
        duke.tasklist.deleteTask(userIn);
    }
}
