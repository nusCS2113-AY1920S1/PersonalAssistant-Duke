package duke.command.bookingcommands;

import duke.list.bookinglist.BookingList;
import duke.command.CommandBooking;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.Messages.ERROR_MESSAGE_RANDOM;


/**
 * Handles the list command and inherits all the fields and methods of Command parent class.
 */
public class AllBookingsCommand extends CommandBooking {

    /**
     * Processes the list command to display all tasks in task list.
     * @param bookingList contains the task list
     * @param ui deals with interactions with the user
     * @param bookingStorage deals with loading tasks from the file and saving tasks in the file
     */
    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        //System.out.println("     Here are all the current bookings in your list:");
        arrayList.add("     Here are all the current bookings in your list:");
        arrayList.addAll(bookingList.listAllBooking());

/*        for (int i = 0; i < bookingList.listAllBooking().size(); i++) {
            System.out.println(bookingList.listAllBooking().get(i));
        }*/
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}