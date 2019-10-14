package command;

import exception.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;
import user.Booking;
import user.BookingList;
import user.Login;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class AddBookingCommand extends Command {
    private String[] splitC;
    private String[] datetime;
    private String timeStart;
    private Storage storage;
    private BookingList bookingList;

    /**
     * Create new booking request
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     * @throws IOException when entry is incorrect
     */
    public  AddBookingCommand(String input, String[] splitStr) throws DukeException, IOException {
        /*File bookingFile = new File("data\\bookingslist.txt");
        bookingFile.createNewFile();
        storage = new Storage("data\\bookingslist.txt");
        bookingList = new BookingList(storage.load());*/
        if (splitStr.length == 1)
            throw new DukeException("☹ OOPS!!! Please create your booking with the following format: username, account type, roomcode, description, date and time");
        this.splitC = input.split(" ", 6);
        this.datetime = splitC[5].split(" ", 3);
        this.timeStart = datetime[0] + " " + datetime[1];
        if(!Login.checkUsername(splitC[1],"data\\members.txt"))
            throw new DukeException("☹ OOPS!!! Please input a valid username!");
        /*if (splitC[1].matches("[0-9]+") || splitC[2].matches("[a-z]+") || splitC[2].matches("[A-Z]+"))//when we get a room list
            throw new DukeException("☹ OOPS!!! This room does not exist.  Please input a valid roomcode.");*/



    }

    public void execute(BookingList bookingList, Ui ui, Storage storage) throws DukeException, IOException, ParseException {
        boolean clash = BookingList.checkBooking(bookingList, splitC[3], timeStart, datetime[2]);
        if (clash)
            throw new DukeException("☹ OOPS!!! This slot is already filled, please choose another vacant one");
        Booking newBooking = newBooking = new Booking(splitC[1], splitC[2], splitC[3], splitC[4], this.timeStart, datetime[2]);;
        bookingList.add(newBooking);
        storage.saveToFile(bookingList);
        ui.addToOutput("Got it. I've added this request:\n"
                + newBooking.toString() + "\n"
                + "Now there are " + bookingList.size() + " request(s) in the list.");
        /*BufferedWriter bw = new BufferedWriter(new FileWriter("data\\bookingslist.txt", true));
        bw.write(newBooking.toWriteFile());
        bw.newLine();
        bw.close();
        ui.addToOutput("Your booking has been successfully requested!");*/
    }
}
