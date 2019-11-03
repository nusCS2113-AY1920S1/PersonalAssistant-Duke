//@@author carrieng0323852

package com.algosenpai.app.logic.constant;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Commands {
    hello,
    menu,
    quiz,
    select,
    result,
    history,
    undo,
    clear,
    help,
    volume,
    print,
    archive,
    save,
    reset,
    exit;

    /**
     * Returns the valid commands as strings in a list.
     *
     * @return List of strings
     */
    public static List<String> getNames() {
        List<String> enumNames = Stream.of(Commands.values())
                                       .map(Commands::name)
                                       .collect(Collectors.toList());
        return enumNames;
    }

    /**
     * Finds out if a string is an integer.
     * @param s input string
     * @return boolean true if string is an integer else false otherwise
     */

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
