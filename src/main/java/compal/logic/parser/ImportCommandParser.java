package compal.logic.parser;

import compal.commons.LogUtils;
import compal.logic.command.Command;
import compal.logic.command.ImportCommand;
import compal.logic.parser.exceptions.ParserException;

import java.util.logging.Logger;

public class ImportCommandParser implements CommandParser {
    private static final Logger logger = LogUtils.getLogger(ImportCommandParser.class);

    @Override
    public Command parseCommand(String restOfInput) throws ParserException {
        logger.info("Attempting to parse Import command");
        String fileName = getFileName(restOfInput);
        logger.info("Successfully parse import command");
        return new ImportCommand(fileName);
    }

}
