package command;

import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import user.User;

//@@author amoschan97
public class ListCommand extends Command {
    /**
     * Prints to the UI a list containing all bookings made.
     * @param roomList list of rooms
     * @param bookingList bookings list
     * @param ui user interface
     * @param bookingStorage handles read write of bookings list file
     * @param roomStorage handles read write of room list file
     * @param user Current user
     * @throws DukeException if there are no bookings
     */
    @Override
    public void execute(RoomList roomList, BookingList bookingList, Ui ui, Storage bookingStorage,
                        Storage roomStorage, User user) throws DukeException {
        if (bookingList.isEmpty()) {
            throw new DukeException("OOPS! There are no bookings in your list");
        }
        ui.addToOutput("Here are the bookings: ");
        for (int i = 0; i < bookingList.size(); i++) {
            ui.addToOutput(i + 1 + ". " + bookingList.get(i).toString());
        }
    }
}
