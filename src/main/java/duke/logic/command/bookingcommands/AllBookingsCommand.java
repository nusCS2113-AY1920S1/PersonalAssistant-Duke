package duke.logic.command.bookingcommands;

import duke.logic.command.Command;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.BookingMessages.MESSAGE_ALL_CURRENT_BOOKINGS;

/**
 * Handles the list all bookings command.
 */
public class AllBookingsCommand extends Command<BookingList, Ui, BookingStorage> {

    /**
     * Processes the list command to display all bookings in booking list.
     *
     * @param bookingList    contains the booking list
     * @param ui             deals with interactions with the user
     * @param bookingStorage deals with loading tasks from the file and saving bookings in the file
     * @return an array list consist of the results or prompts to be displayed to user
     */
    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) {
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(MESSAGE_ALL_CURRENT_BOOKINGS);
        arrayList.addAll(bookingList.listAllBooking());

        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}