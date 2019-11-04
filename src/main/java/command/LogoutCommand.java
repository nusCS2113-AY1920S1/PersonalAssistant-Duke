
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
     * Logout.
     * @param userList List of users.
     * @param inventory List of items
     * @param roomList List of rooms
     * @param bookingList bookings list
     * @param ui user interface
     * @param userStorage manage storage for users
     * @param inventoryStorage manage inventory storage
     * @param bookingstorage manage bookings storage
     * @param roomstorage manage room storage
     */
    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList, BookingList bookingList, Ui ui,
                        Storage userStorage, Storage inventoryStorage, Storage bookingstorage, Storage roomstorage) throws DukeException {
        if(userList.getLoginStatus() == false) {
            throw new DukeException("OOPS!!! You are not currently logged in!");
        }
        userList.logout();
        ui.addToOutput("You have successfully logged out from: " + userList.getCurrentUser());
        userList.setCurrentUser(null) ;
    }
}
