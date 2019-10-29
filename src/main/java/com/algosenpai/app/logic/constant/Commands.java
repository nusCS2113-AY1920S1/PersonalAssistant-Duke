//@@author carrieng0323852

package com.algosenpai.app.logic.constant;

import com.algosenpai.app.logic.command.Command;

import java.util.Arrays;

public enum Commands {
    menu,
    quiz,
    select,
    result,
    report,
    back,
    history,
    undo,
    clear,
    reset,
    save,
    help,
    exit,
    print,
    archive;

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
      //  return Arrays.toString(Commands.values()).split(", ");
    //}

}
