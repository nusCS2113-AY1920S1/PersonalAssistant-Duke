
package command;

import inventory.Inventory;
import inventory.Item;

import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import ui.ListBox;
import ui.Ui;
import user.User;

//@@author amoschan97
public class ListCommand extends Command {

    //@@ AmosChan97
    /**
     * Prints to the UI a list containing all bookings made.
     * @param roomList list of rooms
     * @param bookingList bookings list
     * @param ui user interface
     * @param bookingStorage handles read write of bookings list file
     * @param roomStorage handles read write of room list file
     * @param user Current user
     * @throws DukeException if there are no bookings
     */
    @Override
    public void execute(Inventory inventory, RoomList roomList, BookingList bookingList, Ui ui, Storage inventoryStorage, Storage bookingstorage, Storage roomstorage, User user) throws DukeException {
        if (bookingList.isEmpty()) {
            throw new DukeException("OOPS! There are no bookings in your list");
        }
        ui.addToOutput("Here are the bookings: ");
    }
}
