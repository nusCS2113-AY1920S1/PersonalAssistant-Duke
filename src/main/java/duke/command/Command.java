package duke.command;

import duke.bookinglist.BookingList;
import duke.exception.DukeException;
import duke.storage.BookingStorage;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.text.ParseException;

/**
 * Abstract class to represent command.
 */
public abstract class Command {
    protected String userInputCommand;

    public abstract void execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
