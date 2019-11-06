//@@author carrieng0323852

package com.algosenpai.app.logic.constant;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandsEnum {
    menu,
    quiz,
    select,
    result,
    review,
    reset,
    history,
    undo,
    clear,
    help,
    volume,
    print,
    archive,
    save,
    exit;

    /**
     * Returns the valid commands as strings in a list.
     *
     * @return List of strings
     */
    public static List<String> getNames() {
        List<String> enumNames = Stream.of(CommandsEnum.values())
                                       .map(CommandsEnum::name)
                                       .collect(Collectors.toList());
        return enumNames;
    }

    /**
     * Returns the blocked commands during a quiz.
     * @param enumNames the list of commands
     * @return list of blocked commands during a quiz
     */

    public static List<String> getBlockedNames(List<String> enumNames) {
        List<String> blockedNames = enumNames;
        blockedNames.remove(0); //menu
        blockedNames.remove(5); //history
        blockedNames.remove(8);
        blockedNames.remove(10); //volume

        return blockedNames;
    }
}
