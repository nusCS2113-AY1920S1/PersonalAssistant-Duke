package com.nwjbrandon.duke.constants;

public enum TaskCommands {

    LIST("list"),
    DONE("done"),
    TODO("todo"),
    EVENT("event"),
    DEADLINE("deadline"),
    DELETE("delete"),
    FIND("find"),
    BYE("bye");

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
