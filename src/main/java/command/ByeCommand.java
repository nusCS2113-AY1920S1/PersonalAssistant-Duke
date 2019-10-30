package command;

import inventory.Inventory;
import inventory.Item;

import exception.DukeException;
import room.RoomList;
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
     * @throws IOException if IOException found
     * @throws DukeException if control.Duke specific exception found
     */
    @Override
    public void execute(Inventory inventory, RoomList roomList, BookingList bookingList, Ui ui,
                        Storage inventoryStorage, Storage bookingstorage, Storage roomstorage, User user) {
        ui.showBye();
        this.isExit = true;
    }
}
