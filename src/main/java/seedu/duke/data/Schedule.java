package seedu.duke.data;

import seedu.duke.task.Task;
import seedu.duke.ui.Ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.SimpleDateFormat;

public class Schedule {

    private static final String DATE_FORMATTER_NO_TIME = "dd/MM/yyyy";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER_NO_TIME);
    private static TreeMap<Date, ArrayList<Task>> schedulesInOrder = new TreeMap<Date, ArrayList<Task>>();
    private Ui ui;


    public Schedule() {
        ui = new Ui();
    }


    public ArrayList<Task> addToSchedule(Task task, Date date) throws ParseException {
        if (!schedulesInOrder.containsKey(date)) {
            schedulesInOrder.put(date, new ArrayList<>());
        }
        schedulesInOrder.get(date).add(task);
        return schedulesInOrder.get(date);
    }

    public void printSchedule(Date date) {
        System.out.println("\t_____________________________________");
        String strDate = convertDateToString(date);
        if (schedulesInOrder.containsKey(date)) {
            System.out.println("\tHere are the tasks on this date: (" + strDate + ")");
            int i = 1;
            for (Task task : schedulesInOrder.get(date)) {
                System.out.println("\t" + i + ". " + task.toString());
                i++;
            }
        } else {
            System.out.println("\tThere are no tasks in the given date: (" + strDate + ")");
        }
        System.out.println("\t_____________________________________");
    }

    public Date convertStringToDate(String dateStr) {
        try {
            Date date = new SimpleDateFormat(DATE_FORMATTER_NO_TIME).parse(dateStr);
            return date;
        } catch (ParseException e) {
            ui.dateFormatError();
            return null;
        }
    }

    public String convertDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER_NO_TIME);
        return dateFormat.format(date);
    }

    public static boolean isValidDate(String dateStr) {
        try {
            Date date = new SimpleDateFormat(DATE_FORMATTER_NO_TIME).parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public ArrayList<Task> getDatedList(Date date) {
        return schedulesInOrder.get(date);
    }

}
