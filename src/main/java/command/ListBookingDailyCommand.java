
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
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dateStart = LocalDate.parse(date, formatterStart);
    }


    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList, BookingList bookingList, Ui ui,
                        Storage userStorage, Storage inventoryStorage, Storage bookingstorage, Storage roomstorage)
            throws DukeException, IOException, ParseException {
        int n = 1;
        for (Booking i : bookingList) {
            if (i.getDateStart() == this.dateStart) {
                ui.addToOutput(n + ". " + i.toString() + "\n");
                n += 1;
            }
        }
    }
}

