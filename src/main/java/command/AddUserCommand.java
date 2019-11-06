package command;

import booking.ApprovedList;
import booking.BookingList;
import control.Duke;
import exception.DukeException;
import inventory.Inventory;
import room.RoomList;
import storage.Constants;
import storage.Storage;
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
                        Storage userStorage, Storage inventoryStorage,
                        Storage bookingstorage, Storage roomstorage, Storage approvestorage)
            throws DukeException, IOException {
        if (UserList.checkExistence(userList, splitL[1])) {
            throw new DukeException("Sorry, that user already exists!");
        } else {
            User user = new User(splitL[1]);
            userList.add(user);
            userStorage.saveToFile(userList);
            ui.addToOutput("You have successfully created an account: " + user.getUsername());
        }
    }
}
