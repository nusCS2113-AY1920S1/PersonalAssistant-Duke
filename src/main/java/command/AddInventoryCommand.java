
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
    private String roomcode;
    private String[] splitA;
    private String[] splitB;
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
        if (splitStr.length <= 5) { //length of splitStr array
            throw new DukeException("OOPS!!! Please create your inventory with the following format: name, quantity, room");
        }
        if (!input.contains(" /qty ")) {
            throw new DukeException("Please add the quantity of your item after /qty");
        }
        if (!input.contains(" /in ")) {
            throw new DukeException("Please add the roomcode that your item belongs to after /in");

        String temp = input.substring(13);
        splitA = temp.split(" /qty ", 2);
        if (splitA.length < 2) {
            throw new DukeException("OOPS!!! Please create your inventory with the following format:"
                    + " NAME /qty QUANTITY /in ROOM");
        }
        this.name = splitA[0];
        splitB = splitA[1].split("/in", 2);

        try {
            this.quantity = Integer.parseInt(splitB[0]);
        } catch (Exception e) {
            throw new DukeException("Please only input the Quantity in whole numbers");
        }

        newItem = new Item(roomcode, name, quantity);
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

        /*  INVENTORY CLASH SHOULD CHECK FOR BOTH NAME AND ROOM
        boolean clash = Inventory.checkInventory(inventory, name); //make this function in Inventory class
        if (clash) {
            throw new DukeException("OOPS!!! ITEM ALREADY EXISTS ");
        }
        */

        inventory.add(newItem);
        allStorage.getInventoryStorage().saveToFile(inventory);
        ui.addToOutput("Got it, I've added this to inventory.\n"
                + newItem.toString() + "\n" + "Now you have " + inventory.size() + " item(s) in the inventory.");
    }
}
