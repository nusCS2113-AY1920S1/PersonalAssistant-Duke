package duke.logic.parsers;

import duke.logic.commands.ListCommand;

import java.text.ParseException;
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
                Date temp;
                temp = dateFormat.parse(userInputStr);
                return new ListCommand(dateFormat.format(temp));
            } catch (ParseException e) {
                return new ListCommand(false, "Unable to parse \"" + userInputStr + "\" as a date.");
            }
        } else {
            return new ListCommand();
        }
    }
}
