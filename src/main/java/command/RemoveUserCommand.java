package command;

import booking.ApprovedList;
import booking.BookingList;
import exception.DukeException;
import inventory.Inventory;
import room.RoomList;
import storage.Constants;
import storage.StorageManager;
import ui.Ui;
import user.UserList;

import java.io.IOException;

//@@author AmirAzhar
public class RemoveUserCommand extends Command {
    private String[] splitL;

    /**
     * User login.
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     */
    public RemoveUserCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException(Constants.UNHAPPY + " OOPS!!! Please enter a username you would like to remove!\n"
                    + "rmuser <username>");
        }
        this.splitL = input.split("rmuser ");
    }

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage)
            throws DukeException, IOException {
        if (userList.doesExist(splitL[1].trim())) {
            if (splitL[1].trim().equals(userList.getCurrentUser())) {
                throw new DukeException("OOPS! You are currently logged in as " + userList.getCurrentUser().trim() + "!");
            } else {
                ui.addToOutput("You have successfully removed user: " + splitL[1].trim());
                userList.removeUser(splitL[1].trim());
                allStorage.getUserStorage().saveToFile(userList);
            }
        } else {
            throw new DukeException("The user you are trying to remove does not exist!");
        }

    }
}
