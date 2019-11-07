package duke.logic.command.bookingcommands;

import duke.logic.command.Command;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.logging.*;

import static duke.common.BookingMessages.*;
import static duke.common.Messages.ERROR_MESSAGE_INVALID_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_UNKNOWN_INDEX;


/**
 * Handles the delete booking command.
 */
public class DeleteBookingCommand extends Command<BookingList, Ui, BookingStorage> {
    private static String msg = "";
    private static final Logger logger = Logger.getLogger(DeleteBookingCommand.class.getName());

    /**
     * Set up a logger to log important information.
     */
    private static void setupLogger() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.INFO);

        try {
            FileHandler fh = new FileHandler("logFile.log", true);
            fh.setLevel(Level.INFO);
            logger.addHandler(fh);
        } catch (java.io.IOException e) {
            logger.log(Level.SEVERE, "File logger is not working.", e);
        }
    }

    /**
     * Constructor for class DeleteBookingCommand.
     *
     * @param userInput string containing the input from the user
     */
    public DeleteBookingCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Validates the input to be integer.
     *
     * @param input String input from user
     * @return true if the user inputs an integer and false otherwise
     */
    private static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            logger.warning("Index input is not an integer.");
            return false;
        }
    }

    /**
     * Processes the delete command to delete a booking from the booking list.
     *
     * @param bookingList    contains the booking list
     * @param ui             deals with interactions with the user
     * @param bookingStorage deals with loading tasks from the file and saving bookings in the file
     * @return an array list consist of the results or prompts to be displayed to user
     */
    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) {
        DeleteBookingCommand.setupLogger();
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_DELETE_BOOKING)) {
            arrayList.add(ERROR_MESSAGE_EMPTY_BOOKING_INDEX);
        } else if (userInput.trim().charAt(13) == ' ') {
            String input = userInput.split("\\s", 2)[1].trim();
            if (isParsable(input)) {
                int index = Integer.parseInt(input);
                if (index > bookingList.getSize() || index <= 0) {
                    if (bookingList.getSize() == 0) {
                        arrayList.add(MESSAGE_EMPTY_BOOKING_LIST);
                    } else {
                        arrayList.add(ERROR_MESSAGE_INVALID_INDEX + bookingList.getSize() + ".");
                    }
                } else {
                    if (bookingList.getSize() - 1 <= 1) {
                        msg = " booking in the list.";
                    } else {
                        msg = " bookings in the list.";
                    }
                    arrayList.add(MESSAGE_BOOKING_REMOVED + "       " + bookingList.getBookingList().get(index - 1)
                            + "\n" + "Now you have " + (bookingList.getSize() - 1) + msg);

                    bookingList.deleteBooking(index - 1);
                    bookingStorage.saveFile(bookingList);
                }
            } else {
                arrayList.add(ERROR_MESSAGE_UNKNOWN_INDEX);
            }
        } else {
            logger.warning("Invalid deletebooking command");
            arrayList.add(ERROR_MESSAGE_INVALID_DELETE_COMMAND);
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}