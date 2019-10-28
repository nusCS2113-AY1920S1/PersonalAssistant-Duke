package duke.logic.command.bookingcommands;

import duke.logic.command.Command;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.util.ArrayList;

public class FindBookingCommand extends Command<BookingList, Ui, BookingStorage> {

    public FindBookingCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals("findbooking")) {
            arrayList.add("Customer name cannot be empty!\n" +
                    "       Please enter in the following format:\n" +
                    "       findbooking <customer_name>");
        } else if (userInput.trim().charAt(11) == ' ') {
            String customerName = userInput.split("\\s", 2)[1].trim().toLowerCase();
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
