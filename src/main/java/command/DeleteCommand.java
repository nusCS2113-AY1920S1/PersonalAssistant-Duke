package command;

import booking.Booking;
import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import user.User;

import java.io.IOException;

//@@author amoschan97
public class DeleteCommand extends Command {
    private int index;
    private Booking deletedBooking;

    //@@ AmosChan97
    /**
     * Deletes a booking from the booking list based on the index.
     * @param input raw user input
     * @param splitStr tokenized user input
     * @throws DukeException if no index is entered or index is not an integer
     */
    public DeleteCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length <= 1) {
            throw new DukeException("OOPS!!! Please add the index for the booking you want to delete");
        }
        input = input.substring(7);
        try {
            this.index = Integer.parseInt(input) - 1;
        } catch (Exception e) {
            throw new DukeException("OOPS!!! Index should be a number");
        }
    }

    @Override
    public void execute(RoomList roomlist, BookingList bookingList, Ui ui, Storage bookingStorage,
                        Storage roomStorage, User user) throws DukeException {
        if (index < 0 || index > bookingList.size() - 1) {
            throw new DukeException("OOPS!!! You have entered an index that is out of bounds");
        }
        deletedBooking = bookingList.get(index);
        bookingList.remove(index);
        try {
            bookingStorage.saveToFile(bookingList);
        } catch (IOException e) {
            throw new DukeException("OOPS!!! An error occurred while deleting your booking!");
        }
        ui.addToOutput("This booking has been successfully removed:\n"
                + deletedBooking.toString()
                + "\nYou now have " + bookingList.size() + " bookings in your list.");
    }

}
