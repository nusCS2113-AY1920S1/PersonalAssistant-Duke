
package command;

import booking.ApprovedList;
import inventory.Inventory;

import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Constants;
import storage.Storage;
import ui.Ui;
import user.UnusedLogin;
import user.UserList;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class UnusedLoginCommand extends Command {
    private String[] splitL;

    //@@ AmirAzhar
    /**
     * User login.
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     */
    public UnusedLoginCommand(String input, String[] splitStr) throws DukeException, IOException {
        File membersFile = new File("data\\members.txt");
        membersFile.createNewFile(); //if file already exists, won't create
        if (splitStr.length == 1) {
            throw new DukeException(Constants.UNHAPPY + " OOPS!!! Please login with NUS email and password");
        }
        this.splitL = input.split(" ");
        if (!splitL[1].contains("@u.nus.edu")) {
            throw new DukeException(Constants.UNHAPPY
                    + " OOPS!!! Please use your NUS email, ending with u.nus.edu, for login!");
        }
    }

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        Storage userStorage, Storage inventoryStorage,
                        Storage bookingstorage, Storage roomstorage, Storage approvestorage)
            throws DukeException, IOException, ParseException {
        boolean isVerified = UnusedLogin.verifyLogin(splitL[1], splitL[2], "data\\members.txt");
        if (isVerified) {
            UnusedLogin.setCurrentUser(splitL[1]);
            ui.addToOutput("You have successfully logged in!");
        } else {
            ui.addToOutput(Constants.UNHAPPY + " OOPS!!! You have entered your email/password incorrectly.");
        }
    }
}
