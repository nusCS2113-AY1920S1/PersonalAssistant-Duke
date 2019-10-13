package duke.command.bookingcommands;

import duke.command.CommandBooking;
import duke.exception.DukeException;
import duke.list.BookingList;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import static duke.common.Messages.MESSAGE_FOLLOWUP_NUll;
import static duke.common.Messages.ERROR_MESSAGE_GENERAL;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;


public class FindBookingCommand extends CommandBooking {

    public FindBookingCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public void execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws DukeException {
        if (userInputCommand.trim().equals("findbooking")) {
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInputCommand.trim().charAt(11) == ' ') {
            String customerName = userInputCommand.split("\\s", 2)[1].trim().toLowerCase();
            System.out.println("     Here are the matching bookings in your list:");
            for (int i = 0; i < bookingList.findBooking(customerName).size(); i++) {
                System.out.println("     " + (i + 1) + ". " + bookingList.findBooking(customerName).get(i));
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
