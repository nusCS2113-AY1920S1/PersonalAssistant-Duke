package gazeeebo.commands;

import gazeeebo.storage.Storage;
import gazeeebo.Tasks.Deadline;
import gazeeebo.Tasks.Event;
import gazeeebo.Tasks.Task;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RecurringCommand {

    /**
     * This method replace the task to the following week/month/year
     * base on the key word weekly/monthly/yearly
     * respectively when the task is marked as done[D].
     *
     * @param list             task lists
     * @param numbercheck      the index of the list
     * @param listDescription description of the index of the list
     * @param storage          the object that deals with storing data.
     * @throws IOException
     */

    public void addRecurring(final ArrayList<Task> list, final int numbercheck, final String listDescription, final Storage storage) throws IOException {
        String[] splitstring;
        String[] datesplitstring;
        String[] togetDescription;
        int date;
        if (listDescription.contains("weekly")) {
            if (listDescription.charAt(0) == 'E') {
                splitstring = listDescription.split("\\|at: ");
                datesplitstring = splitstring[1].split(" ");
                LocalDate newDate = LocalDate.parse(datesplitstring[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).plusMonths(0).plusDays(7);
                String hourMinSec = datesplitstring[1];
                String concTime = newDate + " " + hourMinSec;
                togetDescription = splitstring[0].split("\\|");
                String description = togetDescription[togetDescription.length - 1];
                Event newWeeklyEvent = new Event(description, (concTime));
                list.set(numbercheck, newWeeklyEvent);
                System.out.print("\nI've automatically added this weekly task again:\n" + newWeeklyEvent.listFormat() + "\nNow you have " + list.size() + " tasks in the list.\n");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.storages(sb.toString());
            } else if (listDescription.charAt(0) == 'D') {
                splitstring = listDescription.split("\\|by: ");
                datesplitstring = splitstring[1].split(" ");
                LocalDate newDate = LocalDate.parse(datesplitstring[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).plusMonths(0).plusDays(7);
                String hourMinSec = datesplitstring[1];
                String concTime = newDate + " " + hourMinSec;
                togetDescription = splitstring[0].split("\\|");
                String description = togetDescription[togetDescription.length - 1];
                Deadline newWeeklyDeadline = new Deadline(description, (concTime));
                list.set(numbercheck, newWeeklyDeadline);
                System.out.print("\nI've automatically added this weekly task again:\n" + newWeeklyDeadline.listFormat() + "\nNow you have " + list.size() + " tasks in the list.\n");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.storages(sb.toString());
            }
        } else if (listDescription.contains("monthly")) {
            if (listDescription.charAt(0) == 'E') {
                splitstring = listDescription.split("\\|at: ");
                datesplitstring = splitstring[1].split(" ");
                LocalDate newDate = LocalDate.parse(datesplitstring[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).plusMonths(1).plusDays(0);
                String hourMinSec = datesplitstring[1];
                String concTime = newDate + " " + hourMinSec;
                togetDescription = splitstring[0].split("\\|");
                String description = togetDescription[togetDescription.length - 1];
                Event newMonthlyEvent = new Event(description, (concTime));
                list.set(numbercheck, newMonthlyEvent);
                System.out.print("\nI've automatically added this monthly task again:\n" + newMonthlyEvent.listFormat() + "\nNow you have " + list.size() + " tasks in the list.\n");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.storages(sb.toString());
            } else if (listDescription.charAt(0) == 'D') {
                splitstring = listDescription.split("\\|by: ");
                datesplitstring = splitstring[1].split(" ");
                LocalDate newDate = LocalDate.parse(datesplitstring[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).plusMonths(1).plusDays(0);
                String hourMinSec = datesplitstring[1];
                String concTime = newDate + " " + hourMinSec;
                togetDescription = splitstring[0].split("\\|");
                String description = togetDescription[togetDescription.length - 1];
                Deadline newMonthlyDeadline = new Deadline(description, (concTime));
                list.set(numbercheck, newMonthlyDeadline);
                System.out.print("\nI've automatically added this monthly task again:\n" + newMonthlyDeadline.listFormat() + "\nNow you have " + list.size() + " tasks in the list.\n");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.storages(sb.toString());
            }
        } else if (listDescription.contains("yearly")) {
            if (listDescription.charAt(0) == 'E') {
                splitstring = listDescription.split("\\|at: ");
                datesplitstring = splitstring[1].split(" ");
                LocalDate newDate = LocalDate.parse(datesplitstring[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(1).plusMonths(0).plusDays(0);
                String hourMinSec = datesplitstring[1];
                String concTime = newDate + " " + hourMinSec;
                togetDescription = splitstring[0].split("\\|");
                String description = togetDescription[togetDescription.length - 1];
                Event newYearlyEvent = new Event(description, (concTime));
                list.set(numbercheck, newYearlyEvent);
                System.out.print("\nI've automatically added this yearly task again:\n" + newYearlyEvent.listFormat() + "\nNow you have " + list.size() + " tasks in the list.\n");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.storages(sb.toString());
            } else if (listDescription.charAt(0) == 'D') {
                splitstring = listDescription.split("\\|by: ");
                datesplitstring = splitstring[1].split(" ");
                LocalDate newDate = LocalDate.parse(datesplitstring[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(1).plusMonths(0).plusDays(0);
                String hourMinSec = datesplitstring[1];
                String concTime = newDate + " " + hourMinSec;
                togetDescription = splitstring[0].split("\\|");
                String description = togetDescription[togetDescription.length - 1];
                Deadline newYearlyDeadline = new Deadline(description, (concTime));
                list.set(numbercheck, newYearlyDeadline);
                System.out.print("\nI've automatically added this yearly task again:\n" + newYearlyDeadline.listFormat() + "\nNow you have " + list.size() + " tasks in the list.\n");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.storages(sb.toString());
            }
        }
    }
}