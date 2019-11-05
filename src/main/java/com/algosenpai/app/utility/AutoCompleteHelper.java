package com.algosenpai.app.utility;

import com.algosenpai.app.logic.constant.CommandsEnum;

import java.util.List;

/**
 * A utility class to help in AutoComplete feature.
 */
public class AutoCompleteHelper {

    /**
     * Returns the command string starting with the same text as the String paramter passed.
     * If there is no match, it returns the input string.
     */
    public static String autoCompleteCommand(String userInput) {

        // Prevent empty strings from matching.
        if (userInput.isEmpty()) {
            return userInput;
        }

        List<String> commands = CommandsEnum.getNames();
        String bestMatch = userInput;

        for (String s: commands) {
            if (s.startsWith(userInput.toLowerCase())) {
                bestMatch = s;
            }
        }
        return bestMatch;
    }
}
