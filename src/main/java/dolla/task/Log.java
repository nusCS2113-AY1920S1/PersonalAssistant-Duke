package dolla.task;

import dolla.Tag;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A class that is inherited from all forms of logging, ie. entry, Limit and debt so that we can perform polymorphism.
 */
public abstract class Log {
    protected String description;
    protected LocalDate date;
    protected String name;
    protected String userInput;
    protected Tag tag;

    public abstract String getLogText();

    public abstract String formatSave();//currently assuming no tags

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getUserInput() {
        return userInput;
    }
}
