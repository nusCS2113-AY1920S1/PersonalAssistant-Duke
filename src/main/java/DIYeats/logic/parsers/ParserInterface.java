package DIYeats.logic.parsers;

import DIYeats.commons.exceptions.DukeException;
import DIYeats.logic.commands.Command;
import DIYeats.ui.Ui;

import java.time.format.DateTimeFormatter;

import static DIYeats.commons.constants.DateConstants.LOCAL_DATE_FORMATTER;

//@@author Fractalisk
/**
 * Represents a parser that processes user input into a Command of type T.
 * @param <T> type of Command returned.
 */
public interface ParserInterface<T extends Command> {

    Ui ui = new Ui();
    DateTimeFormatter dateFormat = LOCAL_DATE_FORMATTER;

    /**
     * Parses user input into a Command of type T and returns it.
     * @param userInputStr String input by user.
     * @return Command Returns a Command.
     * @throws DukeException If the user input has syntactic errors.
     */
    T parse(String userInputStr) throws DukeException;
}

