package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.DeleteExerciseCommand;

public class DeleteExerciseCommandParser implements ParserInterface<DeleteExerciseCommand> {
    /**
     * Parses user input and returns an AddCommand encapsulating a breakfast object.
     * @param userInputStr String input by user.
     * @return <code>AddCommand</code> Command object encapsulating a breakfast object
     */
    @Override
    public DeleteExerciseCommand parse(String userInputStr) {
        try {
            InputValidator.validate(userInputStr);
        } catch (ProgramException e) {
            return new DeleteExerciseCommand(false, "Please input name of entry you wish to delete");
        }
        return new DeleteExerciseCommand(userInputStr);
    }
}
