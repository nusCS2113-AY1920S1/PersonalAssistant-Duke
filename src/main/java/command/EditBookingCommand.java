package command;

import booking.Booking;
import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import user.User;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EditBookingCommand extends Command {

    private String name;
    private String[] splitC;
    private String roomcode;
    private LocalDateTime dateTimeStart;
    private String datetimeStartString;

    //@@author Alex-Teo
    /**
     * Edit the description of a booking request
     * format: edit roomcode Start date and time description
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException when format not allowed
     * @throws IOException when entry is incorrect
     */
    public EditBookingCommand(String input, String[] splitStr) throws DukeException, IOException {
        if (splitStr.length <= 1) {
            throw new DukeException("☹ OOPS!!! Please create the booking you want to edit with the following format: name, roomcode, start date and time, description");
        }
        splitC = input.split(" ", 6);
        name = splitC[1];
        roomcode = splitC[2];
        if(!RoomList.checkRoom(roomcode,"data\\roomlist.txt")) {
            throw new DukeException("\u2639 OOPS!!! This room doesn't exist!");
        }
        datetimeStartString = splitC[3] + " " + splitC[4];
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        this.dateTimeStart = LocalDateTime.parse(datetimeStartString, formatterStart);
    }

    @Override
    public void execute(RoomList roomList, BookingList bookingList, Ui ui, Storage bookingstorage, Storage roomstorage, User user)
            throws DukeException, IOException, ParseException {
        for (Booking i: bookingList) {
            if ((i.getVenue() == roomcode) && (i.getDateTimeStart() == dateTimeStart) && (i.getName() == name)) {
                i.setDescription(splitC[4]);
                ui.addToOutput("The description of this request has been changed!");
                bookingstorage.saveToFile(bookingList);
                break;
            }
        }
    }
}
