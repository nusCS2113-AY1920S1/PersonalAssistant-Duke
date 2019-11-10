package command;


import booking.ApprovedList;
import booking.BookingList;
import exception.DukeException;
import inventory.Inventory;
import inventory.InventorySorter;
import room.RoomList;
import storage.StorageManager;
import ui.Ui;
import user.UserList;

public class ListInventoryCommand extends Command{
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage)
            throws DukeException {
        if (inventory.isEmpty()) {
            throw new DukeException("The inventory is empty. Please add an item.");
        }

        InventorySorter inventorySorter = new InventorySorter(inventory);

        ui.addToOutput("Here are the items in the inventory: ");
        for (int i = 0; i < inventory.size(); i++) {
            ui.addToOutput(i + 1 + ". " + inventory.get(i).toString());
        }


    }
}
