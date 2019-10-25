package duke.logic.command.inventorycommands;

import duke.logic.command.CommandInventory;
import duke.exception.DukeException;
import duke.model.list.inventorylist.InventoryList;
import duke.storage.InventoryStorage;
import duke.model.task.ingredienttasks.Ingredient;

import java.util.ArrayList;

import static duke.common.InventoryMessages.*;
import static duke.common.Messages.*;

/**
 * Handles the delete command and inherits all the fields and methods of Command parent class.
 */
public class DeleteFromInventoryCommand extends CommandInventory {

    /**
     * Constructor for class DeleteCommand.
     * @param userInput String containing input command from user
     */
    public DeleteFromInventoryCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the delete command to delete task in the task list.
     * @param inventoryList contains the task list
     * @param inventoryStorage deals with loading tasks from the file and saving tasks in the file
     * @throws DukeException if Duke cannot recognize the user input
     *                      or user inputs an invalid index or the list of tasks is empty
     */
    @Override
    public ArrayList<String> execute(InventoryList inventoryList, InventoryStorage inventoryStorage) throws DukeException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_DELETE_FROM_INVENTORY)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(19) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            Ingredient value = inventoryList.deleteIngredient(description);
            if (value == null) {
                arrayList.add(ERROR_MESSAGE_DELETE_INGREDIENT_NOT_FOUND);
                inventoryStorage.saveFile(inventoryList);
            } else {
                arrayList.add(MESSAGE_INGREDIENT_DELETED + "       " + description + "\n" + "Now you have " + inventoryList.getSize() + " ingredients(s) in the inventory.");
                inventoryStorage.saveFile(inventoryList);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }
    /*
            ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_DELETE_FROM_INVENTORY)) {
            arrayList.add(ERROR_MESSAGE_EMPTY_INDEX + MESSAGE_FOLLOWUP_EMPTY_INDEX);
        } else if (userInput.trim().charAt(19) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (isParsable(description)) {
                //converting string to integer
                int index = Integer.parseInt(description);
                if (index > inventoryList.getSize() || index <= 0) {
                    if (inventoryList.getSize() == 0) {
                        arrayList.add(ERROR_MESSAGE_DELETING_FROM_EMPTY_LIST);
                    } else {
                        arrayList.add(ERROR_MESSAGE_INVALID_INDEX + inventoryList.getSize() + ".");
                    }
                } else {
                    inventoryStorage.saveFile(inventoryList);
                    String msg = "";
                    if (inventoryList.getSize() - 1 <= 1) {
                        msg = " ingredient in the list.";
                    } else {
                        msg = MESSAGE_ITEMS2;
                    }
                    arrayList.add(MESSAGE_INGREDIENT_DELETED + "       " + inventoryList.get(index - 1)
                            + "\n" + MESSAGE_ITEMS1 + (inventoryList.getSize() - 1) + msg);
                    inventoryList.deleteIngredient(index - 1);
                }
            } else {
                arrayList.add(ERROR_MESSAGE_UNKNOWN_INDEX);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
     */

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