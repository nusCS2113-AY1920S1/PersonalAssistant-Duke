package diyeats.logic.parsers;

import diyeats.logic.commands.SuggestExerciseCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

//@@author Fractalisk

public class SuggestExerciseCommandParser implements ParserInterface {
    private static final String DATE_ARG_STR = "date";
    private static final String FIND_ARG_STR = "find";


    /**
     * Parse user input and return SuggestCommand.
     * @param userInputStr String input by user
     * @return <code>SuggestCommand</code> to be updated
     */
    @Override
    public SuggestExerciseCommand parse(String userInputStr) {
        HashMap<String, String> argumentsMap = ArgumentSplitter.splitForwardSlashArguments(userInputStr);
        LocalDate suggestionDate = LocalDate.now();   //suggest exercise for current day by default
        String keyword = null;

        if (argumentsMap.containsKey(DATE_ARG_STR)) {
            try {
                suggestionDate = LocalDate.parse(argumentsMap.get(DATE_ARG_STR), dateFormat);
            } catch (DateTimeParseException e) {
                return new SuggestExerciseCommand(true, "Unable to parse" + DATE_ARG_STR + " as a date. "
                        + "Please follow DD/MM/YYYY format.");
            }
        }

        if (argumentsMap.containsKey(FIND_ARG_STR)) {
            keyword = argumentsMap.get(FIND_ARG_STR);
        }

        return new SuggestExerciseCommand(suggestionDate, keyword);
    }
}
