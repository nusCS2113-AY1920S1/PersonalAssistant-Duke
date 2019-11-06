package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.ExportCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

public class ExportCommandParser implements CommandParser {
    public static final String MESSAGE_INVALID_PARAM = "Looks like there's an invalid parameter inserted!\n"
        + "This is how you use the export command:\n\n" + ExportCommand.MESSAGE_USAGE;

    private static final Logger logger = LogUtils.getLogger(ExportCommandParser.class);


    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse Export command");

        String[] args = restOfInput.split(" ");

        if (args.length > 2) {
            throw new ParserException(MESSAGE_INVALID_PARAM);
        }
        String fileName = getFileName(restOfInput);

        logger.info("Successfully parse Export command");
        return new ExportCommand(fileName);
    }
}
