
package command;

import inventory.Inventory;
import inventory.Item;

import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import user.Login;
import user.User;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

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
        if (Login.getCurrentUser().equals(" ")) {
            throw new DukeException("You are not currently logged in!");
        }
    }

    @Override
    public void execute(Inventory inventory, RoomList roomList, BookingList bookingList, Ui ui, Storage inventoryStorage, Storage bookingstorage, Storage roomstorage, User user) throws DukeException, IOException, ParseException {
        Login.setCurrentUser(" ");
        ui.addToOutput("You have succesfully logged out!");
    }
}
