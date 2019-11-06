package command;

import booking.ApprovedList;
import inventory.Inventory;
import inventory.Item;

import booking.Booking;
import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import user.User;
import user.UserList;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ListBookingYearCommand extends Command {

    private LocalDate dateStart;

    /**
     * listing the bookings in a year.
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException input error
     */
    public ListBookingYearCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length <= 1) {
            throw new DukeException("☹ OOPS!!! Please create your booking with the following format: "
                    + "date");
        }
        String date = input.substring(9);
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dateStart = LocalDate.parse(date, formatterStart);

    }

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        Storage userStorage, Storage inventoryStorage, Storage bookingstorage,
                        Storage roomstorage, Storage approvestorage)
            throws DukeException, IOException, ParseException {
        ui.addToOutput("Here are the bookings: ");
        for (Booking i : bookingList) {
            if (i.getStartYear() == this.dateStart.getYear()) {
                ui.addToOutput(bookingList.indexOf(i) + ". " + i.toString() + "\n");
            }
        }

    }
}
