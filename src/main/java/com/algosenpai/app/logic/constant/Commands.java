//@@author carrieng0323852

package com.algosenpai.app.logic.constant;

public enum Commands {
    hello,
    menu,
    select,
    quiz,
    result,
    history,
    undo,
    clear,
    reset,
    save,
    exit,
    print,
    archive,
    review;

    /**
     * Returns the valid commands in a string.
     * @return Array of strings
     */
    public static String[] getNames() {
        Commands[] commands = values();
        String[] names = new String[commands.length];
        for (int i = 0; i < commands.length; i++) {
            names[i] = commands[i].name();
        }
        return names;
    }

    // public static String[] names() {
    // return Arrays.toString(Commands.values()).split(", ");
    //}
}
