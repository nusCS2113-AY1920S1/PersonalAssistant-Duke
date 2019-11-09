
package command;

import booking.ApprovedList;
import inventory.Inventory;

import booking.Booking;
import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import storage.StorageManager;
import ui.Ui;
import user.UserList;

import java.io.IOException;
import java.text.ParseException;

public class FindBookingIndexCommand extends Command {
    private int index;

    //@@author  Alex-Teo
    /**
     * Find booking via index.
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException input error
     */
    public FindBookingIndexCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length <= 1) {
            throw new DukeException("☹ OOPS!!! Please create your booking with the following format: "
                    + "index");
        }
        index = Integer.valueOf(input.substring(10)) - 1;

    }

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage)
            throws DukeException, IOException, ParseException {
        if (index >= bookingList.size()) {
            throw new DukeException("OOPS!!!  No such entry exist!");
        } else {
            Booking result = bookingList.get(index);
            ui.addToOutput((index + 1) + ". " + result.toString());
        }
    }
}
