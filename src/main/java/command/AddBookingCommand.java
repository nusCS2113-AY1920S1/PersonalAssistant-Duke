package command;

import exception.DukeException;

import java.io.File;
import java.io.IOException;

public class AddBookingCommand extends Command {
    private String[] splitC;

    public  AddBookingCommand(String input, String[] splitStr) throws DukeException, IOException {
        File bookingFile = new File("data\\bookingslist.txt");
        bookingFile.createNewFile();
        if (splitStr.length == 1)
            throw new DukeException("☹ OOPS!!! Please create your booking with the following format: username, roomcode, description, date and time");
        this.splitC = input.split(" ", 4);
        /*if(Login.checkExistence(splitC[1],"data\\members.txt"))
            throw new DukeException("☹ OOPS!!! There is already an account registered under that email!");*/
        if (!splitC[0].contains("@u.nus.edu"))
            throw new DukeException("☹ OOPS!!! Please input a valid user account");
        if (splitC[1].matches("[0-9]+") || splitC[2].matches("[a-z]+") || splitC[2].matches("[A-Z]+"))
            throw new DukeException("☹ OOPS!!! This room does not exist.  Please input a valid roomcode.");
        if (!splitC[3].contains("A") && !splitC[4].contains("R") && !splitC[4].contains("C"))
            throw new DukeException("☹ OOPS!!! This slot is already filled, please choose another vacant one");

    }
}
