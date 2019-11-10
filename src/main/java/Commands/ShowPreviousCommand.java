package Commands;

import Commons.Duke;
import Commons.DukeConstants;
import Commons.Storage;
import Commons.UserInteraction;
import DukeExceptions.DukeInvalidCommandException;
import Tasks.TaskList;
import java.util.ArrayList;

/**
 * Represents the command which shows the previous inputs requested by the user.
 */
public class ShowPreviousCommand extends Command {

    private final String fullCommand;

    /**
     * Creates ShowPreviousCommand object.
     * @param fullCommand The user's input
     */
    public ShowPreviousCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * This method adds previous command into the outputList base on user's choice/keyword.
     * @param userInputList The list that contains all user inputs
     * @param outputList The list that contains the inputs the user requested
     * @param string The user's keyword for adding into outputList
     * @return outputList which contains the inputs requested by user
     */
    public ArrayList<String> previousCommandsHandler(ArrayList<String> userInputList,
                                                     ArrayList<String> outputList, String string) {
        int size = userInputList.size() - 1;
        String userInput;
        for (int j = 0; j < size; j++) {
            userInput = userInputList.get(j);
            if (userInput.startsWith(string)) {
                outputList.add(userInput + " \n");
            }
        }
        return outputList;
    }

    public static ArrayList<String> result = new ArrayList<>();
    public ArrayList<String> userInputsList = new ArrayList<>();
    public final ArrayList<String> updatedUserInputList = new ArrayList<>();

    /**
     * Shows the previous user inputs that user requested.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the message to display all the inputs
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display the lists of user inputs
     * @throws DukeInvalidCommandException when user request to show more
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage)
            throws DukeInvalidCommandException {
        boolean isNumber = true;
        int number = 0;
        try {
            number = Integer.parseInt(fullCommand);
        } catch (NumberFormatException e) {
            isNumber = false;
        }

        ArrayList<String> outputList = new ArrayList<>();
        userInputsList = Duke.getUserInputs();
        int size = userInputsList.size();

        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                String userInput = userInputsList.get(i);
                updatedUserInputList.add(userInput);
            }
        }

        int sizeOfUpdatedList = updatedUserInputList.size();
        int sizeOfPreviousList = sizeOfUpdatedList - 1;

        if (sizeOfPreviousList < number) {
            throw new DukeInvalidCommandException("There are only " + sizeOfPreviousList + " previous commands. "
                    + DukeConstants.STR_RANGE_FOR_LESS_THAN + sizeOfPreviousList + " .");
        }

        if (isNumber) {
            int startIndex = sizeOfPreviousList - 1;
            for (int i = 0; i < number; i++) {
                outputList.add(updatedUserInputList.get(startIndex) + " \n");
                startIndex -= 1;
            }
            result = outputList;
        } else if (fullCommand.equals(DukeConstants.ADD_DEADLINE_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.ADD_DEADLINE_HEADER);
        } else if (fullCommand.equals(DukeConstants.ADD_EVENT_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.ADD_EVENT_HEADER);
        } else if (fullCommand.equals(DukeConstants.DELETE_DEADLINE_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.DELETE_DEADLINE_HEADER);
        } else if (fullCommand.equals(DukeConstants.DELETE_EVENT_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.DELETE_EVENT_HEADER);
        } else if (fullCommand.equals(DukeConstants.RECUR_WEEKLY_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.RECUR_WEEKLY_HEADER);
        } else if (fullCommand.equals(DukeConstants.RECUR_BIWEEKLY_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.RECUR_BIWEEKLY_HEADER);
        } else if (fullCommand.equals(DukeConstants.REMOVE_RECUR_WEEKLY_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList,
                    outputList, DukeConstants.REMOVE_RECUR_WEEKLY_HEADER);
        } else if (fullCommand.equals(DukeConstants.REMOVE_RECUR_BIWEEKLY_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList,
                    outputList, DukeConstants.REMOVE_RECUR_BIWEEKLY_HEADER);
        } else if (fullCommand.equals(DukeConstants.REMIND_SET_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.REMIND_SET_HEADER);
        } else if (fullCommand.equals(DukeConstants.REMOVE_REMIND_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.REMOVE_REMIND_HEADER);
        } else if (fullCommand.equals(DukeConstants.REMIND_CHECK_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.REMIND_CHECK_HEADER);
        } else if (fullCommand.equals(DukeConstants.SHOW_WORKLOAD_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.SHOW_WORKLOAD_HEADER);
        } else if (fullCommand.equals(DukeConstants.SHOW_FILTER_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.SHOW_FILTER_HEADER);
        } else if (fullCommand.equals(DukeConstants.HELP_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.HELP_HEADER);
        } else if (fullCommand.equals(DukeConstants.DONE_EVENT_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.DONE_EVENT_HEADER);
        } else if (fullCommand.equals(DukeConstants.DONE_DEADLINE_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.DONE_DEADLINE_HEADER);
        } else if (fullCommand.equals(DukeConstants.FIND_TIME_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.FIND_TIME_HEADER);
        } else if (fullCommand.equals(DukeConstants.RETRIEVE_PREVIOUS_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.RETRIEVE_PREVIOUS_HEADER);
        } else if (fullCommand.equals(DukeConstants.SHOW_PREVIOUS_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.SHOW_PREVIOUS_HEADER);
        } else if (fullCommand.equals(DukeConstants.RETRIEVE_TIME_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.RETRIEVE_TIME_HEADER);
        } else if (fullCommand.equals(DukeConstants.SHOW_WEEK_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.SHOW_WEEK_HEADER);
        } else if (fullCommand.equals(DukeConstants.BYE_HEADER)) {
            result = previousCommandsHandler(updatedUserInputList, outputList, DukeConstants.BYE_HEADER);
        }
        return ui.showPrevious(result);
    }

    /**
     * This method gets the list of output that the user request to show.
     */
    public static ArrayList<String> getOutputList() {
        return result;
    }
}
