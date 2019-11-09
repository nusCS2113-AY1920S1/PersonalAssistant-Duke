package duke.command;

/**
 * Functions for general parsing of command arguments.
 */
public class CommandUtils {

    /**
     * Given a switch name provided by the user, finds the switch it is referring to, or null if ambiguous.
     *
     * @param word    The name provided by the user.
     * @param command The command that the word is supposed to be a switch for.
     * @return The full name of the switch of the command that matches the word, or null if there is no match.
     */
    public static String findSwitch(String word, ArgCommand command) {
        return command.getSwitchAliases().get(word);
    }

    /**
     * Extracts an index from a string argument.
     *
     * @param inputStr The string to parse, generally a command argument.
     * @return The index represented by the string, or -1 if the string does not represent an index.
     */
    public static int idxFromString(String inputStr) {
        try {
            int idx = Integer.parseInt(inputStr) - 1;
            if (idx < 0) {
                return -1;
            } else {
                return idx;
            }
        } catch (NumberFormatException excp) {
            return -1;
        }
    }

    /**
     * Checks two possibly-null strings for equality in a null-safe way.
     *
     * @param str1 The first string to compare.
     * @param str2 The second string to compare.
     * @return True if the strings are both null, or both equal. False otherwise.
     */
    public static boolean equalsIfNotBothNull(String str1, String str2) {
        if (str1 != null) {
            return str1.equals(str2);
        } else {
            return str2 == null;
        }
    }
}