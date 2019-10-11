package duke.command;

import duke.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * Handles the bye command and inherits all the fields and methods of Command parent class.
 */
public class ByeCommand extends Command {

    /**
     * Display the exit message and the program.
     * @param bookingList contains the task list
     * @param ui deals with interactions with the user
     * @param bookingStorage deals with loading tasks from the file and saving tasks in the file
     */
    @Override
    public void execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage)  {
        ui.showGoodbye();
        isExit();
    }

    /**
     * A flag to raise a request to exit the program.
     * @return true if user wants to exit the program
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
