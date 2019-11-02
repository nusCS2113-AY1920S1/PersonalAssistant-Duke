package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.ExportCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

public class ExportCommandParser implements CommandParser {
    private static final Logger logger = LogUtils.getLogger(ExportCommandParser.class);

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse Export command");
        String fileName = getFileName(restOfInput);
        logger.info("Successfully parse Export command");
        return new ExportCommand(fileName);
    }
}
