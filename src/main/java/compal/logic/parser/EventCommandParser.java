package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.EventCommand;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Task;

import java.util.ArrayList;

//@@author yueyeah
/**
 * Command parser that parses arguments given by the user when adding event or recurring events.
 */
public class EventCommandParser implements CommandParser {
    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        final String description = getTokenDescription(restOfInput);
        final ArrayList<String> startDateList = getTokenDate(restOfInput);
        final Task.Priority priority = getTokenPriority(restOfInput);
        final String startTime = getTokenStartTime(restOfInput);
        final String endTime = getTokenEndTime(restOfInput);
        final int interval = getTokenInterval(restOfInput);
        String finalDate;
        if (hasToken(restOfInput, TOKEN_FINAL_DATE)) {
            finalDate = getTokenFinalDate(restOfInput);
        } else {
            int lastStartDateIndex = startDateList.size() - 1;
            finalDate = startDateList.get(lastStartDateIndex);
        }
        isFinalDateAfterStartDate(startDateList.get(INDEX_ZERO), finalDate);
        isValidInterval(interval);
        return new EventCommand(description, startDateList, priority, startTime, endTime, finalDate, interval);
    }
}
