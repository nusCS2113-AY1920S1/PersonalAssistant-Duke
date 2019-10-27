package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.SuggestMealCommand;

/**
 * Parser class to handle suggestion of meals.
 */
public class SuggestMealCommandParser implements ParserInterface<SuggestMealCommand> {

    /**
     * Parse user input and return SuggestCommand.
     * @param userInput String input by user
     * @return <code>SuggestCommand</code> to be updated
     * @throws DukeException If the userInput cannot be parsed
     */
    @Override
    public SuggestMealCommand parse(String userInput) throws DukeException {
        InputValidator.validate(userInput);
        // TODO: Finalize suggest command input format and update UG/DG
        return new SuggestMealCommand();
    }
}
