package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.ViewCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.ArrayList;

import static compal.commons.CompalUtils.dateToString;
import static compal.commons.CompalUtils.stringToDate;

/**
 * Parses input arguments and creates a new ViewCommand object.
 */
public class ViewCommandParser implements CommandParser {
    public static final String MESSAGE_INVALID_PARAM = "Invalid parameter for view command.";
    public static final String MESSAGE_MISSING_DATE_INPUT = "Error: Missing date input";

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     *
     * @param restOfInput parameter of the input string
     * @return the prepared command
     */
    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        String[] viewArgs = restOfInput.trim().split(" ");

        String emptyString = "";
        String viewType = viewArgs[0];
        if (emptyString.equals(viewType)) {
            throw new ParserException(MESSAGE_MISSING_TOKEN);
        }

        switch (viewType) {
        case "/month":
        case "/week":
        case "/day":

            ArrayList<String> startDateList = getTokenDate(restOfInput);
            int lastStartDateIndex = startDateList.size() - 1;

            String finalDate;
            finalDate = startDateList.get(lastStartDateIndex);

            //System.out.println(viewArgs.length);

            if (viewArgs.length == 3) {
                return new ViewCommand(viewType, finalDate);
            } else {
                String type = getType(restOfInput);
                return new ViewCommand(viewType, finalDate, type);
            }


        default:
            break;
        }

        throw new ParserException(MESSAGE_INVALID_PARAM);
    }
}
