package duke.logic.command.inventorycommands;

import duke.logic.command.Command;
import duke.model.list.inventorylist.InventoryList;
import duke.model.task.ingredienttasks.Ingredient;
import duke.storage.InventoryStorage;
import duke.ui.Ui;

import java.util.*;

import static duke.common.InventoryMessages.COMMAND_LIST_INVENTORY;
import static duke.common.InventoryMessages.MESSAGE_HERE_ARE_THE_INGREDIENTS;
import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;

/**
 * Handles the list command and inherits all the fields and methods of Command parent class.
 */
public class ListInventoryCommand extends Command<InventoryList, Ui, InventoryStorage> {

    /**
     * Constructor for class ListCommand.
     * @param userInput String containing input command from user
     */
    public ListInventoryCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the list recipe command to show a list of the existing ingredients in the inventory list.
     *
     * @param inventoryList contains the inventory list
     * @param ui             deals with interactions with the user
     * @param inventoryStorage deals with loading tasks from the file and saving ingredients in the file
     * @return an array list consist of the results or prompts to be displayed to user
     */
    @Override
    public ArrayList<String> execute(InventoryList inventoryList, Ui ui, InventoryStorage inventoryStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_LIST_INVENTORY)) {
            arrayList.add(MESSAGE_HERE_ARE_THE_INGREDIENTS);
            arrayList.addAll(listOfInventories(inventoryList.getInventoryList()));
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    private ArrayList<String> listOfInventories(HashMap<String, Ingredient> map) {
        Set entries = map.entrySet();
        Iterator entryIter = entries.iterator();
        ArrayList<String> arrayList = new ArrayList<>();
        int i = 0;
        while (entryIter.hasNext()) {
            Map.Entry entry = (Map.Entry) entryIter.next();
            Object key = entry.getKey();  // Get the key from the entry.
            Ingredient value = (Ingredient) entry.getValue();  // Get the value.
            arrayList.add(Integer.toString(i + DISPLAYED_INDEX_OFFSET) + ". " + value.toString());
            i++;
        }
        return arrayList;
    }
}
