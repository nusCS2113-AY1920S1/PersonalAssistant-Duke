package command;

import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import task.TaskList;
import ui.Ui;
import user.Login;
import user.User;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class LoginCommand extends Command{
    private String[] splitL;

    /**
     * User login
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     */
    public  LoginCommand(String input, String[] splitStr) throws DukeException, IOException {
        File membersFile = new File("data\\members.txt");
        membersFile.createNewFile(); //if file already exists, won't create
        if (splitStr.length == 1) {
            throw new DukeException("\u2639 OOPS!!! Please login with NUS email and password");
        }
        this.splitL = input.split(" ");
        if (!splitL[1].contains("@u.nus.edu")){
            throw new DukeException("\u2639 OOPS!!! Please use your NUS email, ending with u.nus.edu, for login!");
        }
    }

    @Override
    public void execute(RoomList roomList, BookingList bookingList, Ui ui, Storage bookingStorage, Storage roomStorage, User user) throws DukeException, IOException, ParseException {
        boolean isVerified = Login.verifyLogin(splitL[1], splitL[2], "data\\members.txt");
        if (isVerified) {
            user = Login.getUser(splitL[1], "data\\members.txt");
            ui.addToOutput("You have successfully logged in!");
        }
        else {
            ui.addToOutput("\u2639 OOPS!!! You have entered your email/password incorrectly.");
        }
    }
}
