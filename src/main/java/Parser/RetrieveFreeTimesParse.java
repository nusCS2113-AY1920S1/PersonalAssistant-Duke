package Parser;

import Commands.Command;
import Commands.FindFreeTimesCommand;
import Commands.RetrieveFreeTimesCommand;
import DukeExceptions.DukeInvalidFormatException;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * This class parses the full command that calls for RetrieveFreeTimesParse.
 */
public class RetrieveFreeTimesParse extends Parse {
    private String fullCommand;
    private final String invalidInput = "Invalid input. Please enter the command as follows. \n" +
            "retrieve/ft 'x', where 'x' is a digit between 1 - 5";
    private final String invalidOption = "Invalid option. Please enter the command as follows. \n" +
            "retrieve/ft 'x', where 'x' is a digit between 1 - 5";

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
            throw new DukeInvalidFormatException(invalidInput);
        } else {
            try {
                Integer option = Integer.parseInt(fullCommand);
                if(option >=1 && option <= 5) return new RetrieveFreeTimesCommand(option);
                else throw new DukeInvalidFormatException(invalidOption);
            } catch (NumberFormatException e) {
                throw new DukeInvalidFormatException(invalidInput);
            }
        }
    }
}

