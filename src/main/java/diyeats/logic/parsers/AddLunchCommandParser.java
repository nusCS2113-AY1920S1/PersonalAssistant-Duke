package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.AddCommand;
import diyeats.model.meal.Lunch;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

/**
 * Parser class to handle addition of lunch item to model.
 */
public class AddLunchCommandParser implements ParserInterface<AddCommand> {

    /**
     * Parses user input and returns an AddCommand encapsulating a Lunch object.
     * @param userInputStr String input by user.
     * @return <code>AddCommand</code> Command object encapsulating a breakfast object
     * @throws ProgramException when the user input cannot be parsed
     */
    @Override

    public AddCommand parse(String userInputStr) {
        String[] mealNameAndInfo;
        String foodCostStr = "0";
        HashMap<String, String> nutritionInfoMap;
        LocalDate localDate = LocalDate.now();

        try {
            InputValidator.validate(userInputStr);
            mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInputStr);
            nutritionInfoMap = ArgumentSplitter.splitForwardSlashArguments(mealNameAndInfo[1]);
        } catch (ProgramException e) {
            return new AddCommand(false, e.getMessage());
        }

        for (String details : nutritionInfoMap.keySet()) {
            if (details.equals("date")) {
                String dateArgStr  = "";
                try {
                    dateArgStr = nutritionInfoMap.get(details);
                    localDate = LocalDate.parse(dateArgStr, dateFormat);
                } catch (DateTimeParseException e) {
                    return new AddCommand(true, "Unable to parse " + dateArgStr + " as a date. "
                            + "Please follow DD/MM/YYYY format.");
                }
            } else if (details.equals("cost")) {
                foodCostStr = nutritionInfoMap.get(details);
            } else {
                String intArgStr = nutritionInfoMap.get(details);
                try {
                    int value = Integer.parseInt(intArgStr);
                } catch (NumberFormatException e) {
                    return new AddCommand(true, "Unable to parse " + intArgStr
                            + " as an integer. ");
                }
            }
        }
        return new AddCommand(new Lunch(mealNameAndInfo[0], localDate, nutritionInfoMap, foodCostStr));
    }
}
