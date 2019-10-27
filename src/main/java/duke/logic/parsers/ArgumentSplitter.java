package duke.logic.parsers;

public class ArgumentSplitter {

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

    public static String[] splitArguments(String userInput, String delimiter) {
        String[] splitStrings = userInput.split(delimiter, 2);
        splitStrings[0] = splitStrings[0].trim();
        if (splitStrings.length < 2) {
            return new String[] {splitStrings[0], ""};
        }
        return new String[] {splitStrings[0], splitStrings[1]};
    }
}
