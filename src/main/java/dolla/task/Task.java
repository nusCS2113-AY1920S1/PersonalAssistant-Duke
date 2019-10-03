package dolla.task;

import dolla.Time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Task {
    protected String description;
    protected boolean isDone;
    protected char type;
    protected LocalDateTime date = Time.readDateTime("01/01/0001 0000"); // Default date

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "O" : "X"); // returns O or X
    }

    public void markAsDone() {
        isDone = true;
    }

    public String getDateStr() {
        return "";
    }

    public LocalDateTime getDate() {
        return date;
    }

    /**
     * converts the time from LocalDateTime to string.
     * @param time in LocalDateTime format
     * @return timeStr in "dd/MM/yyyy HHmm" format
     */
    public String timeToString(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm", Locale.ENGLISH);
        String timeStr = time.format(formatter);
        return timeStr;
    }

    public String getTask() {
        return "[" + type + "][" + getStatusIcon() + "] " + description + getDateStr();
    }

    public String formatDateSave() {
        return "";
    }

    public String formatSave() {
        return type + " | " + (isDone ? 1 : 0) + " | " + description + formatDateSave();
    }

    public char getTaskType() {
        return type;
    }

    public String getTaskDescription() {
        return description;
    }
}
