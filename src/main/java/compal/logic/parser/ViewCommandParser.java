package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.ListCommand;
import compal.logic.command.ViewCommand;
import compal.logic.parser.exceptions.ParserException;

public class ViewCommandParser implements CommandParser {

    private static final String MESSAGE_MISSING_DATE = "Missing date.";

    private static final String MESSAGE_INVALID_PARAM = "Invalid parameter for view command.";
    private static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid Date format!";

    @Override
    public Command parseCommand(String cmdParam) throws ParserException {

        String[] viewArgs = cmdParam.trim().split(" ");
        String viewType = viewArgs[0];
        String dateInput = "";

        try {
            dateInput = viewArgs[1];
        } catch (Exception e) {
            throw new ParserException(MESSAGE_MISSING_DATE);
        }

        if (!isValidInputDate(dateInput)) {
            throw new ParserException(MESSAGE_INVALID_DATE_FORMAT);
        }

        switch (viewType) {
        case "/month":
        case "/week":
        case "/day":
            if (viewArgs.length == 2) {
                return new ViewCommand(viewArgs);
            }
            break;
        default:
            throw new ParserException(MESSAGE_INVALID_PARAM);
        }

        return new ListCommand();
    }
}
