package compal.logic.parser;

import compal.commons.CompalUtils;
import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.ViewCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.logging.Logger;

//@@author SholihinK

/**
 * Parses input arguments and creates a new ViewCommand object.
 */
public class ViewCommandParser implements CommandParser {
    private static final ArrayList<String> key = new ArrayList<>(Arrays.asList(TOKEN_DATE, TOKEN_TYPE));
    public static final String MESSAGE_INVALID_PARAM = "Whoops! Looks like that's an invalid command!\n"
        + "This is how you use the view command:\n\n" + ViewCommand.MESSAGE_USAGE;
    private static final Logger logger = LogUtils.getLogger(ViewCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     *
     * @param restOfInput parameter of the input string
     * @return the prepared command
     */
    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse view command");
        String[] viewArgs = restOfInput.trim().split(" ");

        String emptyString = "";
        String viewType = viewArgs[0];
        if (emptyString.equals(viewType)) {
            throw new ParserException(MESSAGE_MISSING_TOKEN);
        }

        isValidKey(key, restOfInput, MESSAGE_INVALID_PARAM);

        switch (viewType) {
        case "month":
        case "week":
        case "day":

            String finalDate;

            if (!restOfInput.contains("/date")) {
                Calendar currentDay = Calendar.getInstance();
                finalDate = CompalUtils.dateToString(currentDay.getTime());
            } else {
                ArrayList<String> startDateList = getTokenDate(restOfInput);
                int lastStartDateIndex = startDateList.size() - 1;
                finalDate = startDateList.get(lastStartDateIndex);
            }

            logger.info("Successfully parse view command");
            if (viewArgs.length == 1) {
                return new ViewCommand(viewType, finalDate);
            } else if (viewArgs.length == 3) {
                if (restOfInput.contains("/type")) {
                    String type = getType(restOfInput);
                    return new ViewCommand(viewType, finalDate, type);
                } else if (restOfInput.contains("/date")) {
                    return new ViewCommand(viewType, finalDate);
                } else {
                   throw new ParserException(MESSAGE_INVALID_PARAM);
                }
            } else if (viewArgs.length == 5) {
                String type = getType(restOfInput);
                return new ViewCommand(viewType, finalDate, type);
            }
            break;
        default:
            break;
        }

        throw new ParserException(MESSAGE_INVALID_PARAM);
    }
}
