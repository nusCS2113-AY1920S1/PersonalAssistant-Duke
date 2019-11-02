package duke.logic.parsers;

import duke.logic.commands.ListCommand;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Parser class to handle a list command.
 */
public class ListCommandParser implements ParserInterface<ListCommand> {

    /**
     * Parse userInput and return ListCommand.
     * @param userInputStr String input by user.
     * @return <code>ListCommand</code>
     */
    @Override
    public ListCommand parse(String userInputStr) {
        if (!userInputStr.isBlank()) {
            try {
                LocalDate date = LocalDate.parse(userInputStr,dateFormat);
                return new ListCommand(date);
            } catch (DateTimeParseException e) {
                return new ListCommand(false, "Unable to parse \"" + userInputStr + "\" as a date.");
            }
        } else {
            return new ListCommand();
        }
    }
}
