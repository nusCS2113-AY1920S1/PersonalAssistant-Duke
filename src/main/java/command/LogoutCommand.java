package command;

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

    /**
     * User logout
     *
     * @param input    from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     */
    public LogoutCommand(String input, String[] splitStr) throws DukeException, IOException {
        if (Login.getCurrentUser().equals(" "))
            throw new DukeException("You are not currently logged in!");
    }

    @Override
    public void execute(RoomList roomList, BookingList bookingList, Ui ui, Storage bookingStorage, Storage roomStorage, User user) throws DukeException, IOException, ParseException {
        Login.setCurrentUser(" ");
        ui.addToOutput("You have succesfully logged out!");
    }
}
