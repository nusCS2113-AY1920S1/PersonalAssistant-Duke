package commands;

import commons.DukeConstants;
import commons.DukeLogger;
import commons.Storage;
import commons.UserInteraction;
import dukeexceptions.DukeInvalidCommandException;
import dukeexceptions.DukeNoValidDataException;
import tasks.TaskList;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Represents the command to retrieve the previous command requested by the user.
 */
public class RetrievePreviousCommand extends Command {
    private String fullCommand;
    public static String retrievedOutput;
    private final Logger logger = DukeLogger.getLogger(RetrievePreviousCommand.class);
    private static boolean isValid = true;

    /**
     * Creates a RetrievePreviousCommand object.
     * @param fullCommand The user's input
     */
    public RetrievePreviousCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * Retrieves the chosen input that the user wish to get.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the message for chosen input
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display retrieve previous message
     * @throws DukeInvalidCommandException on empty list and invalid index input
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage)
            throws DukeInvalidCommandException, DukeNoValidDataException {
        fullCommand = fullCommand.replace(DukeConstants.RETRIEVE_PREVIOUS_HEADER, DukeConstants.NO_FIELD);

        if (!fullCommand.isEmpty()) {
            char checkSpace = fullCommand.charAt(0);
            if (checkSpace != ' ') {
                throw new DukeInvalidCommandException(DukeConstants.INVALID_WITHOUT_SPACE);
            }
        }

        fullCommand = fullCommand.trim();
        if (fullCommand.length() == 0) {
            isValid = false;
            throw new DukeInvalidCommandException(DukeConstants.INVALID_EMPTY_NUMBER);
        }

        ArrayList<String> retrievedList;
        retrievedList = ShowPreviousCommand.getOutputList();
        int size = retrievedList.size();
        if (size == 0) {
            isValid = false;
            throw new DukeNoValidDataException(DukeConstants.NO_PREVIOUS_COMMAND_TO_GET_LIST);
        }

        boolean isNumber = true;
        int intFullCommand = 0;
        try {
            intFullCommand = Integer.parseInt(fullCommand);
        } catch (NumberFormatException e) {
            logger.severe("Unable to parse string to integer");
            isNumber = false;
            isValid = false;
            throw new DukeInvalidCommandException(DukeConstants.INVALID_STRING_SHOULD_BE_INTEGER);
        }

        if (isNumber) {
            if (intFullCommand <= 0) {
                isValid = false;
                throw new DukeInvalidCommandException(DukeConstants.STR_RANGE_FOR_FROM + size + " .");
            } else if (intFullCommand > size) {
                isValid = false;
                throw new DukeInvalidCommandException("There are only " + size + " of previous commands."
                        + DukeConstants.STR_RANGE_FOR_LESS_THAN + size + " but greater than 0.");
            }
            int index = intFullCommand - 1;
            retrievedOutput = retrievedList.get(index);
            isValid = true;
        }
        return ui.showChosenPreviousChoice(retrievedOutput);
    }

    /**
     * This method get the chosen output requested by the user.
     */
    public static String getChosenOutput() {
        return retrievedOutput;
    }

    /**
     * This method checks if the input by the user is an integer.
     */
    public static boolean isValid() {
        return isValid;
    }
}