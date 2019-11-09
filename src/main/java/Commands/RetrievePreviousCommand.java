package Commands;

import Commons.DukeLogger;
import Commons.Storage;
import Commons.UserInteraction;
import DukeExceptions.DukeInvalidCommandException;
import Tasks.TaskList;
import java.util.ArrayList;
import java.util.logging.Logger;

public class RetrievePreviousCommand extends Command {
    private String fullCommand;
    public static String retrievedOutput;
    private final Logger LOGGER = DukeLogger.getLogger(RetrievePreviousCommand.class);

    /**
     * Creates a RetrievePreviousCommand object.
     * @param fullCommand The user's input
     */
    public RetrievePreviousCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * Retrieves the chosen input that the user wish to get.
     * @param ui The Ui object to display the message for chosen input
     * @return This returns the method in the Ui object which returns the string to display retrieve previous message
     * @throws DukeInvalidCommandException on emtpy list and invalid index input
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) throws DukeInvalidCommandException {
        fullCommand = fullCommand.replace("retrieve/previous", "");

        if (!fullCommand.isEmpty()) {
            char checkSpace = fullCommand.charAt(0);
            if (checkSpace != ' ') {
                throw new DukeInvalidCommandException("There should be a space between the command retrieve/previous"
                        + " and <x>, where x is an integer");
            }
        }

        fullCommand = fullCommand.trim();
        if (fullCommand.length() == 0) {
            throw new DukeInvalidCommandException("<x> cannot be empty. Please enter the valid command as retrieve/previous <x>, "
                    + "where x is an integer.");
        }

        ArrayList<String> retrievedList;
        retrievedList = ShowPreviousCommand.getOutputList();
        int size = retrievedList.size();
        if (size == 0) {
            throw new DukeInvalidCommandException("You did not enter Show Previous Command yet. \n"
                    + "Format: show previous <num> or show previous <type> <num>");
        }

        boolean isNumber = true;
        int intFullCommand = 0;
        try {
            intFullCommand = Integer.parseInt(fullCommand);
        } catch (NumberFormatException e) {
            LOGGER.severe("Unable to parse string to integer");
            isNumber = false;
            throw new DukeInvalidCommandException("Unable to parse string. retrieve/previous <x>, where "
                     + "x must be an integer and not string.");
        }

        if (isNumber) {
            if (intFullCommand <= 0 ) {
                throw new DukeInvalidCommandException("Please enter a valid integer that is greater than 0");
            } else if (intFullCommand > size) {
                throw new DukeInvalidCommandException("There are only " + size + " of previous commands."
                        + "Please enter a valid number less than or equal to " + size + " .");
            }
            int index = intFullCommand - 1;
            retrievedOutput = retrievedList.get(index);
        }
        return ui.showChosenPreviousChoice(retrievedOutput);
    }

    public static String getChosenOutput() {
        return retrievedOutput;
    }
}