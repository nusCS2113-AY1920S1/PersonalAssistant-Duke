
package command;

import booking.ApprovedList;
import booking.BookingList;
import exception.DukeException;
import inventory.Inventory;
import room.RoomList;
import storage.Constants;
import storage.Storage;
import storage.StorageManager;
import ui.Ui;
import user.UserList;

public class LoginCommand extends Command {
    private String[] splitL;

    //@@ AmirAzhar

    /**
     * User login.
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     */
    public LoginCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException(Constants.UNHAPPY + " OOPS!!! Please login with your username!");
        }
        this.splitL = input.split("login ");
    }

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage)
            throws DukeException {
        if (!userList.getLoginStatus()) {
            boolean isValid = UserList.checkExistence(userList, splitL[1]);
            if (isValid) {
                userList.setCurrentUser(splitL[1]);
                userList.login();
                ui.addToOutput("You have successfully logged in as: " + userList.getCurrentUser());
            } else {
                throw new DukeException("The user does not exist!");
            }
        } else {
            throw new DukeException("You are already logged in!");
        }
    }
}
