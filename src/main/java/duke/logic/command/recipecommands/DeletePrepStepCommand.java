package duke.logic.command.recipecommands;

import duke.logic.command.CommandPrepStep;
import duke.exception.DukeException;
import duke.model.list.recipelist.PrepStepList;
import duke.storage.PrepStepStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.COMMAND_DELETE_PREPSTEP;
import static duke.common.RecipeMessages.MESSAGE_DELETE_PREPSTEP;

public class DeletePrepStepCommand extends CommandPrepStep {

    /**
     * Constructor for class DeleteCommand.
     * @param userInput String containing input command from user
     */
    public DeletePrepStepCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(PrepStepList prepStepList, Ui ui, PrepStepStorage prepStepStorage) throws DukeException, ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_DELETE_PREPSTEP)) {
            arrayList.add(ERROR_MESSAGE_EMPTY_INDEX + MESSAGE_FOLLOWUP_EMPTY_INDEX);
        } else if (userInput.trim().charAt(5) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (isParsable(description)) {
                //converting string to integer
                int index = Integer.parseInt(description);
                if (index > prepStepList.getSize() || index <= 0) {
                    if (prepStepList.getSize() == 0) {
                        arrayList.add(ERROR_MESSAGE_EMPTY_LIST);
                    } else {
                        arrayList.add(ERROR_MESSAGE_INVALID_INDEX + prepStepList.getSize() + ".");
                    }
                } else {
                    arrayList.add(MESSAGE_DELETE_PREPSTEP + "         " + prepStepList.getPrepStepList().get(index - 1));
                    prepStepList.deletePrepStep(index - 1);
                    prepStepStorage.saveFile(prepStepList);
                }
            } else {
                arrayList.add(ERROR_MESSAGE_UNKNOWN_INDEX);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    /**
     * Validates that user inputs an integer value for the index.
     * @param input String containing integer input from user for the index
     * @return true if the user inputs an integer and false otherwise
     */
    private static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
