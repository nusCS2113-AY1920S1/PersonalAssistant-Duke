package command;

import exception.DukeException;
import storage.Storage;
import ui.Ui;
import booking.BookingList;
import user.User;

import java.io.IOException;

/**
 * command.Command to exit control.Duke programme.
 */
public class ByeCommand extends Command {

    /**
     * If "bye" is entered.
     * @param bookingList task list
     * @param ui user interface
     * @param bookingStorage handles read write of text file
     * @throws IOException if IOException found
     * @throws DukeException if control.Duke specific exception found
     */
    @Override
    public void execute(BookingList bookingList, Ui ui, Storage bookingStorage, User user) {
        ui.showBye();
        this.isExit = true;
    }
}
