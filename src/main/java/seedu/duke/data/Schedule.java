package seedu.duke.data;

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
    private static TreeMap<Date, ArrayList<String>> schedulesInOrder;
    private Ui ui = new Ui();


    public Schedule() {
        schedulesInOrder = new TreeMap<Date, ArrayList<String>>();
    }

    public static String getTodayDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String formatDateTime = localDateTime.format(formatter);
        return formatDateTime;
    }

    public static boolean isSameDate(String comparedDate) {
        return getTodayDate().equals(comparedDate);
    }

    public void addToSchedule(String description, Date date) throws ParseException {
        if (!schedulesInOrder.containsKey(date)) {
            schedulesInOrder.put(date, new ArrayList<>());
        }
        schedulesInOrder.get(date).add(description);
    }

    public void print() {
        for (Map.Entry m:schedulesInOrder.entrySet()) {
            String dateStr = new SimpleDateFormat(DATE_FORMATTER_NO_TIME).format(m.getKey());
            System.out.println("Date: " + dateStr);
            for (String description : schedulesInOrder.get(m.getKey())) {
                System.out.println(description);
            }
            System.out.println();
        }
    }

    public void printSchedule(Date date) {
        if (schedulesInOrder.containsKey(date)) {
            System.out.println("Here are the tasks on this date: (" + date + ")");
            for (String description : schedulesInOrder.get(date)) {
                System.out.println(description);
            }
        } else {
            System.out.println("There are no tasks in the given date: (" + date + ")");
        }
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

}
