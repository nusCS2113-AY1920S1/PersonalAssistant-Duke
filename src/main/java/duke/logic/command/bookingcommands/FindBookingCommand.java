package duke.logic.command.bookingcommands;

import duke.logic.command.Command;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.BookingMessages.*;

public class FindBookingCommand extends Command<BookingList, Ui, BookingStorage> {

    public FindBookingCommand(String userInput) {
        this.userInput = userInput;
    }

    private static boolean isValidName(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c) && !(c == '_')) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_FIND_BOOKING)) {
            arrayList.add(ERROR_MESSAGE_EMPTY_NAME_FIND);
        } else if (userInput.trim().charAt(11) == ' ') {
            String customerName = userInput.split("\\s", 2)[1].trim().toLowerCase();
            if (isValidName(customerName)) {
                arrayList.add(MESSAGE_MATCHING_BOOKINGS);
                arrayList.addAll(bookingList.findBooking(customerName));
            } else {
                arrayList.add(ERROR_MESSAGE_INVALID_NAME);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_INVALID_FIND_COMMAND);
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
