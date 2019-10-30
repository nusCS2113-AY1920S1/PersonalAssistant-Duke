
package command;

import inventory.Inventory;

import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import user.UserList;

//@@author amoschan97
public class ListCommand extends Command {

    //@@ AmosChan97
    /**
     * Prints to the UI a list containing all bookings made.
     * @param roomList list of rooms
     * @param bookingList bookings list
     * @param ui user interface
     * @throws DukeException if there are no bookings
     */
    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList, BookingList bookingList, Ui ui,
                        Storage userStorage, Storage inventoryStorage, Storage bookingstorage, Storage roomstorage)
            throws DukeException {
        if (bookingList.isEmpty()) {
            throw new DukeException("OOPS! There are no bookings in your list");
        }
        ui.addToOutput("Here are the bookings: ");
    }
}
