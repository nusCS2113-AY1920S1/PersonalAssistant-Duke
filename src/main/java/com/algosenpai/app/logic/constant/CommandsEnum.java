//@@author carrieng0323852

package com.algosenpai.app.logic.constant;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandsEnum {
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
        List<String> enumNames = Stream.of(CommandsEnum.values())
                                       .map(CommandsEnum::name)
                                       .collect(Collectors.toList());
        return enumNames;
    }
}
