package command;

import booking.ApprovedList;
import inventory.Inventory;

import exception.DukeException;
import room.RoomList;
import storage.Storage;
import storage.StorageManager;
import ui.Ui;
import booking.BookingList;
import user.UserList;

import java.io.IOException;

/**
 * command.Command to exit control.Duke programme.
 */
public class ByeCommand extends Command {

    /**
     * If "bye" is entered.
     * @param bookingList task list
     * @param ui user interface
     * @throws IOException if IOException found
     * @throws DukeException if control.Duke specific exception found
     */
    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage) {
        ui.showBye();
        this.isExit = true;
    }
}
