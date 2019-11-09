package compal.logic.parser;

import compal.logic.command.Command;
import compal.logic.command.DeleteCommand;
import compal.logic.command.EventCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.ArrayList;
import java.util.Arrays;

//@@author yueyeah
/**
 * Parses input arguments and creates a new DeleteCommand object for deletion of task.
 */
public class DeleteCommandParser implements CommandParser {
    private static final ArrayList<String> key = new ArrayList<>(Arrays.asList(TOKEN_TASK_ID));
    public static final String MESSAGE_INVALID_PARAM = "Whoops! Looks like there's an invalid parameter inserted!\n"
            + "This is how you use the event command:\n\n" + EventCommand.MESSAGE_USAGE;

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        int taskID = getTaskID(restOfInput);
        isValidKey(key,restOfInput,MESSAGE_INVALID_PARAM);
        return new DeleteCommand(taskID);
    }
}
