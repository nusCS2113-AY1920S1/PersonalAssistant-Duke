
package command;

import booking.ApprovedList;
import inventory.Inventory;

import booking.Booking;
import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import storage.Constants;
import user.UserList;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
            throw new DukeException("☹ OOPS!!! Please enter the index of the item you want to edit as well as the "
                    + "updated description of your booking!");
        }
        try {
            index = Integer.parseInt(splitStr[1]);
            index -= 1;
        } catch (NumberFormatException e) {
            throw new DukeException(Constants.UNHAPPY + " OOPS!!! Please enter the index in integer format");
        }
        splitC = input.split(" ", 3);
        textToEdit = splitC[2];
    }

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        Storage userStorage, Storage inventoryStorage,
                        Storage bookingstorage, Storage roomstorage, Storage approvestorage)
            throws DukeException, IOException, ParseException {
        bookingList.get(index).setDescription(textToEdit);
        ui.addToOutput("The description of this request has been changed!");
        ui.addToOutput(bookingList.get(index).toString());
        bookingstorage.saveToFile(bookingList);
    }
}
