
package command;

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
            throw new DukeException("☹ OOPS!!! Please enter the month you want to view in the format <MM/YYYY>.");
        }
        this.monthStart = Integer.parseInt(input.substring(10));
        /*DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dateStart = LocalDate.parse(stringDate, formatterStart);
        this.monthStart = dateStart.getMonth().getValue();*/
    }


    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList, BookingList bookingList, Ui ui,
                        Storage userStorage, Storage inventoryStorage, Storage bookingstorage, Storage roomstorage)
            throws DukeException, IOException, ParseException {
        boolean bookingExists = false;
        for (Booking i : bookingList) {
            if (i.getStartMonth() == this.monthStart) {
                ui.addToOutput((bookingList.indexOf(i) + 1) + ". " + i.toString() + "\n");
                bookingExists = true;
            }
        }
        if (!bookingExists) {
            throw new DukeException("OOPS!! There are no bookings for this month");
        }
    }
}

