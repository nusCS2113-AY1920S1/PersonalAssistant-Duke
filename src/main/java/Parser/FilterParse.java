package Parser;

import Commands.Command;
import Commands.FilterCommand;
import Commons.DukeConstants;
import Commons.DukeLogger;
import DukeExceptions.DukeInvalidCommandException;
import DukeExceptions.DukeInvalidFormatException;
import java.util.logging.Logger;

public class FilterParse extends Parse{
    private static String fullCommand;
    private final Logger LOGGER = DukeLogger.getLogger(FilterParse.class);

    /**
     * Creates FilterParse object.
     * @param fullCommand The entire command that calls for AddParse.
     */
    public FilterParse(String fullCommand)  {
        this.fullCommand = fullCommand;

    }
    /**
     * @return Command which represents the parsed FilterCommand
     * @throws Exception Returned if command does not adhere to format
     */
    @Override
    public Command parse() throws DukeInvalidFormatException, DukeInvalidCommandException {
        if(fullCommand.trim().startsWith(DukeConstants.SHOW_FILTER_HEADER)){
           try {
               String keyword = fullCommand.trim().replaceFirst(DukeConstants.SHOW_FILTER_HEADER, "");
               if(keyword.trim().equals(DukeConstants.EMPTY_ERROR)){
                   throw new DukeInvalidFormatException(DukeConstants.SHOW_FILTER_FORMAT );
               }
               else {
                   return new FilterCommand(keyword);
               }
           }catch (ArrayIndexOutOfBoundsException e){
               LOGGER.severe("Invalid format for filter" + e.getMessage());
               throw new DukeInvalidFormatException(DukeConstants.SHOW_FILTER_FORMAT);
           }
        }
        else {
            throw new DukeInvalidCommandException(DukeConstants.SAD_FACE + DukeConstants.UNKNOWN_MEANING);

        }
    }
}
