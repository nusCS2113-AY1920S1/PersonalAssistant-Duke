package duke.command.bookingcommands;

import duke.bookinglist.BookingList;
import duke.command.Command;
import duke.storage.BookingStorage;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;


/**
 * Handles the list command and inherits all the fields and methods of Command parent class.
 */
public class AllBookingsCommand extends Command {

    /**
     * Processes the list command to display all tasks in task list.
     * @param bookingList contains the task list
     * @param ui deals with interactions with the user
     * @param bookingStorage deals with loading tasks from the file and saving tasks in the file
     */
    @Override
    public void execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) {
        System.out.println("     Here are all the current bookings in your list:");
        for (int i = 0; i < bookingList.listAllBooking().size(); i++) {
            System.out.println(bookingList.listAllBooking().get(i));
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
