package commands;

import Storage.Storage;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RecurringCommand {

    public void AddRecurring(ArrayList<Task> list, String list_description, Storage storage) throws ParseException, IOException {
        String[] splitstring;
        String[] datesplitstring;
        String[] toget_description;
        int date;
        if (list_description.contains("weekly")) {
            if (list_description.charAt(0) == 'E') {
                splitstring = list_description.split("\\|at: ");
                datesplitstring = splitstring[1].split(" ");
                LocalDate newDate = LocalDate.parse(datesplitstring[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).plusMonths(0).plusDays(7);
                String hour_min_sec = datesplitstring[1];
                String conc_time = newDate + " " + hour_min_sec;
                toget_description = splitstring[0].split("\\|");
                String description = toget_description[toget_description.length - 1];

                Event new_weeklyEvent = new Event(description, (conc_time));
                list.add(new_weeklyEvent);
                System.out.println("");
                System.out.println("I've automatically added this weekly task again:");
                System.out.println(new_weeklyEvent.listFormat());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.Storages(sb.toString());
            } else if (list_description.charAt(0) == 'D') {
                splitstring = list_description.split("\\|by: ");
                datesplitstring = splitstring[1].split(" ");
                LocalDate newDate = LocalDate.parse(datesplitstring[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).plusMonths(0).plusDays(7);
                String hour_min_sec = datesplitstring[1];
                String conc_time = newDate + " " + hour_min_sec;
                toget_description = splitstring[0].split("\\|");
                String description = toget_description[toget_description.length - 1];

                Deadline new_weeklyDeadline = new Deadline(description, (conc_time));
                list.add(new_weeklyDeadline);
                System.out.println("\nI've automatically added this weekly task again:");
                System.out.println(new_weeklyDeadline.listFormat());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.Storages(sb.toString());
            }
        } else if (list_description.contains("monthly")) {
            if (list_description.charAt(0) == 'E') {
                splitstring = list_description.split("\\|at: ");
                datesplitstring = splitstring[1].split(" ");
                LocalDate newDate = LocalDate.parse(datesplitstring[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).plusMonths(1).plusDays(0);
                String hour_min_sec = datesplitstring[1];
                String conc_time = newDate + " " + hour_min_sec;
                toget_description = splitstring[0].split("\\|");
                String description = toget_description[toget_description.length - 1];
                Event new_monthlyEvent = new Event(description, (conc_time));
                list.add(new_monthlyEvent);
                System.out.println("");
                System.out.println("I've automatically added this monthly task again:");
                System.out.println(new_monthlyEvent.listFormat());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.Storages(sb.toString());
            } else if (list_description.charAt(0) == 'D') {
                splitstring = list_description.split("\\|by: ");
                datesplitstring = splitstring[1].split(" ");
                LocalDate newDate = LocalDate.parse(datesplitstring[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).plusMonths(1).plusDays(0);
                String hour_min_sec = datesplitstring[1];
                String conc_time = newDate + " " + hour_min_sec;
                toget_description = splitstring[0].split("\\|");
                String description = toget_description[toget_description.length - 1];

                Deadline new_monthlyDeadline = new Deadline(description, (conc_time));
                list.add(new_monthlyDeadline);
                System.out.println("\nI've automatically added this weekly task again:");
                System.out.println(new_monthlyDeadline.listFormat());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.Storages(sb.toString());
            }
        } else if (list_description.contains("yearly")) {
            if (list_description.charAt(0) == 'E') {
                splitstring = list_description.split("\\|at: ");
                datesplitstring = splitstring[1].split(" ");
                LocalDate newDate = LocalDate.parse(datesplitstring[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(1).plusMonths(0).plusDays(0);
                String hour_min_sec = datesplitstring[1];
                String conc_time = newDate + " " + hour_min_sec;
                toget_description = splitstring[0].split("\\|");
                String description = toget_description[toget_description.length - 1];

                Event new_yearlyEvent = new Event(description, (conc_time));
                list.add(new_yearlyEvent);
                System.out.println("");
                System.out.println("I've automatically added this weekly task again:");
                System.out.println(new_yearlyEvent.listFormat());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.Storages(sb.toString());
            } else if (list_description.charAt(0) == 'D') {
                splitstring = list_description.split("\\|by: ");
                datesplitstring = splitstring[1].split(" ");
                LocalDate newDate = LocalDate.parse(datesplitstring[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(1).plusMonths(0).plusDays(0);
                String hour_min_sec = datesplitstring[1];
                String conc_time = newDate + " " + hour_min_sec;
                toget_description = splitstring[0].split("\\|");
                String description = toget_description[toget_description.length - 1];
                Deadline new_yearlyDeadline = new Deadline(description, (conc_time));
                list.add(new_yearlyDeadline);
                System.out.println("");
                System.out.println("I've automatically added this weekly task again:");
                System.out.println(new_yearlyDeadline.listFormat());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.Storages(sb.toString());
            }
        }
    }
}
