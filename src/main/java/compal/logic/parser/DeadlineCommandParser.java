package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.DeadlineCommand;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Task;

public class DeadlineCommandParser implements CommandParser {

    @Override
    public Command parseCommand(String input) throws ParserException {
        String description = getDescription(input);
        Task.Priority priority = getPriority(input);
        String date = getDate(input);
        String endTime = getEndTime(input);
        return new DeadlineCommand(description, priority, date, endTime);
    }
}
