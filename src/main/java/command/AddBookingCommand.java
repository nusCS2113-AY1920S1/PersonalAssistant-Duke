package command;

import booking.ApprovedList;
import inventory.Inventory;
import exception.DukeException;
import room.RoomList;
import storage.Constants;
import storage.Storage;
import storage.StorageManager;
import ui.Ui;
import booking.Booking;
import booking.BookingList;
import user.UserList;

import java.io.IOException;
import java.text.ParseException;

public class AddBookingCommand extends Command {
    private String[] splitC;
    private String[] splitD;
    private String[] splitE;
    private String[] datetime;
    private String timeStart;
    private String timeEnd;
    private Storage storage;
    private String room;
    private String description;
    private BookingList bookingList;
    private String name;

    //@@author Alex-Teo
    /**
     * Create new booking request.
     * format is add NAME DESCRIPTION /at ROOM_CODE /from DATE TIMESTART /to DATE TIMEEND
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if format not followed
     * @throws IOException when entry is incorrect
     */
    public AddBookingCommand(String input, String[] splitStr) throws DukeException, IOException {
        if (!input.contains("/from") || !input.contains("/at") || !input.contains("/to")) {
            throw new DukeException(Constants.UNHAPPY
                   + "OOPS!!! Please create your booking with the following format: "
                    + "add NAME DESCRIPTION /at ROOM_CODE /from DATE TIMESTART /to TIMEEND"
                   + ", DATE TIME format is dd/mm/yyyy HHMM ");
        }

        String temp = input.substring(3).trim(); // name description /at roomcode /from dd/mm/yyyy hhmm /to hhmm
        splitC = temp.split("/at", 2); //splitC[] = {name, description, roomcode, dd/mm/yyyy hhmm /to hhmm)
        if (splitC.length < 2) {
            throw new DukeException(Constants.UNHAPPY
                    + "OOPS!!! Please create your booking with the following format: "
                    + "description, roomcode, date and time");
        }
        splitE = splitC[0].split(" ", 2);
        this.name = splitE[0].trim();
        this.description = splitE[1]; // description
        splitD = splitC[1].split("/from", 2);
        this.room = splitD[0].trim(); // roomcode
        this.datetime = splitD[1].split("/to", 2); // datetime[] = {dd/mm/yyyy hhmm, dd/mm/yyyy hhmm}
        this.timeStart = datetime[0].trim();
        this.timeEnd = datetime[1].trim();
    }

    /**
     * Executes the command to add a room to the system.
     * @param roomList room list
     * @param bookingList bookings list
     * @param ui user interface
     * @param allStorage all the storage in command execution
     * @throws DukeException if a clash in booking is found
     * @throws IOException if input entry is incorrect
     */
    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage)
            throws DukeException, IOException, ParseException {
        Booking newBooking = new Booking(name, room, description, timeStart, timeEnd);
        boolean clash = BookingList.checkBooking(bookingList, room, timeStart, timeEnd);
        if (clash) {
            throw new DukeException(Constants.UNHAPPY
                    + " OOPS!!! This slot is already filled, please choose another vacant one");
        }
        boolean valid = roomList.checkRoom(room);
        if (!valid) {
            throw new DukeException(Constants.UNHAPPY + " OOPS!!! This room doesn't exist!");
        }
        bookingList.add(newBooking);
        allStorage.getBookingStorage().saveToFile(bookingList);
        ui.addToOutput("Got it. I've added this request:\n"
                + newBooking.toString() + "\n"
                + "Now there are " + bookingList.size() + " request(s) in the list.");
    }
}
