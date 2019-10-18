package dolla.task;

import java.time.LocalDateTime;

/**
 * A class that is inherited from all forms of logging, ie. Entry and Debt,
 * so that we can perform polymorphism.
 */
public abstract class Log {
    protected String description;
    protected LocalDateTime date;
    protected String name;
    protected String userInput;

    public abstract String getLogText();

    public abstract String formatSave();//currently assuming no tags

    public LocalDateTime getDate() {
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
