package command;

import booking.BookingList;
import exception.DukeException;
import storage.Storage;
import room.AddRoom;
import room.RoomList;
import ui.Ui;
import user.User;

import java.io.IOException;
import java.text.ParseException;

public class AddRoomCommand extends Command {
    String[] date;
    String[] timeslot;

    /**
     * Creates anew room entry in the list of rooms.
     * format is addroom ROOMCODE /date DD/MM/YYYY /timeslot HH:MM AM/PM to HH:MM AM/PM
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException when format is incorrect
     */
    public AddRoomCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException("Please enter the following to add a room:\n"
                    + "addroom ROOMCODE /date DATE /timeslot TIMESLOT.\n"
                    + "DATE format: DD/MM/YYYY.\n"
                    + "TIMESLOT format: HH:MM AM/PM to HH:MM AM/PM.");
        }
        String tempAR = input.substring(8);
        if (!tempAR.contains(" /date ")) {
            throw new DukeException("Please enter correct date for the room.");
        }
        if (!tempAR.contains(" /timeslot ")) {
            throw new DukeException("Please enter a timeslot for the room.");
        }
        this.date = tempAR.split(" /date ");
        this.timeslot = date[1].split(" /timeslot ");
    }

    public void execute(RoomList rooms,  Ui ui, Storage roomStorage)
            throws DukeException, IOException, ParseException {
        AddRoom addroom = new AddRoom(date[0], timeslot[0], timeslot[1]);
        rooms.add(addroom);
        roomStorage.saveToFile(rooms);
        ui.addToOutput("Got it, I've added this room.\n"
            + addroom.toString() + "\n" + "Now you have " + rooms.size() + " room(s) in the list.");
    }
}
