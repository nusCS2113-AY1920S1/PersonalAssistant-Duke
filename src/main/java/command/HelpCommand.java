package command;

import booking.BookingList;
import inventory.Inventory;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import user.UserList;

public class HelpCommand extends Command {

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList, BookingList bookingList, Ui ui,
                        Storage userStorage, Storage inventoryStorage, Storage bookingstorage, Storage roomstorage) {
        ui.showHelp();
    }
}

