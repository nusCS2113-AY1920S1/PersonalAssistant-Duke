package command;

import exception.DukeException;
import storage.Storage;
import ui.Ui;
import booking.Booking;
import booking.BookingList;
import user.Login;
import user.User;

import java.io.IOException;
import java.text.ParseException;

public class AddBookingCommand extends Command {
    private String[] splitC;
    private String[] datetime;
    private String timeStart;
    private Storage storage;
    private String room;
    private String description;
    private BookingList bookingList;

    /**
     * Create new booking request
     * format is add DESCRIPTION /at ROOM_CODE /at DATE TIMESTART /to TIMEEND
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     * @throws IOException when entry is incorrect
     */
    public  AddBookingCommand(String input, String[] splitStr) throws DukeException, IOException {
        if (splitStr.length <= 8)
            throw new DukeException("☹ OOPS!!! Please create your booking with the following format: description, roomcode, date and time");
        if (!input.contains(" /at ")) {
            throw new DukeException("Please add the date and time for your booking");
        }
        if (!input.contains(" /to ")) {
            throw new DukeException("Please add the end time of your booking");
        }
        String temp = input.substring(4); // description /at roomcode /at dd/mm/yyyy hhmm /to dd/mm/yyyy hhmm
        splitC = temp.split(" /at ", 3); // splitC[] = {description, roomcode, dd/mm/yyyy hhmm /to dd/mm/yyyy hhmm)
        if (splitC.length < 3) {
            throw new DukeException("☹ OOPS!!! Please create your booking with the following format: description, roomcode, date and time");
        }
        this.description = splitC[0]; // description
        this.room = splitC[1]; // roomcode
        this.datetime = splitC[2].split(" /to ", 2); // datetime[] = {dd/mm/yyyy hhmm, dd/mm/yyyy hhmm}
        this.timeStart = datetime[0];
    }

    @Override
    public void execute(BookingList bookingList, Ui ui, Storage storage, User user) throws DukeException, IOException, ParseException {
        Booking newBooking = new Booking(room, description, timeStart, datetime[1], user);
        boolean clash = BookingList.checkBooking(bookingList, room, timeStart, datetime[1]);
        if (clash)
            throw new DukeException("☹ OOPS!!! This slot is already filled, please choose another vacant one");
        bookingList.add(newBooking);
        storage.saveToFile(bookingList);
        ui.addToOutput("Got it. I've added this request:\n"
                + newBooking.toString() + "\n"
                + "Now there are " + bookingList.size() + " request(s) in the list.");
    }
}
