package Parser;

import Commands.Command;
import Commands.RetrieveFreeTimesCommand;
import DukeExceptions.DukeInvalidFormatException;

/**
 * This class parses the full command that calls for RetrieveFreeTimesParse.
 */
public class RetrieveFreeTimesParse extends Parse {
    private String fullCommand;

    /**
     * Creates RetrieveFreeTimesParse object.
     * @param fullCommand The input by the user
     */
    public RetrieveFreeTimesParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException {
        fullCommand = fullCommand.replaceFirst("retrieve/ft ", "");
        fullCommand = fullCommand.trim();
        if(fullCommand.isEmpty()){
            throw new DukeInvalidFormatException("Invalid input. Please enter the command as follows. \n" +
                    "Find 'x' hours , where 'x' is a digit");
        } else {
            Integer duration = Integer.parseInt(fullCommand);
            return new RetrieveFreeTimesCommand(duration);
        }
    }
}

