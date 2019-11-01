package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.DeadlineCommand;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Task;

import java.util.ArrayList;
import java.util.logging.Logger;

//@@author LTPZ
//@@author yueyeah
/**
 * Command parser that parses arguments given by the user when adding deadline or recurring deadline.
 */
public class DeadlineCommandParser implements CommandParser {
    private static final Logger logger = LogUtils.getLogger(DeadlineCommand.class);

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse deadline command");
        String description = getTokenDescription(restOfInput);
        ArrayList<String> startDateList = getTokenDate(restOfInput);
        Task.Priority priority = getTokenPriority(restOfInput);
        String endTime = getTokenEndTime(restOfInput);
        int interval = getTokenInterval(restOfInput);
        String finalDate;
        if (hasToken(restOfInput, TOKEN_FINAL_DATE)) {
            finalDate = getTokenFinalDate(restOfInput);
        } else {
            int lastStartDateIndex = startDateList.size() - 1;
            finalDate = startDateList.get(lastStartDateIndex);
        }
        logger.info("Successfully parse deadline command");
        return new DeadlineCommand(description, priority, startDateList, endTime, finalDate, interval);
    }
}
