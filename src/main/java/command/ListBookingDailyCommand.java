
package command;

import booking.ApprovedList;
import inventory.Inventory;

import booking.Booking;
import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.BookingConstants;
import storage.Storage;
import storage.StorageManager;
import ui.Ui;
import user.UserList;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ListBookingDailyCommand extends Command {

    private LocalDate dateStart;

    /**
     * Show all bookings on a certain day.
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException when entry is invalid
     */
    public ListBookingDailyCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length <= 1) {
            throw new DukeException("☹ OOPS!!! Please create your booking with the following format: "
                    + "date");
        }
        String date = input.substring(8);
        try {
            DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.dateStart = LocalDate.parse(date, formatterStart);
        } catch (DateTimeParseException error) {
            throw new DukeException(BookingConstants.DATEERROR);
        }
    }


    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage)
            throws DukeException, IOException, ParseException {
        //int n = 1;
        ui.addToOutput("Here are the bookings: ");
        for (Booking i : bookingList) {
            if (i.getDateStart().equals(this.dateStart)) {
                ui.addToOutput((bookingList.indexOf(i) + 1) + ". " + i.toString());
            }
        }
    }
}

