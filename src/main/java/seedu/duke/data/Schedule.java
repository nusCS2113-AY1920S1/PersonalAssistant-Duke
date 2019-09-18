package seedu.duke.data;

import seedu.duke.task.Task;
import seedu.duke.ui.Ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;

public class Schedule {

    private static final String DATE_FORMATTER_NO_TIME = "dd/MM/yyyy";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER_NO_TIME);
    private static TreeMap<Date, ArrayList<Task>> schedulesInOrder = new TreeMap<Date, ArrayList<Task>>();
    private Ui ui = new Ui();


    public Schedule() {
    }

    public static String getTodayDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String formatDateTime = localDateTime.format(formatter);
        return formatDateTime;
    }

    public void addToSchedule(Task task, Date date) throws ParseException {
        if (!schedulesInOrder.containsKey(date)) {
            schedulesInOrder.put(date, new ArrayList<>());
        }
        schedulesInOrder.get(date).add(task);
    }
/*
    public void removeFromSchedule(Task task) {

    }*/
/*
    public void print() {
        for (Map.Entry m:schedulesInOrder.entrySet()) {
            String dateStr = new SimpleDateFormat(DATE_FORMATTER_NO_TIME).format(m.getKey());
            System.out.println("Date: " + dateStr);
            for (Task task : schedulesInOrder.get(m.getKey())) {
                System.out.println(task.toString());
            }
            System.out.println();
        }
    }*/

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

}
