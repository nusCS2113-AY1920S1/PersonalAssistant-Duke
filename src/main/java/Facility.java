import java.util.ArrayList;

public class Facility {

    protected String roomCode;

    protected ArrayList <Items> Inventory;

    protected int Capacity;

    protected Facility (String roomCode, int Capacity) {
        this.roomCode = roomCode;
        this.Capacity = Capacity;

    }

    protected void addInventory (String item, int amount) {
        Items entry = new Items (item, amount);
        Inventory.add(entry);
    }

    protected void editInventory (String item, int amount) {
        boolean found = false;
        for (int i = 0; i < Inventory.size(); i++) {
            if (Inventory.get(i).name.contains(item)) {
                found = true;
                Inventory.get(i).amount = amount;
                break;
            }
        }
        if (!found) {
            //ui.showString("No items match your search!");
            System.out.println ("No items match your search!");
        }
    }


}
