package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.EditCommand;
import duke.model.meal.Meal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import static duke.commons.constants.DateConstants.LOCAL_DATE_FORMATTER;

/**
 * Parser class to handle editing of a entry in the model.
 */
public class EditCommandParser implements ParserInterface<EditCommand> {

    /**
     * Parse user input and return EditCommand.
     * @param userInputStr String input by user.
     * @return <code>EditCommand</code> Command object encapsulating the Meal object to replace the old entry
     */
    @Override
    public EditCommand parse(String userInputStr) {
        String[] mealNameAndInfo;
        HashMap<String, String> nutritionInfoMap;
        DateTimeFormatter dateFormat = LOCAL_DATE_FORMATTER;
        LocalDate localDate = LocalDate.now();

        try {
            InputValidator.validate(userInputStr);
            mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInputStr);
            nutritionInfoMap = ArgumentSplitter.splitForwardSlashArguments(mealNameAndInfo[1]);
        } catch (DukeException e) {
            return new EditCommand(true, e.getMessage());
        }

        for (String details : nutritionInfoMap.keySet()) {
            if (details == "date") {
                String dateArgStr = "";
                try {
                    dateArgStr = nutritionInfoMap.get(details);
                    localDate = LocalDate.parse(dateArgStr, dateFormat);
                } catch (DateTimeParseException e) {
                    return new EditCommand(true, "Unable to parse" + dateArgStr + " as a date. "
                            + "Please follow DD/MM/YYYY format.");
                }
            } else {
                String intArgStr = nutritionInfoMap.get(details);
                try {
                    int value = Integer.parseInt(intArgStr);
                } catch (NumberFormatException e) {
                    return new EditCommand(true, "Unable to parse" + intArgStr
                            + " as an integer. ");
                }
            }
        }
        return new EditCommand(new Meal(mealNameAndInfo[0], localDate, nutritionInfoMap));
    }
}
