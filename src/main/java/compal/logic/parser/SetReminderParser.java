package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.SetReminderCommand;
import compal.logic.parser.exceptions.ParserException;

public class SetReminderParser implements CommandParser {

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        int taskID = getTokenTaskID(restOfInput);
        String status = getTokenStatus(restOfInput);
        return new SetReminderCommand(taskID, status);
    }
}
