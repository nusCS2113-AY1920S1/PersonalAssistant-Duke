package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;

import java.text.ParseException;

public class AddCommand extends Command implements CommandParser {

    public AddCommand(Duke d) {
        super(d);
    }

    @Override
    public void Command(String userIn) throws ParseException {
        duke.tasklist.addTask(userIn);
    }
}
