package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.SetReminderCommand;
import compal.logic.parser.exceptions.ParserException;

public class SetReminderParser implements CommandParser {

    @Override
    public Command parseCommand(String command) throws ParserException {
        int taskID = getTaskID(command);
        String status = getStatus(command);
        return new SetReminderCommand(taskID, status);
    }
}
