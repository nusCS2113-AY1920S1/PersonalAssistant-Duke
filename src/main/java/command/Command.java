
package command;

import booking.ApprovedList;
import inventory.Inventory;

import exception.DukeException;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import booking.BookingList;
import user.UserList;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Abstract class from which all Commands are the extended from.
 */
public abstract class Command {
    protected boolean isExit;

    /**
     * Execute command logic.
     * @param bookingList bookings list
     * @param ui user interface
     * @throws DukeException if control.Duke specific exception found
     * @throws IOException if IO exception found
     */
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        Storage userStorage, Storage inventoryStorage,
                        Storage bookingstorage, Storage roomstorage, Storage approvestorage)
            throws DukeException, IOException, ParseException {

    }

    public boolean isExit() {
        return this.isExit;
    }

    /**
     * Check if date-time input is in valid format.
     * @param dateTime date and time for some tasks
     * @return true is input is valid
     */
    protected static boolean isValidDateTime(String dateTime) {
        SimpleDateFormat dateTimeFormat =  new SimpleDateFormat("d/M/yyyy HHmm");
        try {
            dateTimeFormat.parse(dateTime);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
