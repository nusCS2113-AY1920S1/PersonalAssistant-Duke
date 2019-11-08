
package command;

import booking.ApprovedList;
import inventory.Inventory;

import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import storage.StorageManager;
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
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage)
            throws DukeException {
        if (bookingList.isEmpty()) {
            throw new DukeException("OOPS! There are no bookings in your list");
        }
        ui.addToOutput("Here are the bookings: ");
    }
}
