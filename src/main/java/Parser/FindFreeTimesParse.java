package Parser;

import Commands.Command;
import Commands.FindFreeTimesCommand;
import DukeExceptions.DukeInvalidFormatException;

/**
 * This class parses the full command that calls for FindFreeTimesParse.
 */
public class FindFreeTimesParse extends Parse {
    private String fullCommand;
    private Integer duration;

    /**
     * Creates FindFreeTimesParse object.
     * @param fullCommand
     */
    public FindFreeTimesParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException {
        fullCommand = fullCommand.replaceFirst("Find", "");
        fullCommand = fullCommand.trim();
        fullCommand = fullCommand.replaceFirst("hours", "");
        fullCommand = fullCommand.trim();
        if(fullCommand.isEmpty()){
            throw new DukeInvalidFormatException("Invalid input. Please enter the command as follows. \n" +
                    "Find 'x' hours , where 'x' is a digit");
        } else {
            duration = Integer.parseInt(fullCommand);
            return new FindFreeTimesCommand(duration);
        }
    }
}

