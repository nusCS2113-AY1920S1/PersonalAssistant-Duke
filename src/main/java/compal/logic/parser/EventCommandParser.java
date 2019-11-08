package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.DeadlineCommand;
import compal.logic.command.EventCommand;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

//@@author yueyeah
/**
 * Command parser that parses arguments given by the user when adding event or recurring events.
 */
public class EventCommandParser implements CommandParser {
    private static final Logger logger = LogUtils.getLogger(DeadlineCommand.class);
    private static final ArrayList<String> key = new ArrayList<>(Arrays.asList(TOKEN_END_TIME, TOKEN_START_TIME,
            TOKEN_DATE, TOKEN_PRIORITY, TOKEN_FINAL_DATE, TOKEN_INTERVAL));

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse event command");
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
        isValidKey(key, restOfInput);
        logger.info("Successfully parse event command");
        return new EventCommand(description, startDateList, priority, startTime, endTime, finalDate, interval);
    }
}
