package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.ListCommand;
import compal.logic.parser.exceptions.ParseException;
import compal.model.tasks.Task;

import java.util.ArrayList;

public class ListCommandParser implements CommandParser {
    @Override
    public Command parseCommand(String restOfUserInput) throws ParseException {
        return new ListCommand();
    }
}
