package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
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
    private static final Logger logger = LogUtils.getLogger(EventCommand.class);
    private static final ArrayList<String> key = new ArrayList<>(Arrays.asList(TOKEN_END_TIME, TOKEN_START_TIME,
            TOKEN_DATE, TOKEN_PRIORITY, TOKEN_FINAL_DATE, TOKEN_INTERVAL));

    public static final String MESSAGE_INVALID_PARAM = "Whoops! Looks like that's an invalid command!\n"
        + "This is how you use the event command:\n\n" + EventCommand.MESSAGE_USAGE;

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
        isValidAmountTaskToAdd(startDateList.get(0),finalDate,interval);
        isValidKey(key, restOfInput,MESSAGE_INVALID_PARAM);
        logger.info("Successfully parse event command");
        return new EventCommand(description, startDateList, priority, startTime, endTime, finalDate, interval);
    }
}
