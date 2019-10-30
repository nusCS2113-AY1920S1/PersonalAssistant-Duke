package command;

import booking.BookingList;
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
        this.splitL = input.split(" ");
    }

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList, BookingList bookingList, Ui ui,
                        Storage userStorage, Storage inventoryStorage, Storage bookingstorage, Storage roomstorage)
            throws DukeException, IOException {
        User user = new User(splitL[1]);
        userList.add(user);
        userStorage.saveToFile(userList);
        ui.addToOutput("You have successfully created an account: " + user.getUsername());
    }
}
