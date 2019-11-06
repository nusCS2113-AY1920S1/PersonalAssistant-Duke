
package command;

import booking.ApprovedList;
import inventory.Inventory;

import booking.Booking;
import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import user.UserList;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class ListBookingMonthCommand extends Command {

    private LocalDate dateStart;
    private int monthStart;

    /**
     * Show all bookings in a certain month.
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException when entry is ivalid due to format
     */
    public ListBookingMonthCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length <= 1) {
            throw new DukeException("☹ OOPS!!! Please create your booking with the following format: "
                    + "month value");
        }
        this.monthStart = Integer.parseInt(input.substring(10));
    }


    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        Storage userStorage, Storage inventoryStorage,
                        Storage bookingstorage, Storage roomstorage, Storage approvestorage)
            throws DukeException, IOException, ParseException {

        ui.addToOutput("Here are the bookings: ");
        for (Booking i : bookingList) {
            if (i.getStartMonth() == this.monthStart) {
                ui.addToOutput((bookingList.indexOf(i) + 1) + ". " + i.toString() + "\n");

            }
        }
    }
}

