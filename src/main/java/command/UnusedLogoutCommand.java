
package command;

import booking.ApprovedList;
import inventory.Inventory;

import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import storage.StorageManager;
import ui.Ui;
import user.UnusedLogin;
import user.UserList;

import java.io.IOException;

public class UnusedLogoutCommand extends Command {
    private String[] splitL;

    //@@ AmirAzhar
    /**
     * User logout.
     * @param input    from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     */
    public UnusedLogoutCommand(String input, String[] splitStr) throws DukeException, IOException {
        if (UnusedLogin.getCurrentUser().equals(" ")) {
            throw new DukeException("You are not currently logged in!");
        }
    }

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage) {
        UnusedLogin.setCurrentUser(" ");
        ui.addToOutput("You have succesfully logged out!");
    }
}
