
package command;

import booking.ApprovedList;
import inventory.Inventory;

import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.BookingConstants;
import storage.StorageManager;
import ui.Ui;
import storage.Constants;
import user.UserList;

import java.io.IOException;
import java.text.ParseException;

public class EditBookingCommand extends Command {

    private int index;
    private String[] splitC;
    private String textToEdit;

    //@@author Alex-Teo
    /**
     * Edit the description of a booking request.
     * format: edit roomcode Start date and time description
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException when format not allowed
     * @throws IOException when entry is incorrect
     */
    public EditBookingCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length <= 2) {
            throw new DukeException(BookingConstants.INDEXERROR1);
        }
        try {
            index = Integer.parseInt(splitStr[1]);
            index -= 1;
        } catch (NumberFormatException e) {
            throw new DukeException(BookingConstants.INDEXERROR2);
        }
        splitC = input.split(" ", 3);
        textToEdit = splitC[2];
    }

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage)
            throws DukeException, IOException, ParseException {
        bookingList.get(index).setDescription(textToEdit);
        ui.addToOutput(BookingConstants.EDITSUCCESS);
        ui.addToOutput(bookingList.get(index).toString());
        allStorage.getBookingStorage().saveToFile(bookingList);
    }
}
