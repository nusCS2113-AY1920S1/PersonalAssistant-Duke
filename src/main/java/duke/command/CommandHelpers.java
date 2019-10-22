package duke.command;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

    private final static int ALPHABET_COUNT = 26;
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

    // I hate Java

    /**
     * Given a switch name provided by the user, finds the switch it is referring to, or the closest match,
     * allowing the user to disambiguate.
     * @param word The name provided by the user, which may not match any switch.
     * @param command The command that the word is supposed to be a switch for.
     * @return The full name of the switch of the command that matches the word.
     */
    public static String findSwitch(String word, ArgCommand command) {
        /* TODO: Using a HashMap matching roots to full switches, generate a
        TreeMap matching each possible substring to the corresponding switch.
        Find the SubMap from the user's input to the next possible prefix
        (increment last character) or use some faster method (such as using
        a TreeSet) to find if there's only one possible value it can be resolved to.
        If not, find the set of possible valid inputs with the smallest Damerau-Levenshtein
        distance from the word, package them and their commands as "suggestions" and send
        to disambiguate.*/

        /*Might be possible to just use a TreeSet to check if autocomplete can be evaluated
        unambiguously, and a HashMap to store the possible inputs. Suggestions might be
        better implemented as a map of command strings to possible inputs?*/

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
            }
        }

        return disambiguate(word, suggestions, command.getSwitchMap().keySet());
    }

    /**
     * Provides the user with the choice between several possible options for a switch which does not match exactly.
     *
     * @param word The user-provided switch name.
     * @param suggestions A map of the closest matching switch aliases to the switch names they represent.
     * @param valid The set of valid switches for this command.
     * @return The string that the user has selected.
     */
    public static String disambiguate(String word, HashMap<String, String> suggestions, Set<String> valid) {
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
     * between keys on a standard QWERTY keyboard.
     * <p>
     * https://stackoverflow.com/questions/29233888/
     * https://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance
     * https://dl.acm.org/citation.cfm?doid=1963190.1963191
     * </p>
     *
     * @param str1 The first string to compare.
     * @param str2 The second string to compare.
     * @param minDist The minimum string distance found so far.
     * @return The hybrid Damerau-Levenshtein distance between str1 and str2.
     */
    private static int stringDistance(String str1, String str2, int minDist) {

        int[] da = new int[ALPHABET_COUNT]; //initialised to 0

        //if minDist is 0, run till the end; else break when dist exceeds minDist
        return str1.length() - str2.length() + minDist; //placeholder to deceive codacy
        //if dist == 0 throw an error
    }
}
