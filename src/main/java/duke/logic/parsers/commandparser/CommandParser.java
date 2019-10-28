package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeUnknownCommandException;

public abstract class CommandParser<T> {

    public abstract T parse() throws DukeException;

    /**
     * Gets command word from the userInput.
     *
     * @param userInput The userInput read by the user interface.
     * @return The command word.
     */
    protected String getCommandWord(String userInput) {
        return userInput.strip().split(" ")[0];
    }

    /**
     * Gets word from the userInput.
     *
     * @param userInput The userInput read by the user interface.
     * @return The word.
     */
    protected String getWord(String userInput) throws DukeException {
        try {
            return userInput.strip().split(" ", 2)[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeUnknownCommandException();
        }
    }

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
