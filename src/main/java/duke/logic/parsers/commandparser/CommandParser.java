package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;

public abstract class CommandParser<T> {

    public abstract T parse() throws DukeException;

    /**
     * Gets the field at a given index in a String, delimited by whitespace.
     *
     * @param index The index of the field.
     * @param userInput The userInput read by the user interface.
     * @return The field.
     */
    protected String getEventIndexInList(int index, String userInput) {
        if (index == 1) {
            return userInput.strip().split(" ", 4)[2];
        } else {
            return userInput.strip().split(" ", 4)[3];
        }
    }
}
