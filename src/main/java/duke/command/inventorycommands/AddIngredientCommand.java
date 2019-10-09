package duke.command.inventorycommands;

import duke.command.Command;
import duke.exception.DukeException;
import duke.ingredientlist.IngredientList;
import duke.storage.IngredientStorage;

import duke.ui.Ui;

import java.text.ParseException;

import static duke.common.Messages.*;
import static duke.common.IngredientMessages.ERROR_MESSAGE_INCOMPLETE;
import static duke.common.IngredientMessages.COMMAND_ADD_INGREDIENT;

public class AddIngredientCommand extends Command {

    public AddIngredientCommand(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.INGREDIENT;
    }

    public void execute(IngredientList ingredientList, Ui ui, IngredientStorage ingredientStorage) throws DukeException, ParseException {
        if (userInput.trim().equals(COMMAND_ADD_INGREDIENT)) {
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(13) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (description.contains("q/")) {
                String ingredientName = description.split("q/", 2)[0].trim();
                String quantity = description.split("q/", 2)[1].trim();
                if (ingredientName.isEmpty() || quantity.isEmpty()) {
                    throw new DukeException(ERROR_MESSAGE_INCOMPLETE);
                } else if (isParsable(quantity)) {
                    ingredientList.addIngredient(ingredientName, Integer.parseInt(quantity));
                    ingredientStorage.saveFile(ingredientList);
                }
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

    public static boolean isParsable(String input) {
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
    };
}
