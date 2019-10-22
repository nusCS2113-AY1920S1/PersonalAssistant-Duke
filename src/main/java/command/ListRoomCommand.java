package command;


import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import storage.Storage;
import ui.Ui;
import user.User;


public class ListRoomCommand extends Command {

    /**
     * Executes the command to list the rooms in the text file
     * @param roomList list of rooms
     * @param bookingList bookings list
     * @param ui user interface
     * @param bookingstorage booking storage in command execution
     * @param roomstorage room storage in command execution
     * @param user Current user
     * @throws DukeException
     */
    @Override
    public void execute(RoomList roomList, BookingList bookingList, Ui ui, Storage bookingstorage, Storage roomstorage, User user) throws DukeException {
        if (roomList.isEmpty()) {
            throw new DukeException("The room list is empty. Please add a room.");
        }
        ui.addToOutput("Here are the rooms in the room list: ");
        for (int i = 0; i < roomList.size(); i++) {
            ui.addToOutput(i + 1 + ". " + roomList.get(i).toString());
        }


    }
}
