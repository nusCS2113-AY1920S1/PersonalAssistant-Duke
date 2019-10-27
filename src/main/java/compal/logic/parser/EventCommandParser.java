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
        String description = getTokenDescription(restOfInput);
        ArrayList<String> startDateList = getTokenDate(restOfInput);
        Task.Priority priority = getTokenPriority(restOfInput);
        String startTime = getTokenStartTime(restOfInput);
        String endTime = getTokenEndTime(restOfInput);
        int interval = getTokenInterval(restOfInput);
        String finalDate;
        if (hasToken(restOfInput, TOKEN_FINAL_DATE)) {
            finalDate = getTokenFinalDate(restOfInput);
        } else {
            int lastStartDateIndex = startDateList.size() - 1;
            finalDate = startDateList.get(lastStartDateIndex);
        }
        return new EventCommand(description, startDateList, priority, startTime, endTime, finalDate, interval);
    }
}
