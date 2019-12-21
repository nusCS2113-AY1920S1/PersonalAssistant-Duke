package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.DeadlineCommand;
import compal.logic.command.NusmodsCommand;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class NusmodsCommandParser implements CommandParser {
    private static final Logger logger = LogUtils.getLogger(NusmodsCommand.class);
    private static final ArrayList<String> key = new ArrayList<>(Arrays.asList(TOKEN_SEMSTART, TOKEN_NUSMODS_LINK));
    public static final String MESSAGE_INVALID_PARAM = "Whoops! Looks like that's an invalid command!\n"
            + "This is how you use the NUSMODS command:\n\n" + NusmodsCommand.MESSAGE_USAGE;

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse NUSMODS command");
        isValidKey(key, restOfInput,MESSAGE_INVALID_PARAM);
        String semStartDateString = getTokenSemStart(restOfInput);
        ArrayList<String> moduleDetailsList = getTokenNusmodsLink(restOfInput);
        logger.info("Successfully parse deadline command");
        return new NusmodsCommand(semStartDateString, moduleDetailsList);
    }
}
