
package command;

import booking.ApprovedList;
import inventory.Inventory;
import inventory.Item;


import exception.DukeException;
import room.RoomList;
import booking.BookingList;
import storage.Storage;
import storage.StorageManager;
import ui.Ui;
import user.UserList;


import java.io.IOException;
import java.text.ParseException;

public class AddInventoryCommand extends Command {
    private int quantity;
    private String name;
    private String[] splitC;
    private Item newItem;


    /**
     * Creates new inventory item.
     * format is: addinventory NAME /qty QUANTITY /in ROOM
     *
     * @param input    from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     * @throws IOException   when entry is incorrect
     */
    public AddInventoryCommand(String input, String[] splitStr) throws DukeException, IOException {
        if (splitStr.length <= 3) { //length of splitStr array
            throw new DukeException("☹ OOPS!!! Please create your inventory with the following format: name, quantity");
        }
        if (!input.contains(" /qty ")) {
            throw new DukeException("Please add the quantity of your item after /qty");
        }

        String temp = input.substring(13);
        splitC = temp.split(" /qty ", 2);
        if (splitC.length < 2) {
            throw new DukeException("☹ OOPS!!! Please create your inventory with the following format:"
                    + " NAME /qty QUANTITY");
        }
        this.name = splitC[0];
        try {
            this.quantity = Integer.parseInt(splitC[1]);
        } catch (Exception e) {
            throw new DukeException("Please only input the Quantity in whole numbers");
        }

        newItem = new Item(name, quantity);
    }


    /**
     * Executes the command to add a room to the system.
     * //@param roomList room list
     * @param inventory bookings list
     * @param ui user interface
     * @param allStorage all the storage in command execution
     * @throws DukeException if a clash in booking is found
     * @throws IOException if input entry is incorrect
     */
    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage)
            throws DukeException, IOException, ParseException {
        boolean clash = Inventory.checkInventory(inventory, name); //make this function in Inventory class
        if (clash) {
            throw new DukeException("☹ OOPS!!! ITEM ALREADY EXISTS ");
        }
        inventory.add(newItem);
        allStorage.getInventoryStorage().saveToFile(inventory);
        ui.addToOutput("Got it, I've added this to inventory.\n"
                + newItem.toString() + "\n" + "Now you have " + inventory.size() + " item(s) in the inventory.");
    }
}
