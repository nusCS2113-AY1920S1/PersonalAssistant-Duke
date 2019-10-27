package command;

import booking.Booking;
import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Constants;
import storage.Storage;
import ui.Ui;
import user.User;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FindBookingCommand extends Command {
    private String name;
    private String[] splitC;
    private String roomcode;
    private LocalDateTime dateTimeStart;
    private String datetimeStartString;

    /**
     * Find the booking request matching the room, date, time and user.
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException format error
     * @throws IOException entry error
     */
    public FindBookingCommand(String input, String[] splitStr) throws DukeException, IOException {
        if (splitStr.length <= 1) {
            throw new DukeException("☹ OOPS!!! Please create the booking you want to edit with the following format: "
                    + "name, roomcode, start date and time");
        }
        splitC = input.split(" ", 5);
        name = splitC[1];
        roomcode = splitC[2];
        datetimeStartString = splitC[3] + " " + splitC[4];
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        this.dateTimeStart = LocalDateTime.parse(datetimeStartString, formatterStart);
    }

    @Override
    public void execute(RoomList roomList, BookingList bookingList, Ui ui, Storage bookingstorage,
                        Storage roomstorage, User user) throws DukeException, IOException, ParseException {
        boolean valid = roomList.checkRoom(roomcode);
        if (!valid) {
            throw new DukeException(Constants.UNHAPPY + " OOPS!!! This room doesn't exist!");
        }
        for (Booking i: bookingList) {
            if ((i.getVenue() == roomcode) && (i.getDateTimeStart() == dateTimeStart)) {
                int entry = bookingList.indexOf(i) + 1;
                ui.addToOutput(entry + ". " + i.toString());
                break;
            }
        }
    }
}