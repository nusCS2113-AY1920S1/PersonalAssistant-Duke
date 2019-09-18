package com.nwjbrandon.duke.constants;

import com.nwjbrandon.duke.services.task.FixedDuration;

public enum TaskCommands {

    LIST("list"),
    DONE("done"),
    TODO("todo"),
    EVENT("event"),
    DEADLINE("deadline"),
    DELETE("delete"),
    FIND("find"),
    BYE("bye"),
    REMINDER("reminder"),
    VIEW_SCHEDULE("view schedule"),
    RECURRING("recurring"),
    SNOOZE("snooze"),
    FIXED_DURATION("fixed duration");

    /**
     * Name of the tasks commands.
     */
    private final String name;

    /**
     * Create commands.
     * @param name name of the commands.
     */
    TaskCommands(String name) {
        this.name = name;
    }

    /**
     * Return command in string.
     * @return command in string.
     */
    @Override public String toString() {
        return name;
    }

}
