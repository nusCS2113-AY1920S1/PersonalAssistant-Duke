package Parser;

import Commands.Command;
import Commands.FindFreeTimesCommand;
import DukeExceptions.DukeInvalidFormatException;

/**
 * This class parses the full command that calls for FindFreeTimesParse.
 */
public class FindFreeTimesParse extends Parse {
    private String fullCommand;

    /**
     * Creates FindFreeTimesParse object.
     * @param fullCommand The input by the user
     */
    public FindFreeTimesParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException {
        fullCommand = fullCommand.replaceFirst("find", "");
        fullCommand = fullCommand.trim();
        fullCommand = fullCommand.replaceFirst("hours", "");
        fullCommand = fullCommand.trim();
        if(fullCommand.isEmpty()){
            throw new DukeInvalidFormatException("Invalid input. Please enter the command as follows. \n" +
                    "Find 'x' hours , where 'x' is a digit");
        } else {
            Integer duration = Integer.parseInt(fullCommand);
            return new FindFreeTimesCommand(duration);
        }
    }
}

