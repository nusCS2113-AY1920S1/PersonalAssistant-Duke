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

public class FindBookingIndexCommand extends Command {
    private int index;

    public FindBookingIndexCommand(String input, String[] splitStr) throws DukeException, IOException {
        if (splitStr.length <= 1) {
            throw new DukeException("☹ OOPS!!! Please create your booking with the following format: "
                    + "index");
        }
        index = Integer.valueOf(input.substring(10)) - 1;

    }

    @Override
    public void execute(RoomList roomList, BookingList bookingList, Ui ui, Storage bookingstorage,
                        Storage roomstorage, User user) throws DukeException, IOException, ParseException {
        if (index >= bookingList.size()) {
            throw new DukeException("OOPS!!!  No such entry exist!");
        }
        else {
            Booking result = bookingList.get(index);
            ui.addToOutput((index + 1) + ". " + result.toString());
        }
    }
}
