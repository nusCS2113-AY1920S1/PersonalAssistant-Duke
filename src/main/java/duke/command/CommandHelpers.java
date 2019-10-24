package duke.command;

import duke.exception.DukeHelpException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Functions for command autocompletion and autocorrection.
 */
public class CommandHelpers {

    static class Coord {
        int x, y;

        Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * For autocorrect, do not consider strings whose lengths differ from the input by more than this value.
     */
    private final static int MAX_LEN_DIFF = 2;
    private final static Map<Character, Coord> keyboardMap =
            Map.ofEntries(Map.entry('q', new Coord(0, 1)), Map.entry('w', new Coord(1, 1)),
            Map.entry('e', new Coord(2, 1)), Map.entry('r', new Coord(3, 1)),
            Map.entry('t', new Coord(4, 1)), Map.entry('y', new Coord(5, 1)),
            Map.entry('u', new Coord(6, 1)), Map.entry('i', new Coord(7, 1)),
            Map.entry('o', new Coord(8, 1)), Map.entry('p', new Coord(9, 1)),
            Map.entry('a', new Coord(0, 2)), Map.entry('z', new Coord(0, 3)),
            Map.entry('s', new Coord(1, 2)), Map.entry('x', new Coord(1, 3)),
            Map.entry('d', new Coord(2, 2)), Map.entry('c', new Coord(2, 3)),
            Map.entry('f', new Coord(3, 2)), Map.entry('b', new Coord(4, 3)),
            Map.entry('m', new Coord(5, 3)), Map.entry('j', new Coord(6, 2)),
            Map.entry('g', new Coord(4, 2)), Map.entry('h', new Coord(5, 2)),
            Map.entry('k', new Coord(7, 2)), Map.entry('l', new Coord(8, 2)),
            Map.entry('v', new Coord(3, 3)), Map.entry('n', new Coord(5, 3)),
            Map.entry('1', new Coord(0, 0)), Map.entry('2', new Coord(1, 0)),
            Map.entry('3', new Coord(2, 0)), Map.entry('4', new Coord(3, 0)),
            Map.entry('5', new Coord(4, 0)), Map.entry('6', new Coord(5, 0)),
            Map.entry('7', new Coord(6, 0)), Map.entry('8', new Coord(7, 0)),
            Map.entry('9', new Coord(8, 0)), Map.entry('0', new Coord(9, 0)),
            Map.entry('[', new Coord(10, 1)), Map.entry(']', new Coord(9, 1)),
            Map.entry(';', new Coord(9, 2)), Map.entry('\'', new Coord(10, 2)),
            Map.entry(',', new Coord(8, 3)), Map.entry('.', new Coord(9, 3)),
            Map.entry('/', new Coord(10, 3)), Map.entry('-', new Coord(10, 0)),
            Map.entry('+', new Coord(11, 0)));
    private final static Map<Character, Integer> charMap;

    static {
        HashMap<Character, Integer> tempMap = new HashMap<Character, Integer>();
        int i = 0;
        for (char c : keyboardMap.keySet()) {
            tempMap.put(c, i);
            ++i;
        }
        charMap = Collections.unmodifiableMap(tempMap);
    }

    // I hate Java

    /**
     * Checks if a particular switch in an ArgCommand is null, and if not, attempts to parse it as an Integer.
     * @param command The ArgCommand whose switch is being extracted.
     * @param switchName The name of the switch being extracted.
     * @return The Integer that the string represents, or null if it is null.
     * @throws NumberFormatException If the string is not a valid representation of an integer.
     */
    public static Integer switchToInt(String switchName, ArgCommand command) throws DukeHelpException {
        String str = command.getSwitchVal(switchName);
        if (str == null) {
            return null;
        } else {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException excp) {
                throw new DukeHelpException("The switch '" + switchName + "' must be an integer!", command);
            }
        }
    }

    /**
     * Given a switch name provided by the user, finds the switch it is referring to, or the closest match,
     * allowing the user to disambiguate.
     * @param word The name provided by the user, which may not match any switch.
     * @param command The command that the word is supposed to be a switch for.
     * @return The full name of the switch of the command that matches the word.
     */
    public static String findSwitch(String word, ArgCommand command) {
        String corrStr = command.getSwitchAliases().get(word);
        if (corrStr != null) {
            return corrStr;
        }

        HashMap<String, String> suggestions = new HashMap<String, String>();
        int minDist = 0;
        for (Map.Entry<String, String> entry : command.getSwitchAliases().entrySet()) {
            int dist = stringDistance(entry.getKey(), word, minDist);
            if (dist < minDist) {
                suggestions.clear();
                suggestions.put(entry.getValue(), entry.getKey());
                minDist = dist;
            } else if (dist == minDist) {
                suggestions.put(entry.getValue(), entry.getKey());
            } //ignore if dist > minDist
        }

        return disambiguateSwitches(word, suggestions, command.getSwitchMap().keySet());
    }

    /**
     * Identifies a switch which is not matched exactly. Returns the closest match if it exists, and provides the user
     * with a window offering the choice between the closest possible options and a list of valid options otherwise,
     * including the choice to enter his own input.
     *
     * @param word The user-provided switch name.
     * @param suggestions A map of the closest matching switch aliases to the switch names they represent.
     * @param valid The set of valid switches for this command.
     * @return The string that the user has selected.
     */
    public static String disambiguateSwitches(String word, HashMap<String, String> suggestions, Set<String> valid) {
        StringBuilder builder = new StringBuilder("I didn't understand '").append(word)
                .append("'. Here are the closest matches:").append(System.lineSeparator());
        int i = 1;
        for (Map.Entry<String, String> entry : suggestions.entrySet()) {
            builder.append("  ").append(i).append(". '").append(entry.getKey())
                    .append("' - for switch '").append(entry.getValue()).append("'")
                    .append(System.lineSeparator());
            ++i;
        }

        builder.append(System.lineSeparator()).append("Enter the number corresponding to a suggestion to")
                .append("select it, or enter the full form of one of the valid options listed below:")
                .append(System.lineSeparator()).append(System.lineSeparator()).append("  ");
        for (String validStr : valid) {
            builder.append(validStr).append(", ");
        }
        //delete trailing comma and add newline
        builder.delete(builder.length() - 2, builder.length()).append(System.lineSeparator());

        String selectedStr = ""; //TODO: get from user
        return selectedStr;
    }

    /**
     * Algorithm to compute a hybrid version of the Damerau-Levenshtein distance that takes into account distance
     * between keys on a standard QWERTY keyboard when computing substitution cost. Search is pruned if it exceeds
     * the minimum distance found so far, returning the distance computed up to that point.
     *
     * @param str1 The first string to compare.
     * @param str2 The second string to compare.
     * @param minDist The minimum string distance found so far, or 0 if no distance computations have been
     *                performed yet.
     * @return The hybrid Damerau-Levenshtein distance between str1 and str2.
     */
    private static int stringDistance(String str1, String str2, int minDist) {
        //setup values
        int len1 = str1.length();
        int len2 = str2.length();
        int[] d1 = new int[keyboardMap.size()]; //values initialised to 0
        int[][] d = new int[len1 + 2][len2 + 2];
        int maxdist = len1 + len2;

        //populate boundary values
        d[0][0] = maxdist;
        for (int i = 1; i <= len1 + 1; ++i) {
            d[i][0] = maxdist;
            d[i][1] = i;
        }
        for (int j = 1; j <= len2 + 1; ++j) {
            d[0][j] = maxdist;
            d[1][j] = j;
        }

        for (int i = 2; i <= len1 + 1; ++i) {
            int d2 = 2;
            char c1 = str1.charAt(i - 2);
            for (int j = 2; j <= len2 + 1; ++j) {
                int k = d1[str2.charAt(j - 2)]; // TODO: translate letters of alphabet (keyboardMap) into integers
                int l = d2;
                int subCostInc;
                char c2 = str2.charAt(j - 2);
                if (c1 == c2) {
                    subCostInc = 0;
                    d2 = j;
                } else {
                    // TODO: implement scaling factor?
                    subCostInc = Math.abs(keyboardMap.get(c1).x - keyboardMap.get(c2).x)
                            + Math.abs(keyboardMap.get(c1).y - keyboardMap.get(c2).y);
                }

                //calculate cost of each edit and find minimum
                int subCost = d[i - 2][j - 2] + subCostInc;
                int insCost = d[i - 1][j - 2] + 1;
                int delCost = d[i - 2][j - 1] + 1;
                int transCost = d[k - 2][l - 2] + (i - k - 3) + 1 + (j - l - 3);
                List<Integer> costs = Arrays.asList(subCost, insCost, delCost, transCost);
                // TODO: verify that this pruning works; won't work if the distance decreases as it converges
                int min = Collections.min(costs);
                assert(min != 0);
                if (min > minDist && minDist != 0) {
                    return min;
                } else {
                    d[i - 1][j - 1] = min;
                }
            }
            d1[str1.charAt(i - 2)] = i;
        }
        return d[len1 + 1][len2 + 1];
    }
}
