package command;

import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import user.User;

import java.io.IOException;
import java.text.ParseException;

public class ListCommand extends Command{
    @Override
    public void execute(RoomList roomList, BookingList bookingList, Ui ui, Storage bookingStorage, Storage roomStorage, User user) throws DukeException, IOException, ParseException {
        if (bookingList.isEmpty()) {
            throw new DukeException("OOPS! There are no bookings in your list");
        }
        ui.addToOutput("Here are the bookings: ");
        for (int i = 0; i < bookingList.size(); i++) {
            ui.addToOutput(i + 1 + ". " + bookingList.get(i).toString());
        }
    }
}
