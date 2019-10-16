package gazeeebo.commands;

import gazeeebo.Storage.Storage;
import gazeeebo.Tasks.Deadline;
import gazeeebo.Tasks.Event;
import gazeeebo.Tasks.Task;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RecurringCommand {

    /**
     * This method replace the task to the following week/month/year base on the key word weekly/monthly/yearly respectively when the
     * task is marked as done[D].
     *
     * @param list             task lists
     * @param numbercheck      the index of the list
     * @param list_description description of the index of the list
     * @param storage          the object that deals with storing data.
     * @throws IOException
     */

    public void AddRecurring(ArrayList<Task> list, int numbercheck, String list_description, Storage storage) throws IOException {
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
                list.set(numbercheck, new_weeklyEvent);
                System.out.print("\nI've automatically added this weekly task again:\n" + new_weeklyEvent.listFormat()
                        + "\nNow you have " + list.size() + " tasks in the list.\n");
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
                list.set(numbercheck, new_weeklyDeadline);
                System.out.print("\nI've automatically added this weekly task again:\n" + new_weeklyDeadline.listFormat()
                        + "\nNow you have " + list.size() + " tasks in the list.\n");
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
                list.set(numbercheck, new_monthlyEvent);
                System.out.print("\nI've automatically added this monthly task again:\n" + new_monthlyEvent.listFormat()
                        + "\nNow you have " + list.size() + " tasks in the list.\n");
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
                list.set(numbercheck, new_monthlyDeadline);
                System.out.print("\nI've automatically added this monthly task again:\n" + new_monthlyDeadline.listFormat()
                        + "\nNow you have " + list.size() + " tasks in the list.\n");
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
                list.set(numbercheck, new_yearlyEvent);
                System.out.print("\nI've automatically added this yearly task again:\n" + new_yearlyEvent.listFormat()
                        + "\nNow you have " + list.size() + " tasks in the list.\n");
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
                list.set(numbercheck, new_yearlyDeadline);
                System.out.print("\nI've automatically added this yearly task again:\n" + new_yearlyDeadline.listFormat()
                        + "\nNow you have " + list.size() + " tasks in the list.\n");

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.Storages(sb.toString());
            }
        }
    }
}