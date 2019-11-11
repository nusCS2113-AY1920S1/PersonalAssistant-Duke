
package command;

import booking.ApprovedList;
import inventory.Inventory;

import booking.BookingList;
import exception.DukeException;
import room.Room;
import room.RoomList;
import storage.Constants;
import storage.StorageManager;
import ui.Ui;
import user.UserList;

import java.io.IOException;

public class AddRoomCommand extends Command {

    private String[] splitC;
    private String roomcode;
    private int capacity;

    //@@author zkchang97
    /**
     * Creates a new room entry in the list of rooms.
     * Format is addroom ROOMCODE CAPACITY
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException when format is incorrect
     */
    public AddRoomCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException(Constants.ADDROOMFORMAT);
        }
        String tempAR = input.substring(8).trim();
        splitC = tempAR.split(" ");
        this.roomcode = splitC[Constants.ROOMCODE]; // ROOMCODE
        String capString = tempAR.substring(roomcode.length()).trim();
        try {
            this.capacity = Integer.parseInt(capString);
        } catch (NumberFormatException e) {
            throw new DukeException("Capacity should be an integer");
        }
        if (capacity < 0) {
            throw new DukeException(Constants.INVALIDCAPACITY);
        }
    }

    /**
     * Executes the command to add a room to the system.
     * @param roomList room list
     * @param bookingList bookings list
     * @param ui user interface
     * @param allStorage booking storage in command execution
     * @throws IOException if input entry is incorrect
     */
    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        StorageManager allStorage)
            throws IOException, DukeException {
        Room addroom = new Room(roomcode, capacity);
        boolean clash = RoomList.checkRoom(roomList, roomcode);
        if (clash) {
            throw new DukeException(":-("
                    + " OOPS!!! This room is already added, please add another one.");
        }
        roomList.add(addroom);
        allStorage.getRoomStorage().saveToFile(roomList);
        ui.addToOutput("Got it, I've added this room.\n"
            + addroom.toString() + "\n" + "Now you have " + roomList.size() + " room(s) in the list.");
    }
}
