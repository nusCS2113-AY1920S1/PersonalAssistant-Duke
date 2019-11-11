package logic.parser;

import common.DukeException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class ArgumentTokenizer {
    public static final String[] TOKENS = new String[]{"/to", "/at", "/from", "/skill"};

    //@@author chenyuheng
    /**
     * From command separated by given tokens, get HashMap<String, String>.
     * The given tokens are:
     * <code>public static final String[] TOKENS = new String[]{"/to", "/at", "/from", "/skill"};</code>
     * @param userInput command
     * @return Use a token as key to get the corresponding value;
     * use an empty string ("") as key to get the first part of command without token;
     * null may be get if the corresponding key is not exist.
     * @throws DukeException If a key-value pair has key but has am empty value.
     */
    public static HashMap<String, String> tokenize(String userInput) {
        ArrayList<Integer> breakpoints = getSortedBreakpoints(userInput);
        String[] argumentStrings = breakByBreakpoints(userInput, breakpoints);
        HashMap<String, String> arguments = getMultimap(argumentStrings);
        return arguments;
    }

    //@@author chenyuheng
    /**
     * Get sorted breakpoint by given tokens.
     * @param userInput partial parsed command
     * @return parsed breakpoints list.
     */
    private static ArrayList<Integer> getSortedBreakpoints(String userInput) {
        ArrayList<Integer> breakpoints = new ArrayList<>();
        for (int i = 0; i < TOKENS.length; i++) {
            if (userInput.contains(TOKENS[i])) {
                breakpoints.add(userInput.indexOf(TOKENS[i]));
            }
        }
        breakpoints.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.intValue() - o2.intValue();
            }
        });
        return breakpoints;
    }

    //@@author chenyuheng

    /**
     * Split an whole string to different part by breakpoints set.
     * @param originalString the string need to be broken.
     * @param breakpoints the breakpoints
     * @return split string array
     */
    private static String[] breakByBreakpoints(String originalString, ArrayList<Integer> breakpoints) {
        if (breakpoints.size() == 0) {
            return new String[]{originalString};
        }
        String[] splits = new String[breakpoints.size() + 1];
        breakpoints.add(0, 0);
        for (int i = 0; i < breakpoints.size() - 1; i++) {
            splits[i] = originalString.substring(breakpoints.get(i), breakpoints.get(i + 1));
        }
        splits[splits.length - 1] = originalString.substring(breakpoints.get(breakpoints.size() - 1));
        return splits;
    }

    //@@author chenyuheng
    /**
     * Using split strings and given tokens to get the key-value multimap.
     * @param argumentStrings Split strings
     * @return The corresponding multimap
     */
    private static HashMap<String, String> getMultimap(String[] argumentStrings) {
        HashMap<String, String> arguments = new HashMap<>();
        for (int i = 0; i < argumentStrings.length; i++) {
            boolean found = false;
            for (int j = 0; j < TOKENS.length; j++) {
                try {
                    if (argumentStrings[i].indexOf(TOKENS[j]) == 0) {
                        String value = argumentStrings[i].substring(TOKENS[j].length() + 1).trim();
                        arguments.put(TOKENS[j], value);
                        found = true;
                        break;
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    arguments.put(TOKENS[j], null);
                }
            }
            if (!found) {
                arguments.put("", argumentStrings[i].trim());
            }
        }
        return arguments;
    }
}
