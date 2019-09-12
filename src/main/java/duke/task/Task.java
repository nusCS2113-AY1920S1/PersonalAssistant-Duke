package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Task {
    protected String description;
    protected boolean isDone;
    protected char type;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public String getStatusIcon() {
//        return (isDone ? "\u2713" : "\u2718"); // returns ✓ or X
        return (isDone ? "O" : "X"); // returns O or X
    }

    public void markAsDone() {
        isDone = true;
    }

    public String getDate() {
        return "";
    }

    public String timeToString(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm", Locale.ENGLISH);
        String timeStr = time.format(formatter);
        return timeStr;
    }

    public String getTask() {
        return "[" + type + "][" + getStatusIcon() + "] " + description + getDate();
    }

    public String formatDateSave() {
        return "";
    }

    public String formatSave() {
        return type + " | " + (isDone ? 1 : 0) + " | " + description + formatDateSave();
    }
}
