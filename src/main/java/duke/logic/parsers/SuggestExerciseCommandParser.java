package duke.logic.parsers;

import duke.logic.commands.SuggestExerciseCommand;

public class SuggestExerciseCommandParser implements ParserInterface {

    /**
     * Parse user input and return SuggestCommand.
     * @param userInput String input by user
     * @return <code>SuggestCommand</code> to be updated
     */
    @Override
    public SuggestExerciseCommand parse(String userInput) {
        // TODO: Finalize suggest command input format and update UG/DG
        return new SuggestExerciseCommand();
    }
}
