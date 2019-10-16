<<<<<<< HEAD:src/main/oldduke/dukecommands/DeleteCommand.java
package duke.command.dukecommands;

import duke.command.Command;
import duke.storage.Storage;
import duke.list.tasklist.TaskList;
=======
package duke.command.bookingcommands;

import duke.list.BookingList;
import duke.command.CommandBooking;
import duke.exception.DukeException;
import duke.storage.BookingStorage;
>>>>>>> branch-booking-list:src/main/java/duke/command/bookingcommands/DeleteBookingCommand.java
import duke.ui.Ui;

import static duke.common.Messages.MESSAGE_FOLLOWUP_EMPTY_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_EMPTY_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_INVALID_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_UNKNOWN_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;


/**
 * Handles the delete command and inherits all the fields and methods of Command parent class.
 */
public class DeleteBookingCommand extends CommandBooking {

    /**
     * Constructor for class DeleteCommand.
     * @param userInput String containing input command from user
     */
<<<<<<< HEAD:src/main/oldduke/dukecommands/DeleteCommand.java
    public DeleteCommand(String userInput) {
        this.userInput = userInput;
=======
    public DeleteBookingCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
>>>>>>> branch-booking-list:src/main/java/duke/command/bookingcommands/DeleteBookingCommand.java
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
<<<<<<< HEAD:src/main/oldduke/dukecommands/DeleteCommand.java
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (userInput.trim().equals(COMMAND_DELETE)) {
            throw new DukeException(ERROR_MESSAGE_EMPTY_INDEX + MESSAGE_FOLLOWUP_EMPTY_INDEX);
        } else if (userInput.trim().charAt(6) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (isParsable(description)) {
=======
    public void execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws DukeException {
        if (userInputCommand.trim().equals("deletebooking")) {
            throw new DukeException(ERROR_MESSAGE_EMPTY_INDEX + MESSAGE_FOLLOWUP_EMPTY_INDEX);
        } else if (userInputCommand.trim().charAt(13) == ' ') {
            String input = userInputCommand.split("\\s",2)[1].trim();
            if (isParsable(input)) {
>>>>>>> branch-booking-list:src/main/java/duke/command/bookingcommands/DeleteBookingCommand.java
                //converting string to integer
                int index = Integer.parseInt(input);
                if (index > bookingList.getSize() || index <= 0) {
                    if (bookingList.getSize() == 0) {
                        throw new DukeException("Booking list is empty.");
                    } else {
                        throw new DukeException(ERROR_MESSAGE_INVALID_INDEX + bookingList.getSize() + ".");
                    }
                } else {
                    bookingList.deleteBooking(index - 1);
                    bookingStorage.saveFile(bookingList);
                }
            } else {
                throw new DukeException(ERROR_MESSAGE_UNKNOWN_INDEX);
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
