package duke.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class RecurringTask extends Task {
    private int period;
    private Task task;

    public RecurringTask(@JsonProperty("task") Task task,
            @JsonProperty("period") int period) {
            super(task.getDescription());
            this.task = task;
            this.period = period;
    }

    public Task getTask() {
        return task;
    }

    public int getPeriod() {
        return period;
    }

    private Date addPeriodTo(Date date) {

        Date now = new Date();

        long diff = now.getTime() - date.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        long multiplier = 1;
        if (period != 0) {
            multiplier = (diffDays / period) + 1;
        }

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        cal.add(Calendar.DATE, (period * (int) multiplier));

        return cal.getTime();
    }

    public void updateTask() {

        Date now = new Date();
        if (task instanceof Deadline) {
            Date deadlineTime = ((Deadline) task).getBy();
            if (deadlineTime.before(now)) {
                deadlineTime = addPeriodTo(deadlineTime);
                ((Deadline) task).setTime(deadlineTime);
            }
        }

        if (task instanceof Event) {
            Date eventFromTime = ((Event) task).getFrom();
            Date eventToTime = ((Event) task).getTo();
            if (eventFromTime.before(now)) {
                eventFromTime = addPeriodTo(eventFromTime);
                if (eventToTime != null) {
                    eventToTime = addPeriodTo(eventToTime);
                    //LocalDateTime.from(eventToTime.toInstant()).plusDays(period);
                }
                ((Event) task).setTime(eventFromTime, eventToTime);
            }
        }
        System.out.println(period);
    }


    @Override
    public String toString() {
            return String.format("  %s  \n  %s   ", task.toString(), "/ " + period + " days");
    }
}
