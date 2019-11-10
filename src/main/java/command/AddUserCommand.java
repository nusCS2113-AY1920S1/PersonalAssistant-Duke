package command;

import booking.ApprovedList;
import booking.BookingList;
import exception.DukeException;
import inventory.Inventory;
import room.RoomList;
import storage.Constants;
import storage.StorageManager;
import ui.Ui;
import user.User;
import user.UserList;

import java.io.IOException;

public class AddUserCommand extends Command {
    private String[] splitL;

    //@@ AmirAzhar
    /**
     * User login.
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     */
    public AddUserCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException(Constants.UNHAPPY + " OOPS!!! Please enter a username u would like to register!");
        }
        this.splitL = input.split("adduser ");
    }

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage)
            throws DukeException, IOException {
        if (userList.addUser(splitL[1])) {
            allStorage.getUserStorage().saveToFile(userList);
            ui.addToOutput("You have successfully created an account: " + splitL[1]);
        } else {
            throw new DukeException("Sorry, that user already exists!");
        }
    }
}
