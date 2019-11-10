package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.EditCommand;
import diyeats.model.meal.Meal;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import static diyeats.commons.constants.DateConstants.LOCAL_DATE_FORMATTER;

//@@author HashirZahir
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
        String[] indexAndMealInfo;
        int mealIndex;
        HashMap<String, String> nutritionInfoMap;
        DateTimeFormatter dateFormat = LOCAL_DATE_FORMATTER;
        LocalDate localDate = LocalDate.now();

        try {
            InputValidator.validate(userInputStr);
            indexAndMealInfo = userInputStr.split(" ", 2);
            if (indexAndMealInfo.length < 2) {
                return new EditCommand(true, "No tags specified to update or edit. Nothing updated");
            }
            InputValidator.validatePositiveInteger(indexAndMealInfo[0]);
            mealIndex = Integer.parseInt(indexAndMealInfo[0]);
            nutritionInfoMap = ArgumentSplitter.splitForwardSlashArguments(indexAndMealInfo[1]);
        } catch (ProgramException e) {
            return new EditCommand(true, e.getMessage());
        }

        if (nutritionInfoMap.containsKey("date")) {
            String dateArgStr = "";
            try {
                dateArgStr = nutritionInfoMap.get("date");
                localDate = LocalDate.parse(dateArgStr, dateFormat);
            } catch (DateTimeParseException e) {
                return new EditCommand(true, "Unable to parse" + dateArgStr + " as a date. "
                        + "Please follow DD/MM/YYYY format.");
            }
            nutritionInfoMap.remove("date");
        }

        for (String detailsStr : nutritionInfoMap.keySet()) {
            String valueStr = nutritionInfoMap.get(detailsStr);
            try {
                InputValidator.validateNutritionalValue(valueStr);
            } catch (ProgramException e) {
                return new EditCommand(false, "Unable to parse tag " + detailsStr + " with value "
                        + valueStr + " as an integer. Please enter values as integers larger than or equal to 0");
            }
        }

        return new EditCommand(mealIndex - 1, localDate, nutritionInfoMap);
    }
}
