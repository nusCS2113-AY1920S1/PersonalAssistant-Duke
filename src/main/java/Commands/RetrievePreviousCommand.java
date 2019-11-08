package Commands;

import Commons.Storage;
import Commons.UserInteraction;
import DukeExceptions.DukeInvalidCommandException;
import Tasks.TaskList;
import java.util.ArrayList;

public class RetrievePreviousCommand extends Command {
    private String fullCommand;
    public static String retrievedOutput;

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
        fullCommand = fullCommand.trim();

        ArrayList<String> retrievedList;
        retrievedList = ShowPreviousCommand.getOutputList();
        int size = retrievedList.size();
        if (size == 0) {
            throw new DukeInvalidCommandException("You did not enter Show Previous Command yet. \n"
                    + "Format: show previous <num> or show previous <type> <num>");
        }
        int intFullCommand = Integer.parseInt(fullCommand);
        if (intFullCommand > size) {
            throw new DukeInvalidCommandException("There are only " + size + " of previous commands."
                    + "Please enter a valid number less than or equal to " + size + " .");
        }
        int index = intFullCommand - 1;
        retrievedOutput = retrievedList.get(index);
        return ui.showChosenPreviousChoice(retrievedOutput);
    }

    public static String getChosenOutput() {
        return retrievedOutput;
    }
}
