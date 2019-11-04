
package command;

import booking.BookingList;
import exception.DukeException;
import inventory.Inventory;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import user.UnusedLogin;
import user.UserList;

import java.io.IOException;

public class LogoutCommand extends Command {
    private String[] splitL;

    //@@ AmirAzhar

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList, BookingList bookingList, Ui ui,
                        Storage userStorage, Storage inventoryStorage, Storage bookingstorage, Storage roomstorage) throws DukeException {
        if (userList.currentUser == null)
            throw new DukeException("You are not currently logged in!");
        else {
            userList.loginStatus = false;
            ui.addToOutput("You have successfully logged out from: " + userList.currentUser);
            userList.currentUser = null;
        }
    }
}
