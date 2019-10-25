package duke.logic.command.inventorycommands;

import duke.logic.command.CommandInventory;
import duke.model.list.inventorylist.InventoryList;
import duke.storage.InventoryStorage;
import duke.model.task.ingredienttasks.Ingredient;

import java.util.*;

import static duke.common.InventoryMessages.COMMAND_LIST_INVENTORY;
import static duke.common.InventoryMessages.MESSAGE_HERE_ARE_THE_INGREDIENTS;
import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;

/**
 * Handles the list command and inherits all the fields and methods of Command parent class.
 */
public class ListInventoryCommand extends CommandInventory {

    /**
     * Constructor for class ListCommand.
     * @param userInput String containing input command from user
     */
    public ListInventoryCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the list command to display all tasks in task list.
     * @param inventoryList contains the task list
     * @param inventoryStorage deals with loading tasks from the file and saving tasks in the file
     */

    /*
    public ArrayList<String> execute(InventoryList inventoryList, Ui ui, InventoryStorage inventoryStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(MESSAGE_LIST_INGREDIENTS);
        for (int i = 0; i < inventoryList.getSize(); i++) {
            final int displayedIndex = i + DISPLAYED_INDEX_OFFSET;
            arrayList.add("     " + displayedIndex + ". " + inventoryList.get(i));
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
*/


    public ArrayList<String> execute(InventoryList inventoryList, InventoryStorage inventoryStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_LIST_INVENTORY)) {
            arrayList.add(MESSAGE_HERE_ARE_THE_INGREDIENTS);
            // using generics. can use KeySet also but use EntrySet can extend for further purpose later on.
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
            arrayList.add(Integer.toString(i + DISPLAYED_INDEX_OFFSET) + ". " + value.getIngredientName());
            i++;
        }
        return arrayList;
    }
}
