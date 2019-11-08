package command;

import booking.ApprovedList;
import booking.BookingList;
import inventory.Inventory;
import room.RoomList;
import storage.Storage;
import storage.StorageManager;
import ui.Ui;
import user.UserList;

public class HelpCommand extends Command {

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage) {
        ui.showHelp();
    }
}

