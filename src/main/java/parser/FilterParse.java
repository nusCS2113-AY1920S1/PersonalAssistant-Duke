package parser;

import commands.Command;
import commands.FilterCommand;
import commons.DukeConstants;
import commons.DukeLogger;
import dukeexceptions.DukeInvalidCommandException;
import dukeexceptions.DukeInvalidFormatException;
import java.util.logging.Logger;

/**
 * This class parses the full command that calls for FilterParse.
 */
public class FilterParse extends Parse {
    private static String fullCommand;
    private final Logger logger = DukeLogger.getLogger(FilterParse.class);

    /**
     * Creates FilterParse object.
     * @param fullCommand The entire command that calls for AddParse.
     */
    public FilterParse(String fullCommand) {
        this.fullCommand = fullCommand;

    }

    /**
     * This executes the data processing for FilterParse.
     * @return Command which represents the parsed FilterCommand
     * @throws Exception Returned if command does not adhere to format
     */
    @Override
    public Command parse() throws DukeInvalidFormatException, DukeInvalidCommandException {
        if (fullCommand.trim().startsWith(DukeConstants.SHOW_FILTER_HEADER)) {
            try {
                String keyword = fullCommand.trim().replaceFirst(DukeConstants.SHOW_FILTER_HEADER,
                        DukeConstants.NO_FIELD);
                if (keyword.trim().equals(DukeConstants.NO_FIELD)) {
                    throw new DukeInvalidFormatException(DukeConstants.SHOW_FILTER_FORMAT);
                } else {
                    return new FilterCommand(keyword);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                logger.severe("Invalid format for filter");
                throw new DukeInvalidFormatException(DukeConstants.SHOW_FILTER_FORMAT);
            }
        } else {
            throw new DukeInvalidCommandException(DukeConstants.SAD_FACE + DukeConstants.UNKNOWN_MEANING);
        }
    }
}
