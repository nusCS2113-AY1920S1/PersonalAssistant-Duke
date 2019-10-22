

import java.util.ArrayList;

public class Facility {

    protected String roomCode;

    protected String Name;

    protected ArrayList <Items> inventory; //convert to 2d array

    protected int capacity;

    protected Facility(String roomCode, int capacity) {
        this.roomCode = roomCode;
        this.capacity = capacity;
    }

    /**
     * To add a new item into the room's inventory
     * @param item the new entry
     * @param amount the quantity of the item
     */
    protected void addInventory(String item, int amount) {
        Items entry = new Items(item, amount);
        inventory.add(entry);
    }

    /**
     * To edit the inventory of the room
     * @param item to edit
     * @param amount new quantity of item
     */
    protected void editInventory(String item, int amount) {
        boolean found = false;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.contains(item)) {
                found = true;
                inventory.get(i).amount = amount;
                System.out.println("This place now has:\n");
                System.out.println((i + 1) + ". " + inventory.get(i).toString() + "\n");
                break;
            }
        }
        if (!found) {
            System.out.println("No items match your search!");
        }
    }


}
