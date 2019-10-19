package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.ViewCommand;
import compal.logic.parser.exceptions.ParserException;

/**
 * Parses input arguments and creates a new ViewCommand object.
 */
public class ViewCommandParser implements CommandParser {

    public static final String MESSAGE_MISSING_DATE = "Missing date input.";
    public static final String MESSAGE_INVALID_PARAM = "Invalid parameter for view command.";


    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     *
     * @param cmdParam parameter of the input string
     * @return the prepared command
     */
    @Override
    public Command parseCommand(String cmdParam) throws ParserException {
        String[] viewArgs = cmdParam.trim().split(" ");
        String emptyString = "";
        String viewType = viewArgs[0];
        String dateInput;

        if (emptyString.equals(viewType)) {
            throw new ParserException(MESSAGE_MISSING_TOKEN);
        }

        switch (viewType) {
        case "/month":
        case "/week":
        case "/day":
            try {
                dateInput = viewArgs[1];
            } catch (Exception e) {
                throw new ParserException(MESSAGE_MISSING_DATE);
            }

            isDateValid(dateInput);

            if (viewArgs.length == 2) {
                return new ViewCommand(viewArgs);
            }
            break;
        default:
            break;
        }

        throw new ParserException(MESSAGE_INVALID_PARAM);
    }
}
