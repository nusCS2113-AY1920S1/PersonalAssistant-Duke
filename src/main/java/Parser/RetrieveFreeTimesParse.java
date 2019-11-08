package Parser;

import Commands.Command;
import Commands.FindFreeTimesCommand;
import Commands.RetrieveFreeTimesCommand;
import Commons.DukeConstants;
import Commons.DukeLogger;
import DukeExceptions.DukeInvalidFormatException;
import DukeExceptions.DukeNoValidDataException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This class parses the full command that calls for RetrieveFreeTimesParse.
 */
public class RetrieveFreeTimesParse extends Parse {
    private final Logger LOGGER = DukeLogger.getLogger(FindFreeTimesParse.class);
    private String fullCommand;
    private final String invalidOption = "Invalid option. Please enter the command as follows. \n" +
            "retrieve/time 'x', where 'x' is a digit between 1 - 5";
    private final String invalidEmptyOption = "Invalid input.\n" +
            "Option cannot be blank.\nPlease enter the command as follows.\n"
            + "retrieve/time 'x', where 'x' is a digit between 1 - 5";
    private final String invalidNoFreeTimeFound = "Please find free times by invoking the command shown below\n"
            + "find/time 'x' hours, where 'x' is a digit between 1 - 16\n"
            + "Followed by the command\n"
            + "retrieve/time 'x', where 'x' is a digit between 1- 5";

    /**
     * Creates RetrieveFreeTimesParse object.
     * @param fullCommand The input by the user
     */
    public RetrieveFreeTimesParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException, DukeNoValidDataException {
        ArrayList<Pair<String, String>> retrievedFreeTimes = FindFreeTimesCommand.getCompiledFreeTimesList();
        if (checkIsEmpty(retrievedFreeTimes)) throw new DukeNoValidDataException(invalidNoFreeTimeFound);
        fullCommand = fullCommand.replaceFirst(DukeConstants.RETRIEVE_TIME_HEADER, "");
        fullCommand = fullCommand.trim();
        if(fullCommand.isEmpty()){
            throw new DukeInvalidFormatException(invalidEmptyOption);
        } else {
            try {
                Integer option = Integer.parseInt(fullCommand);
                if (option >= DukeConstants.RETRIEVE_TIME_LOWER_BOUNDARY && option <= DukeConstants.RETRIEVE_TIME_UPPER_BOUNDARY) return new RetrieveFreeTimesCommand(option);
                else throw new DukeInvalidFormatException(invalidOption);
            } catch (NumberFormatException e) {
                LOGGER.info("Unable to parse string to integer" + e.getMessage());
                throw new DukeInvalidFormatException(invalidOption);
            }
        }
    }

    public static boolean isValidOption(String input) {
        input = input.replace(DukeConstants.RETRIEVE_TIME_HEADER, "");
        input = input.trim();
        if(input.isEmpty()){
            return false;
        } else {
            try {
                Integer option = Integer.parseInt(input);
                if (option >= DukeConstants.RETRIEVE_TIME_LOWER_BOUNDARY && option <= DukeConstants.RETRIEVE_TIME_UPPER_BOUNDARY) return true;
                else return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    private boolean checkIsEmpty (ArrayList<Pair<String, String>> retrievedFreeTimes){
        return (retrievedFreeTimes.size() == 0) ? true : false;
    }
}

