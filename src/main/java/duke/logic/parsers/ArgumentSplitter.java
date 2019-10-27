package duke.logic.parsers;

/**
 * ArgumentSplitter is a public class that handles the splitting of command arguments.
 */
public class ArgumentSplitter {

    /**
     * split multiple meal related command arguments (nutritional values).
     * @param userInput String input by user.
     * @return <code>String[]{mealName, mealInfo}</code> Name and info of the meal.
     */
    public static String[] splitMealArguments(String userInput) {

        String[] splitStrings = userInput.split("/", 2);
        String mealName = splitStrings[0].trim();
        String mealInfo = "";
        if (splitStrings.length > 1) {
            mealInfo += "/";
            mealInfo += splitStrings[1];
        }
        return new String[]{mealName, mealInfo};
    }

    /**
     * split command arguments based on specific delimiter
     * @param userInput String input by user.
     * @param delimiter the delimiter that the userInput will be split around.
     * @return <code>String[] {splitStrings[0], splitStrings[1]}</code> Split arguments of the command.
     */
    public static String[] splitArguments(String userInput, String delimiter) {
        String[] splitStrings = userInput.split(delimiter, 2);
        splitStrings[0] = splitStrings[0].trim();
        if (splitStrings.length < 2) {
            return new String[] {splitStrings[0], ""};
        }
        return new String[] {splitStrings[0], splitStrings[1]};
    }
}
