package dolla.task;

import java.time.LocalDate;

/**
 * A class that is inherited from all forms of recording, ie. entry, Limit and debt so that we can perform polymorphism.
 */
public abstract class Record {
    protected String description;
    protected LocalDate date;
    protected String name;
    protected String userInput;
    protected String recordType;

    protected static final String RECORD_ENTRY = "entry";
    protected static final String RECORD_LIMIT = "limit";
    protected static final String RECORD_DEBT = "debt";

    public abstract String getRecordDetail();

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

    public String getRecordType() {
        return recordType;
    }

    public String getUserInput() {
        return userInput;
    }
}
