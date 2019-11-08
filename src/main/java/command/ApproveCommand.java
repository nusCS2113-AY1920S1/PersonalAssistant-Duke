
package command;

import booking.ApprovedList;
import inventory.Inventory;
import inventory.Item;

import booking.Booking;
import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Constants;
import storage.Storage;
import storage.StorageManager;
import ui.Ui;
import user.UserList;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApproveCommand extends Command {
    private int index;

    //@@author Alex-Teo
    /**
     * Approve a request.
     * format is approve name roomcode date time
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     * @throws IOException when entry is incorrect
     */
    public ApproveCommand(String input, String[] splitStr) throws DukeException, IOException {
        if (splitStr.length <= 1) {
            throw new DukeException("☹ OOPS!!! Please create the booking you want to approve"
                    + " with the following format: "
                    + "index");
        }
        try {
            index = Integer.parseInt(splitStr[1]);
        } catch (NumberFormatException e) {
            throw new DukeException("OOPS!!! Please enter a index in integer form!");
        }
        index -= 1;
    }

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage)
            throws DukeException, IOException {
        if (!userList.getLoginStatus()) {
            throw new DukeException("Please log in to approve or reject bookings.");
        }
        if (index < 0 || index >= bookingList.size()) {
            throw new DukeException("OOPS!!! The index you have entered is out of bounds");
        }
        bookingList.get(index).approveStatus(userList.getCurrentUser());
        ui.addToOutput("This request has been approved");
        ui.addToOutput(bookingList.get(index).toString());
        //bookingstorage.saveToFile(approvedList);
        allStorage.getBookingStorage().saveToFile(bookingList);

    }
}
