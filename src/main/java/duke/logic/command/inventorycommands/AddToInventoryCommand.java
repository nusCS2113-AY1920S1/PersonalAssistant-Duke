package duke.logic.command.inventorycommands;

import duke.logic.command.CommandInventory;
import duke.model.list.inventorylist.InventoryList;
import duke.storage.InventoryStorage;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.InventoryMessages.*;
import static duke.common.Messages.*;

public class AddToInventoryCommand extends CommandInventory {

    public AddToInventoryCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(InventoryList inventoryList, InventoryStorage inventoryStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_ADD_TO_INVENTORY)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(14) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (description.contains("q/") && description.contains("u/")) {
                String ingredientName, quantity, unit, additionalInfo, remaining, remaining2;
                ingredientName = description.split("q/", 2)[0].trim();
                remaining = description.split("q/", 2)[1].trim();
                quantity = remaining.split("u/", 2)[0].trim();
                remaining2 = remaining.split("u/", 2)[1].trim();
                if (remaining2.contains("a/")) {
                    unit = remaining2.split("a/", 2)[0].trim();
                    additionalInfo = remaining2.split("a/", 2)[1].trim();
                    if (additionalInfo.isEmpty()) {
                        additionalInfo = NO_ADDITIONAL_INFO;
                    }
                } else {
                    unit = remaining2;
                    additionalInfo = NO_ADDITIONAL_INFO;
                }
                if (ingredientName.isEmpty() || quantity.isEmpty() || unit.isEmpty()) {
                    arrayList.add(ERROR_MESSAGE_INCOMPLETE);
                } else {
                    if (isParsable(quantity) && isKnownUnit(unit)) {
                        inventoryList.addIngredient(ingredientName, quantity, unit, additionalInfo);
                        inventoryStorage.saveFile(inventoryList);
                        int index = inventoryList.getSize();
                        arrayList.add(MESSAGE_ADDED_TO_INVENTORY + "\n" + "       " + ingredientName + "\n" + "Now you have " + index + " ingredient(s) in your inventory");
                    } else if (!isParsable(quantity) && isKnownUnit(unit)){
                        arrayList.add(ERROR_MESSAGE_INVALID_QUANTITY);
                    } else  if (!isKnownUnit(unit) && isParsable(quantity)) {
                        arrayList.add(ERROR_MESSAGE_INVALID_UNIT);
                    } else {
                        arrayList.add(ERROR_MESSAGE_INVALID_QUANTITY_OR_UNIT);
                    }
                }
            } else {
                arrayList.add(ERROR_MESSAGE_ADD_INCORRECT_FORMAT);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    private static boolean isParsable(String quantity) {
        try {
            Integer.parseInt(quantity);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isKnownUnit(String unit) { // edit this part.
        if (unit.equals("g") || unit.equals("kg") || unit.equals("l") || unit.equals("ml") || unit.equals("cup") || unit.equals("teaspoon") || unit.equals("tablespoon")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    };
}
