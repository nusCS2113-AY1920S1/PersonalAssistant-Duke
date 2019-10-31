
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
    /**
     * User logout.
     * @param input    from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     */
    public LogoutCommand(String input, String[] splitStr) throws DukeException, IOException {
        if (UnusedLogin.getCurrentUser() == null) {
            throw new DukeException("You are not currently logged in!");
        }
    }

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList, BookingList bookingList, Ui ui,
                        Storage userStorage, Storage inventoryStorage, Storage bookingstorage, Storage roomstorage) {
        userList.loginStatus = false;
        ui.addToOutput("You have successfully logged out from: " + userList.currentUser);
        userList.currentUser = null;
    }
}
