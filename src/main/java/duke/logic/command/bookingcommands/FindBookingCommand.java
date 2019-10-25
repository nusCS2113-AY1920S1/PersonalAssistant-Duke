package duke.logic.command.bookingcommands;

import duke.logic.command.CommandBooking;
import duke.exception.DukeException;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.util.ArrayList;

public class FindBookingCommand extends CommandBooking {

    public FindBookingCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws DukeException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInputCommand.trim().equals("findbooking")) {
            arrayList.add("Customer name cannot be empty!\n" +
                    "       Please enter in the following format:\n" +
                    "       findbooking <customer_name>");
        } else if (userInputCommand.trim().charAt(11) == ' ') {
            String customerName = userInputCommand.split("\\s", 2)[1].trim().toLowerCase();
            arrayList.add("     Here are the matching bookings in your list:");
            arrayList.addAll(bookingList.findBooking(customerName));
        } else {
            arrayList.add("Incorrect find booking command.\n " +
                    "       Please enter in the following format:\n" +
                    "       findbooking <customer_name>");
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
