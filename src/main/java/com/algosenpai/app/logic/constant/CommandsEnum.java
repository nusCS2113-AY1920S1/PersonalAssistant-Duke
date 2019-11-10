//@@author carrieng0323852

package com.algosenpai.app.logic.constant;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandsEnum {
    menu, //0
    lecture, //1
    quiz, //2
    arcade, //3
    chapters, //4
    review, //5
    reset, //6
    history, //7
    undo, //8
    clear, //9
    help, //10
    volume, //11
    print, //12
    archive, //13
    stats, //15
    result,//16
    exit, //17
    load, //18
    start; //19

    private static List<String> enumNames = Stream.of(CommandsEnum.values())
            .map(CommandsEnum::name)
            .collect(Collectors.toList());

    /**
     * Returns the valid commands as strings in a list.
     *
     * @return List of strings
     */
    public static List<String> getNames() {
        return enumNames;
    }

    /**
     * Returns the blocked commands during a quiz.
     * @return list of blocked commands during a quiz.
     */
    public static List<String> getQuizBlockedNames() {
        List<String> blockedNames = new LinkedList<>(enumNames);
        blockedNames.remove(17); //exit
        blockedNames.remove(11); //volume
        blockedNames.remove(2); //quiz
        return blockedNames;
    }

    /**
     * Returns the blocked commands during a lecture.
     * @return list of blocked commands during a lecture.
     */
    public static List<String> getLectureBlockedNames() {
        List<String> blockedNames = new LinkedList<>(enumNames);
        blockedNames.remove(17); //exit
        blockedNames.remove(11); //volume
        blockedNames.remove(1); //lecture
        return blockedNames;
    }

    /**
     * Returns the blocked command during an arcade.
     * @return list of blocked commands during an arcade.
     */
    public static List<String> getArcadeBlockedNames() {
        List<String> blockedNames = new LinkedList<>(enumNames);
        blockedNames.remove(17); //exit
        blockedNames.remove(11); //volume
        blockedNames.remove(3); //arcade
        return blockedNames;
    }

}
