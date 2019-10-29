package Parser;

import Commands.Command;
import Commands.FindFreeTimesCommand;
import Commands.RetrieveFreeTimesCommand;
import DukeExceptions.DukeInvalidFormatException;

/**
 * This class parses the full command that calls for FindFreeTimesParse.
 */
public class RetrieveFreeTimesParse extends Parse {
    private String fullCommand;
    private Integer duration;

    /**
     * Creates FindFreeTimesParse object.
     * @param fullCommand
     */
    public RetrieveFreeTimesParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException {
        fullCommand = fullCommand.replaceFirst("retrieve/freetime ", "");
        fullCommand = fullCommand.trim();
        if(fullCommand.isEmpty()){
            throw new DukeInvalidFormatException("Invalid input. Please enter the command as follows. \n" +
                    "Find 'x' hours , where 'x' is a digit");
        } else {
            duration = Integer.parseInt(fullCommand);
            return new RetrieveFreeTimesCommand(duration);
        }
    }
}

