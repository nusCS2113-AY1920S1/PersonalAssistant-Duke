package command;

import booking.BookingList;
import exception.DukeException;
import room.RoomList;
import room.Room;
import ui.Ui;
import storage.Storage;
import user.User;

import java.io.IOException;

public class DeleteRoomCommand extends Command {
    private int index;
    private Room deletedRoom;

    public DeleteRoomCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length <= 1) {
            throw new DukeException("OOPS!!! Please add the index for the room you want to delete.");
        }
        input = input.substring(11);
        try {
            this.index = Integer.parseInt(input) - 1;
        } catch (Exception e) {
            throw new DukeException("OOPS!!! Index should be an integer.");
        }
    }

    @Override
    public void execute(RoomList roomList, BookingList bookingList, Ui ui, Storage bookingStorage,
                        Storage roomStorage, User user) throws DukeException {
        if (index < 0 || index > roomList.size() - 1) {
            throw new DukeException("OOPS!!! You have entered an index that is out of bounds.");
        }
        deletedRoom = roomList.get(index);
        roomList.remove(index);
        try {
            roomStorage.saveToFile(roomList);
        } catch (IOException e) {
            throw new DukeException("OOPS!!! An error occurred while deleting your booking!");
        }
        ui.addToOutput("This room has been successfully removed:\n"
                + deletedRoom.toString()
                + "\nYou now have " + roomList.size() + " rooms in your list.");
    }
}
