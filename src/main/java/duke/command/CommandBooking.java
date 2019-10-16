package duke.command;

import duke.list.bookinglist.BookingList;
import duke.exception.DukeException;
import duke.storage.BookingStorage;

import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Abstract class to represent command.
 */

public abstract class CommandBooking {
    protected String userInputCommand;

    public abstract ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
