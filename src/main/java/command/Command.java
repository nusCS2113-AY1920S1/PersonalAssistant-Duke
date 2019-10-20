package command;

import exception.DukeException;
import room.RoomList;
import storage.Storage;
import task.TaskList;
import ui.Ui;
import booking.BookingList;
import user.User;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Abstract class from which all Commands are the extended from.
 */
public abstract class Command {
    protected boolean isExit;

    /**
     * Execute command logic.
     * @param bookingList bookings list
     * @param ui user interface
     * @param bookingStorage handles read write of text file
     * @param user Current user
     * @throws DukeException if control.Duke specific exception found
     * @throws IOException if IO exception found
     */
    public void execute(BookingList bookingList, Ui ui, Storage bookingStorage, User user)
            throws DukeException, IOException, ParseException {
    }

    public void execute(RoomList roomList, Ui ui, Storage roomStorage) throws IOException {

    }

    public boolean isExit() {
        return this.isExit;
    }

    /**
     * Check if date-time input is in valid format.
     * @param dateTime date and time for some tasks
     * @return true is input is valid
     */
    protected static boolean isValidDateTime(String dateTime) {
        SimpleDateFormat dateTimeFormat =  new SimpleDateFormat("d/M/yyyy HHmm");
        try {
            dateTimeFormat.parse(dateTime);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    protected static boolean isAdd (String dateTime, TaskList tasks) {
        SimpleDateFormat dateTimeFormat =  new SimpleDateFormat("d/M/yyyy HHmm");
        boolean found = false;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).toString().contains(dateTime)) {
                found = true;
                break;
            }
        }
        return found;
    }
}
