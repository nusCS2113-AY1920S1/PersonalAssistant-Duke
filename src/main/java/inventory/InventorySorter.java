package inventory;

import java.util.ArrayList;
import java.util.Collections;

public class InventorySorter {
    ArrayList inventory = new ArrayList<>();

    public InventorySorter(ArrayList inventory) {
        this.inventory = inventory;
    }

    public ArrayList getSortedInvByRoom() {
        Collections.sort(inventory, new Item.SortbyRoom());
        return inventory;
    }

}