package Commands;

import Commons.Duke;
import Commons.Storage;
import Commons.UserInteraction;
import DukeExceptions.DukeInvalidCommandException;
import Tasks.TaskList;
import java.util.ArrayList;

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
    public ArrayList<String> previousCommandsHandler(ArrayList<String> userInputList, ArrayList<String> outputList, String string) {
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
     * @param ui The Ui object to display the message to display all the inputs
     * @return This returns the method in the Ui object which returns the string to display the lists of user inputs
     * @throws DukeInvalidCommandException when user request to show more
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) throws DukeInvalidCommandException {
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
                    + "Please enter a valid number less than or equal to " + sizeOfPreviousList + " .");
        }

        if (isNumber) {
            int startIndex = sizeOfPreviousList - 1;
            for (int i = 0; i < number; i++) {
                outputList.add(updatedUserInputList.get(startIndex) + " \n");
                startIndex -= 1;
            }
            result = outputList;
        } else if (fullCommand.equals("add/d")) {
            result = previousCommandsHandler(updatedUserInputList, outputList,"add/d");
        } else if (fullCommand.equals("add/e")) {
            result = previousCommandsHandler(updatedUserInputList, outputList,"add/e");
        } else if (fullCommand.equals("delete/d")) {
            result = previousCommandsHandler(updatedUserInputList, outputList,"delete/d");
        } else if (fullCommand.equals("delete/e")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "delete/e");
        } else if (fullCommand.equals("recur/weekly")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "recur/weekly");
        } else if (fullCommand.equals("recur/biweekly")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "recur/biweekly");
        } else if (fullCommand.equals("recur/rmweekly")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "recur/rmweekly");
        } else if (fullCommand.equals("recur/rmbiweekly")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "recur/rmbiweekly");
        } else if (fullCommand.equals("remind/set")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "remind/set");
        } else if (fullCommand.equals("remind/rm")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "remind/rm");
        } else if (fullCommand.equals("/show")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "/show");
        } else if (fullCommand.equals("filter")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "filter");
        } else if (fullCommand.equals("help")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "help");
        } else if (fullCommand.equals("list")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "list");
        } else if (fullCommand.equals("done")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "done");
        } else if (fullCommand.equals("find")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "find");
        } else if (fullCommand.equals("show/previous")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "show/previous");
        } else if (fullCommand.equals("retrieve/ft")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "retrieve/ft");
        } else if (fullCommand.equals("Week")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "Week");
        }
        return ui.showPrevious(result);
    }

    public static ArrayList<String> getOutputList() {
        return result;
    }
}
