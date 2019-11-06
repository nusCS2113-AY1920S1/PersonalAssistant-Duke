
package command;

import booking.ApprovedList;
import inventory.Inventory;


import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import user.UserList;


public class ListRoomCommand extends Command {

    //@@author zkchang97
    /**
     * Executes the command to list the rooms in the text file.
     * @param roomList list of rooms
     * @param bookingList bookings list
     * @param ui user interface
     * @param bookingstorage booking storage in command execution
     * @param roomstorage room storage in command execution
     * @throws DukeException when entry is invalid due to formatting
     */

    @Override
    public void execute(UserList userList, Inventory inventory, RoomList roomList,
                        BookingList bookingList, ApprovedList approvedList, Ui ui,
                        Storage userStorage, Storage inventoryStorage,
                        Storage bookingstorage, Storage roomstorage, Storage approvestorage)
            throws DukeException {
        if (roomList.isEmpty()) {
            throw new DukeException("The room list is empty. Please add a room.");
        }
        ui.addToOutput("Here are the rooms in the room list: ");
        for (int i = 0; i < roomList.size(); i++) {
            ui.addToOutput(i + 1 + ". " + roomList.get(i).toString());
        }


    }
}
