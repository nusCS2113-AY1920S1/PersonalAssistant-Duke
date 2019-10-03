package duke.tasks;

import duke.exceptions.DukeInvalidTimePeriodException;
import duke.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Task {

    /**
     * task is the string value of the task name.
     * done is the active status of the task.
     * dateTime is the date and time information if the task requires.
     */
    private String task;
    private Boolean done;
    TimePeriod period;

    /**
     * Constructor to Task class.
     * @param task User's input of the desired task.
     */
    public Task(String task) throws DukeInvalidTimePeriodException {
        this.task = task.trim();
        this.done = false;
        this.period = new TimePeriod();
    }

    public void setTaskDone() {
        done = true;
    }

    public String getTask() {
        return task;
    }

    private boolean getDone() {
        return done;
    }

    public TimePeriod getPeriod() {
        return this.period;
    }

    public void setPeriod(LocalDateTime begin, LocalDateTime end) throws DukeInvalidTimePeriodException {
        this.period.setPeriod(begin, end);
    }

    public void setPeriod(LocalDateTime begin, TimeInterval duration) throws DukeInvalidTimePeriodException {
        this.period.setPeriod(begin, duration);
    }

    public LocalDateTime getTime() {
        return this.getBegin() != null ? this.getBegin() : this.getEnd();
    }

    public LocalDateTime getBegin() {return this.period.getBegin();}

    public LocalDateTime getEnd() {return this.period.getEnd();}

    public LocalDate getBeginDate() {
        return this.getBegin().toLocalDate();
    }

    public LocalTime getBeginTime() {
        return this.getBegin().toLocalTime();
    }

    public LocalDate getEndDate() {
        return this.getEnd().toLocalDate();
    }

    public LocalTime getEndTime() {
        return this.getEnd().toLocalTime();
    }

    /**
     * Function to be used to when writing to the file.
     * @return Returns a string containing task name and done status.
     */
    public String writingFile() {
        return task
                + "|"
                + (getDone() ? "1" : "0");
    }

    @Override
    public String toString() {
        String completed = (done) ? "[✓] " : "[✗] ";
        return completed + task;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Task)) {
            return false;
        }
        Task otherTask = (Task) other;
        return otherTask.getTask().equals(this.getTask());
    }

    @Override
    public int hashCode() {
        return Objects.hash(task, done);
    }

    public boolean isDone() {
        return this.done;
    }

    public boolean isClashing(LocalDateTime localDateTime) {
        return this.period.isClashing(localDateTime);
    }

    public boolean isClashing(LocalDateTime begin, LocalDateTime end) {
        return this.period.isClashing(begin, end);
    }

    public boolean isClashing(TimePeriod timePeriod) {
        return this.period.isClashing(timePeriod);
    }

    public boolean isClashing(DoWithin other) {
        return this.period.isClashing(other.getPeriod());
    }
}
