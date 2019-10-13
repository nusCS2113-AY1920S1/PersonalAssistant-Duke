package duke.parser;

import duke.command.*;
import duke.command.bookingcommands.*;
import duke.exception.DukeException;

import static duke.common.Messages.ERROR_MESSAGE_RANDOM;

/**
 * Making sense of the user input command.
 */
public class Parser {

    /**
     * Processes the different user input command.
     *
     * @param userInputCommand String containing input command from user
     * @return the different command object corresponding to the user input
     * @throws DukeException if Duke cannot recognise the user input
     */
    public static CommandBooking parseBooking(String userInputCommand) throws DukeException {

        if (userInputCommand.trim().equals("allbookings")) {
            return new AllBookingsCommand();
        } else if (userInputCommand.contains("addbooking")) {
            if (userInputCommand.trim().substring(0, 10).equals("addbooking")) {
                return new AddBookingCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains("deletebooking")) {
            if (userInputCommand.trim().substring(0, 13).equals("deletebooking")) {
                return new DeleteBookingCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains("viewbookingschedule")) {
            if (userInputCommand.trim().substring(0, 19).equals("viewbookingschedule")) {
                return new ViewBookingScheduleCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if (userInputCommand.contains("findbooking")) {
            if (userInputCommand.trim().substring(0, 11).equals("findbooking")) {
                return new FindBookingCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }
}
