package inventory;

import java.util.ArrayList;

import exception.DukeException;
import storage.Constants;



public class Inventory extends ArrayList<Item> {

    /**
     * Create Inventory from text file.
     * @param loader strings from text file containing Item info
     * @throws DukeException if file format incorrect
     */
    public Inventory(ArrayList<String> loader) { //loads previous inventory data stored in text file
        for (String line : loader) {
            String[] splitStr = line.split(" \\| ", 2);
            this.add(new Item(splitStr[Constants.ITEMNAME], Integer.parseInt(splitStr[Constants.ITEMQTY])));
        }
    }

    public Inventory() {
        super();
    }

    /**
     * To check if an item already exists in the current inventory.
     * @param inventory the list of requests
     * @param inputItem the item in question
     * @return if the item already exists
     */
    public static boolean checkInventory(Inventory inventory, String inputItem) {
        boolean found = false;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals(inputItem)) {
                found = true;
            }
        }
        return found;
    }
}
