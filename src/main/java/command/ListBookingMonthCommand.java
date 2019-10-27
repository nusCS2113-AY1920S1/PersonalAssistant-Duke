package command;

import booking.Booking;
import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import user.User;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class ListBookingMonthCommand extends Command {

    private LocalDate dateStart;
    private Month monthStart;

    /**
     * Show all bookings in a certain month.
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException when entry is ivalid due to format
     */
    public ListBookingMonthCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length <= 1) {
            throw new DukeException("☹ OOPS!!! Please create your booking with the following format: "
                    + "date");
        }
        String stringDate = input.substring(10);
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dateStart = LocalDate.parse(stringDate, formatterStart);
        this.monthStart = dateStart.getMonth();
    }


    @Override
    public void execute(RoomList roomList, BookingList bookingList, Ui ui, Storage bookingstorage,
                        Storage roomstorage, User user) throws DukeException, IOException, ParseException {
        int n = 1;
        for (Booking i : bookingList) {
            if (i.getStartMonth() == this.monthStart) {
                ui.addToOutput(n + ". " + i.toString() + "\n");
                n += 1;
            }
        }
    }
}

