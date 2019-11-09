package diyeats.logic.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@author GaryStu
/**
 * ArgumentSplitter is a public class that handles the splitting of command arguments.
 */
public class ArgumentSplitter {

    private static final String argPatternStr = " /[a-zA-Z]+";
    private static final Pattern argPattern = Pattern.compile(argPatternStr);

    /**
     * split multiple meal related command arguments (nutritional values).
     * @param userInputStr String input by user.
     * @return <code>String[]{mealName, mealInfo}</code> Name and info of the meal.
     */
    public static String[] splitMealArguments(String userInputStr) {

        String[] splitStrings = userInputStr.split("/", 2);
        String mealName = splitStrings[0].trim();
        String mealInfo = "";
        if (splitStrings.length > 1) {
            mealInfo += "/";
            mealInfo += splitStrings[1];
        }
        return new String[]{mealName, mealInfo};
    }

    /**
     * split command arguments based on specific delimiter.
     * @param userInputStr String input by user.
     * @param delimiter the delimiter that the userInput will be split around.
     * @return <code>String[] {splitStrings[0], splitStrings[1]}</code> Split arguments of the command.
     */
    public static String[] splitArguments(String userInputStr, String delimiter) {
        String[] splitStrings = userInputStr.split(delimiter, 2);
        splitStrings[0] = splitStrings[0].trim();
        if (splitStrings.length < 2) {
            return new String[] {splitStrings[0], ""};
        }
        splitStrings[1] = splitStrings[1].trim();
        return new String[] {splitStrings[0], splitStrings[1]};
    }

    //@@author HashirZahir
    /**
     * Split commands based on forward slash separated arguments (eg: /date 1 /calorie 2 ..)
     * @param userInputStr String input by user.
     * @return Hashmap containing all the keys and respective string value for each forward
     *         slash separated string argument in user input.
     */
    public static HashMap<String, String> splitForwardSlashArguments(String userInputStr) {
        HashMap<String, String> argumentsMap = new HashMap<>();
        userInputStr = " " + userInputStr;
        String[] tempSplitStrings = userInputStr.split(argPatternStr);
        ArrayList<String> valueStrings = new ArrayList<>();

        /*
         * Necessary filtering of empty strings as matching the regex
         * produces leading empty string.
         */
        for (String tempStr : tempSplitStrings) {
            if (!tempStr.isEmpty()) {
                valueStrings.add(tempStr.trim());
            }
        }

        // Regex pattern matching to get argument names.
        Matcher argNameMatcher = argPattern.matcher(userInputStr);
        ArrayList<String> argNameStrings = new ArrayList<>();
        while (argNameMatcher.find()) {
            String matchedStr = argNameMatcher.group();
            argNameStrings.add(matchedStr.replace("/", ""));
        }

        for (int idx = 0; idx < argNameStrings.size();idx++) {
            String argNameStr = argNameStrings.get(idx).trim();
            if (idx < valueStrings.size()) {
                String argValueStr = valueStrings.get(idx);
                argumentsMap.put(argNameStr, argValueStr);
            } else {
                // number of arguments and values do not match
                // return the argument value pairs written so far
                break;
            }
        }

        return argumentsMap;
    }
}
