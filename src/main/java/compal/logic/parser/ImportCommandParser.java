package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.ImportCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

public class ImportCommandParser implements CommandParser {
    public static final String MESSAGE_INVALID_PARAM = "Looks like there's an invalid parameter inserted!\n"
        + "This is how you use the import command:\n\n" + ImportCommand.MESSAGE_USAGE;
    private static final Logger logger = LogUtils.getLogger(ImportCommandParser.class);

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse Import command");

        String[] args = restOfInput.split(" ");

        if (args.length > 2) {
            throw new ParserException(MESSAGE_INVALID_PARAM);
        }

        String fileName = getFileName(restOfInput);

        logger.info("Successfully parse import command");
        return new ImportCommand(fileName);
    }

}
