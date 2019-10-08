package duke.parser;

import duke.command.*;
import duke.exception.DukeException;

import static duke.common.Messages.ERROR_MESSAGE_RANDOM;
import static duke.common.Messages.COMMAND_BYE;
import static duke.common.Messages.COMMAND_VIEWSCHEDULE;
import static duke.common.Messages.COMMAND_FIND;

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
    public static Command parse(String userInputCommand) throws DukeException {
        if (userInputCommand.trim().equals(COMMAND_BYE)) {
            return new ByeCommand();
        } else if (userInputCommand.trim().equals("allbookings")) {
            return new AllBookingsCommand();
        } else if (userInputCommand.contains("addbooking")) {
            if (userInputCommand.trim().substring(0, 10).equals("addbooking")) {
                return new AddBookingCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        } else if(userInputCommand.contains("deletebooking")) {
            if(userInputCommand.trim().substring(0, 13).equals("deletebooking")) {
                return new DeleteBookingCommand(userInputCommand);
            } else {
                throw new DukeException(ERROR_MESSAGE_RANDOM);
            }
        }
        else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }
}
