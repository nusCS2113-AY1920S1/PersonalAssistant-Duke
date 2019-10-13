package duke.command;

import duke.list.BookingList;
import duke.exception.DukeException;
import duke.storage.BookingStorage;
import duke.ui.Ui;

import java.text.ParseException;

/**
 * Abstract class to represent command.
 */
public abstract class CommandBooking {
    protected String userInputCommand;

    public abstract void execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
