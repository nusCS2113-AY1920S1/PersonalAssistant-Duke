package duke.command.bookingcommands;

import duke.command.CommandBooking;
import duke.exception.DukeException;
import duke.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.Messages.MESSAGE_FOLLOWUP_NUll;
import static duke.common.Messages.ERROR_MESSAGE_GENERAL;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;


public class FindBookingCommand extends CommandBooking {

    public FindBookingCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws DukeException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInputCommand.trim().equals("findbooking")) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInputCommand.trim().charAt(11) == ' ') {
            String customerName = userInputCommand.split("\\s", 2)[1].trim().toLowerCase();
            arrayList.add("     Here are the matching bookings in your list:");
            arrayList.addAll(bookingList.findBooking(customerName));
/*            for (int i = 0; i < bookingList.findBooking(customerName).size(); i++) {
                System.out.println("     " + (i + 1) + ". " + bookingList.findBooking(customerName).get(i));
            }*/
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
