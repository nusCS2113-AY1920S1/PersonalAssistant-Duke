package duke.parser;

import duke.commons.DukeException;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Parse time. Convert between date object and String date.
 */
public class TimeParser {
    private static PrettyTime prettyTime = new PrettyTime();
    private static PrettyTimeParser prettyTimeParser = new PrettyTimeParser();

    /**
     * Converts a Date object to a String representing the date.
     *
     * @param date Date object
     * @return a String representing the date.
     */
    public static String convertDateToString(Date date) {
        return prettyTime.format(date);
    }

    /**
     * Converts a String representing the date to a Date object.
     * @param str a String representing the date. Must be in correct format: dd-MM-yyyy HHmm
     * @return a Date object.
     * @throws DukeException if the String is of incorrect format.
     */
    public static Date convertStringToDate(String str) throws DukeException {
        List<Date> dates = prettyTimeParser.parse(str);
        if (dates.size() == 0) {
            throw new DukeException("Invalid date");
        }
        return dates.get(0);
    }

    public static List<Task> detectConflict(Task task, TaskList taskList) {
        if (!(task instanceof Event)) {
            return new ArrayList<>();
        }

        List<Task> tasks = taskList.getTasks();
        List<Task> result = new ArrayList<>();

        Date from1 = ((Event) task).getFrom();
        Date to1 = ((Event) task).getTo();
        if (to1 == null) {
            to1 = from1;
        }

        for (Task t : tasks) {
            if (t instanceof Event) {
                Date from2 = ((Event) t).getFrom();
                Date to2 = ((Event) t).getTo();
                if (to2 == null) {
                    to2 = from2;
                }

                if (isOverlapping(from1, to1, from2, to2) && !t.equals(task)) {
                    result.add((Event) t);
                }
            }
        }

        return result;

    }

    private static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) throws NullPointerException {
        return !start1.after(end2) && !start2.after(end1);
    }
}
