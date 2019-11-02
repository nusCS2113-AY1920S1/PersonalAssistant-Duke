package duke.logic.command.inventorycommands;

import duke.logic.command.Command;
import duke.model.list.inventorylist.InventoryList;
import duke.storage.InventoryStorage;
import duke.ui.Ui;

import java.util.*;

import static duke.common.InventoryMessages.COMMAND_CLEAR_INVENTORY;
import static duke.common.InventoryMessages.MESSAGE_INGREDIENTS_CLEARED;
import static duke.common.InventoryMessages.ERROR_MESSAGE_INVENTORY_ALREADY_EMPTY;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;

public class ClearInventoryCommand extends Command<InventoryList, Ui, InventoryStorage> {

    public ClearInventoryCommand(String userInput) {
        this.userInput = userInput;
    }

    public ArrayList<String> execute(InventoryList inventoryList, Ui ui, InventoryStorage inventoryStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_CLEAR_INVENTORY)) {
            if (inventoryList.getSize() == 0) {
                arrayList.add(ERROR_MESSAGE_INVENTORY_ALREADY_EMPTY);
            } else {
                inventoryList.clearInventory();
                arrayList.add(MESSAGE_INGREDIENTS_CLEARED);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
