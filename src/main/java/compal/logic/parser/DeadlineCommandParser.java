package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.DeadlineCommand;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Task;

import java.text.ParseException;

import static compal.commons.Messages.MESSAGE_INVALID_DATE_TIME_INPUT;

public class DeadlineCommandParser implements CommandParser {

    @Override
    public Command parseCommand(String input) throws ParserException, ParseException {
        String description = getDescription(input);
        Task.Priority priority = getPriority(input);
        String date = getDate(input);
        String endTime = getEndTime(input);
        if (!isValidDateAndTime(date, endTime)) {
            throw new ParserException(MESSAGE_INVALID_DATE_TIME_INPUT);
        }
        return new DeadlineCommand(description, priority, date, endTime);
    }
}
