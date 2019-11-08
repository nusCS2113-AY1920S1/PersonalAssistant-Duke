package Parser;

import Commands.Command;
import Commands.FilterCommand;
import Commons.DukeLogger;
import DukeExceptions.DukeInvalidCommandException;
import DukeExceptions.DukeInvalidFormatException;

import java.text.ParseException;
import java.util.logging.Logger;

public class FilterParse extends Parse{
    private static String[] split;
    private static String[] split1;
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
     * @return Command which represents the parsed Filtercommand
     * @throws Exception Returned if command does not adhere to format
     */
    @Override
    public Command parse() throws DukeInvalidFormatException, DukeInvalidCommandException {
        if(fullCommand.trim().startsWith("show/filter")){
           try {
               String keyword = fullCommand.trim().substring(11);
               if(keyword.trim().equals("")){
                   throw new DukeInvalidFormatException(" OOPS!!! Please enter filter command as follows\n" +
                           "show/filter keyword\n");
               }
               else {
                   return new FilterCommand(keyword);
               }
           }catch (ArrayIndexOutOfBoundsException e){
               LOGGER.severe("Invalid format for filter" + e.getMessage());
               throw new DukeInvalidFormatException(" OOPS!!! Please enter filter command as follows\n" +
                       "show/filter keyword\n");
           }
        }
        else {
            throw new DukeInvalidCommandException("\u2639" + " OOPS!!! I'm sorry, but I don't know what that means :-(");

        }
    }
}
