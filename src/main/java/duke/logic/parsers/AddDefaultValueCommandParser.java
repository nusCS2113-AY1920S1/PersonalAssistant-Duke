package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddDefaultValueCommand;
import duke.model.meal.Meal;

import java.time.LocalDate;
import java.util.HashMap;

//@@author Fractalisk
/**
 * Parser class to handle addition of Item object to model.
 */
public class AddDefaultValueCommandParser implements ParserInterface<AddDefaultValueCommand> {

    /**
     * Parses user input and returns an AddItemCommand encapsulating a Item object.
     * @param userInputStr String input by user.
     * @return <code>AddItemCommand</code> Command object encapsulating a breakfast object
     */
    @Override
    public AddDefaultValueCommand parse(String userInputStr) {
        String[] mealNameAndInfo;
        String foodCostStr = "0";
        HashMap<String, String> nutritionInfoMap;
        LocalDate dateArgStr = null;

        try {
            InputValidator.validate(userInputStr);
            mealNameAndInfo = ArgumentSplitter.splitMealArguments(userInputStr);
            nutritionInfoMap = ArgumentSplitter.splitForwardSlashArguments(mealNameAndInfo[1]);
        } catch (DukeException e) {
            return new AddDefaultValueCommand(true, e.getMessage());
        }

        for (String details : nutritionInfoMap.keySet()) {
            if (details.equals("cost")) {
                foodCostStr = nutritionInfoMap.get(details);
            }
            String intArgStr = nutritionInfoMap.get(details);
            try {
                int value = Integer.parseInt(intArgStr);
            } catch (NumberFormatException e) {
                return new AddDefaultValueCommand(true, "Unable to parse " + intArgStr
                        + " as an integer. ");
            }
        }
        return new AddDefaultValueCommand(new Meal(mealNameAndInfo[0], dateArgStr, nutritionInfoMap, foodCostStr));
    }
}
