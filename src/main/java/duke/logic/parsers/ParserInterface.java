package duke.logic.parsers;

import duke.logic.commands.Command;
import duke.commons.exceptions.DukeException;

/**
 * Represents a parser that processes user input into a Command of type T
 * @param <T> type of Command returned
 */
public interface ParserInterface<T extends Command> {

    /**
     * Parses user input into a Command of type T and returns it
     * @param userInput String input by user
     * @return Command Returns a Command
     * @throws DukeException If the user input has syntactic errors
     */
    T parse(String userInput) throws DukeException;
}

