package duke.command;

import java.util.ArrayList;
import java.util.Set;

/**
 * Functions for command autocompletion and autocorrection.
 */
public class CommandHelpers {

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

        ArrayList<String> suggestions = new ArrayList<String>();
        return null;
        //return disambiguate(word, suggestions, command.getSwitchMap().keySet());
    }

    public static String disambiguate(String word, ArrayList<String> suggestions, Set<String> valid) {
        StringBuilder builder = new StringBuilder("I didn't understand '").append(word)
                .append("'. Here are the closest matches:").append(System.lineSeparator());
        for (int i = 1; i <= suggestions.size(); ++i) {
            builder.append("  ").append(i).append(". ").append(suggestions.get(i - 1)).append(System.lineSeparator());
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
     *
     * https://stackoverflow.com/questions/29233888/
     * https://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance
     * https://dl.acm.org/citation.cfm?doid=1963190.1963191
     *
     * @param str1 The first string to compare.
     * @param str2 The second string to compare.
     * @return The hybrid Damerau-Levenshtein distance between str1 and str2.
     */
    private int stringDistance(String str1, String str2) {
        return 0;
    }
}
