package command;

import exception.DukeException;
import room.RoomList;
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
    private String[] splitD;
    private String[] splitE;
    private String[] datetime;
    private String timeStart;
    private Storage storage;
    private String room;
    private String description;
    private BookingList bookingList;
    private String name;

    /**
     * Create new booking request.
     * format is add DESCRIPTION /at ROOM_CODE /from DATE TIMESTART /to TIMEEND
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     * @throws IOException when entry is incorrect
     */
    public AddBookingCommand(String input, String[] splitStr) throws DukeException, IOException {
        if (splitStr.length <= 8) {
            throw new DukeException("☹ OOPS!!! Please create your booking with the following format: " +
                    "name, description, roomcode, date and time");
        }
        if (!input.contains(" /from ")) {
            throw new DukeException("Please add the date and time for your booking");
        }
        if (!input.contains(" /to ")) {
            throw new DukeException("Please add the end time of your booking");
        }

        String temp = input.substring(4); // name description /at roomcode /from dd/mm/yyyy hhmm /to dd/mm/yyyy hhmm
        splitC = temp.split(" /at ", 2); // splitC[] = {name, description, roomcode, dd/mm/yyyy hhmm /to dd/mm/yyyy hhmm)
        if (splitC.length < 2) {
            throw new DukeException("☹ OOPS!!! Please create your booking with the following format: " +
                    "description, roomcode, date and time");
        }
        splitE = splitC[0].split(" ", 2);
        this.name = splitE[0];
        this.description = splitE[1]; // description
        splitD = splitC[1].split(" /from ", 2);
        this.room = splitD[0]; // roomcode
        this.datetime = splitD[1].split(" /to ", 2); // datetime[] = {dd/mm/yyyy hhmm, dd/mm/yyyy hhmm}
        this.timeStart = datetime[0];
    }

    /**
     * Executes the command to add a room to the system.
     * @param roomList room list
     * @param bookingList bookings list
     * @param ui user interface
     * @param bookingstorage booking storage in command execution
     * @param roomstorage room storage in command execution
     * @param user current user
     * @throws DukeException if a clash in booking is found
     * @throws IOException if input entry is incorrect
     */
    @Override
    public void execute(RoomList roomList, BookingList bookingList, Ui ui, Storage bookingstorage,
                        Storage roomstorage, User user) throws DukeException, IOException, ParseException {
        Booking newBooking = new Booking(name, room, description, timeStart, datetime[1]);
        boolean clash = BookingList.checkBooking(bookingList, room, timeStart, datetime[1]);
        if (clash) {
            throw new DukeException("☹ OOPS!!! This slot is already filled, please choose another vacant one");
        }
        bookingList.add(newBooking);
        bookingstorage.saveToFile(bookingList);
        ui.addToOutput("Got it. I've added this request:\n"
                + newBooking.toString() + "\n"
                + "Now there are " + bookingList.size() + " request(s) in the list.");
    }
}
