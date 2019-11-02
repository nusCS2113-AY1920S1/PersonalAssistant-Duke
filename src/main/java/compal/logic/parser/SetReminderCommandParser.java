package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.SetReminderCommand;
import compal.logic.parser.exceptions.ParserException;

//@@author Catherinetan99
public class SetReminderCommandParser implements CommandParser {

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        int taskID = getTaskID(restOfInput);
        String status = getTokenStatus(restOfInput);
        return new SetReminderCommand(taskID, status);
    }
}
