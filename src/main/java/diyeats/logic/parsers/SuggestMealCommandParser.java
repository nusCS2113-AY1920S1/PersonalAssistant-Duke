package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.SuggestMealCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

//@@author HashirZahir
/**
 * Parser class to handle suggestion of meals.
 */
public class SuggestMealCommandParser implements ParserInterface<SuggestMealCommand> {

    private static final String dateArgStr = "date";
    private static final String displayArgStr = "display";
    private static final String mealTypeArgStr = "type";
    // Default values for arguments
    private LocalDate suggestionDate = LocalDate.now();
    private int maxMealsToSuggest = 5;
    private String mealTypeStr = "L";

    /**
     * Parse user input and return SuggestCommand.
     * @param userInputStr String input by user
     * @return <code>SuggestCommand</code> Command object that will process meal suggestions.
     * @throws ProgramException If the userInput cannot be parsed
     */
    @Override
    public SuggestMealCommand parse(String userInputStr) {
        HashMap<String, String> argumentsMap = ArgumentSplitter.splitForwardSlashArguments(userInputStr);

        for (String argNameStr : argumentsMap.keySet()) {
            if (!(argNameStr.equals(dateArgStr) || argNameStr.equals(displayArgStr)
                    || argNameStr.equals(mealTypeArgStr))) {
                return new SuggestMealCommand(true, "Unknown argument " + argNameStr
                        + ". Type `help suggest` to get command syntax.");
            }
        }

        if (argumentsMap.containsKey(dateArgStr)) {
            try {
                suggestionDate = LocalDate.parse(argumentsMap.get(dateArgStr), dateFormat);
            } catch (DateTimeParseException e) {
                return new SuggestMealCommand(true, "Unable to parse" + dateArgStr + " as a date. "
                        + "Please follow DD/MM/YYYY format.");
            }
        }

        if (argumentsMap.containsKey(displayArgStr)) {
            try {
                maxMealsToSuggest = Integer.parseInt(argumentsMap.get(displayArgStr).trim());
            } catch (NumberFormatException e) {
                return new SuggestMealCommand(true, "Unable to parse display as integer. Please "
                        + "input as integer. eg: /display 5");
            }
        }

        if (argumentsMap.containsKey(mealTypeArgStr)) {
            // TODO: Parameterize magic constants of meal type.
            String tempMealTypeStr = argumentsMap.get(mealTypeArgStr).toUpperCase();
            if (tempMealTypeStr.equals("B") || tempMealTypeStr.equals("L") || tempMealTypeStr.equals("D")) {
                mealTypeStr = tempMealTypeStr;
            } else {
                return new SuggestMealCommand(true, "Unable to parse meal type as breakfast, "
                        + "lunch or dinner.\nPlease input a single character \"b\", \"l\" or \"d\""
                        + " which signify breakfast, lunch and dinner respectively.");
            }
        }

        return new SuggestMealCommand(suggestionDate, maxMealsToSuggest, mealTypeStr);
    }
}
