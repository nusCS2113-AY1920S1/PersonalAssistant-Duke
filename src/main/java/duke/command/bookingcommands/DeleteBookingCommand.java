package duke.command.bookingcommands;

import duke.list.bookinglist.BookingList;
import duke.command.CommandBooking;
import duke.exception.DukeException;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.Messages.ERROR_MESSAGE_INVALID_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_UNKNOWN_INDEX;


/**
 * Handles the delete command and inherits all the fields and methods of Command parent class.
 */
public class DeleteBookingCommand extends CommandBooking {
    private static String msg = "";

    /**
     * Constructor for class DeleteCommand.
     * @param userInputCommand String containing input command from user
     */
    public DeleteBookingCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    /**
     * Validates that user inputs an integer value for the index.
     * @param input String containing integer input from user for the index
     * @return true if the user inputs an integer and false otherwise
     */
    private static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Processes the delete command to delete booking in the task list.
     * @param bookingList contains the booking list
     * @param ui deals with interactions with the user
     * @param bookingStorage deals with loading tasks from the file and saving tasks in the file
     * @throws DukeException if Duke cannot recognize the user input
     *                      or user inputs an invalid index or the list of tasks is empty
     */
    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws DukeException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInputCommand.trim().equals("deletebooking")) {
            arrayList.add("Booking index cannot be empty!\n" +
                    "       Please enter in the following format:\n" +
                    "       deletebooking <booking_index>");
        } else if (userInputCommand.trim().charAt(13) == ' ') {
            String input = userInputCommand.split("\\s",2)[1].trim();
            if (isParsable(input)) {
                int index = Integer.parseInt(input);
                if (index > bookingList.getSize() || index <= 0) {
                    if (bookingList.getSize() == 0) {
                        arrayList.add("Booking list is empty.");
                    } else {
                        arrayList.add(ERROR_MESSAGE_INVALID_INDEX + bookingList.getSize() + ".");
                    }
                } else {
                    if (bookingList.getSize() - 1 <= 1) {
                        msg = " booking in the list.";
                    } else {
                        msg = " bookings in the list.";
                    }
                    arrayList.add("     Noted. I've removed this booking:\n" + "       " + bookingList.getBookingList().get(index - 1)
                            + "\n" + "Now you have " + (bookingList.getSize() - 1) + msg);

                    bookingList.deleteBooking(index - 1);
                    bookingStorage.saveFile(bookingList);

                }
            } else {
                arrayList.add(ERROR_MESSAGE_UNKNOWN_INDEX);
            }
        } else {
            arrayList.add("Incorrect delete booking command.\n " +
                    "       Please enter in the following format:\n" +
                    "       deletebooking <booking_index>");
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}