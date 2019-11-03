package duke.logic.parsers;

import duke.logic.commands.SuggestExerciseCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

public class SuggestExerciseCommandParser implements ParserInterface {
    private static final String dateArgStr = "date";

    /**
     * Parse user input and return SuggestCommand.
     * @param userInputStr String input by user
     * @return <code>SuggestCommand</code> to be updated
     */
    @Override
    public SuggestExerciseCommand parse(String userInputStr) {
        HashMap<String, String> argumentsMap = ArgumentSplitter.splitForwardSlashArguments(userInputStr);
        LocalDate suggestionDate = LocalDate.now();   //suggest exercise for current day by default

        if (argumentsMap.containsKey(dateArgStr)) {
            try {
                suggestionDate = LocalDate.parse(argumentsMap.get(dateArgStr), dateFormat);
            } catch (DateTimeParseException e) {
                return new SuggestExerciseCommand(true, "Unable to parse" + dateArgStr + " as a date. "
                        + "Please follow DD/MM/YYYY format.");
            }
        }

        return new SuggestExerciseCommand(suggestionDate);
    }
}
