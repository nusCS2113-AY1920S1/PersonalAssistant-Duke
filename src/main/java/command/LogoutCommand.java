
package command;

import booking.ApprovedList;
import booking.BookingList;
import exception.DukeException;
import inventory.Inventory;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import user.UserList;

public class LogoutCommand extends Command {
    private String[] splitL;

    //@@ AmirAzhar

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        Storage userStorage, Storage inventoryStorage, Storage bookingstorage,
                        Storage roomstorage, Storage approvestorage)
            throws DukeException {
        if (userList.getLoginStatus() == false) {
            throw new DukeException("OOPS!!! You are not currently logged in!");
        }
        userList.logout();
        ui.addToOutput("You have successfully logged out from: " + userList.getCurrentUser());
        userList.setCurrentUser(null);
    }
}
