package gazeeebo.commands.tasks;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
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

    public void addRecurring(ArrayList<Task> list, int numbercheck, String listDescription, Storage storage) throws IOException {
        String[] splitDescriptionandDate;
        String[] splitDate;
        String[] getDescription;
        if (listDescription.contains("weekly")) {
            if (listDescription.charAt(0) == 'E') {
                splitDescriptionandDate = listDescription.split("\\|at: ");
                splitDate = splitDescriptionandDate[1].split(" ");
                LocalDate newDate = LocalDate.parse(splitDate[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).plusMonths(0).plusDays(7);
                String hourMinSec = splitDate[1];
                String concTime = newDate + " " + hourMinSec;
                getDescription = splitDescriptionandDate[0].split("\\|");
                String description = getDescription[getDescription.length - 1];
                Event newWeeklyEvent = new Event(description, (concTime));
                list.set(numbercheck, newWeeklyEvent);
                System.out.print("\nI've automatically added this weekly task again:\n" + newWeeklyEvent.listFormat() + "\nNow you have " + list.size() + " tasks in the list.\n");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.writeToSaveFile(sb.toString());
            } else if (listDescription.charAt(0) == 'D') {
                splitDescriptionandDate = listDescription.split("\\|by: ");
                splitDate = splitDescriptionandDate[1].split(" ");
                LocalDate newDate = LocalDate.parse(splitDate[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).plusMonths(0).plusDays(7);
                String hourMinSec = splitDate[1];
                String concTime = newDate + " " + hourMinSec;
                getDescription = splitDescriptionandDate[0].split("\\|");
                String description = getDescription[getDescription.length - 1];
                Deadline newWeeklyDeadline = new Deadline(description, (concTime));
                list.set(numbercheck, newWeeklyDeadline);
                System.out.print("\nI've automatically added this weekly task again:\n" + newWeeklyDeadline.listFormat() + "\nNow you have " + list.size() + " tasks in the list.\n");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.writeToSaveFile(sb.toString());
            }
        } else if (listDescription.contains("monthly")) {
            if (listDescription.charAt(0) == 'E') {
                splitDescriptionandDate = listDescription.split("\\|at: ");
                splitDate = splitDescriptionandDate[1].split(" ");
                LocalDate newDate = LocalDate.parse(splitDate[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).plusMonths(1).plusDays(0);
                String hourMinSec = splitDate[1];
                String concTime = newDate + " " + hourMinSec;
                getDescription = splitDescriptionandDate[0].split("\\|");
                String description = getDescription[getDescription.length - 1];
                Event newMonthlyEvent = new Event(description, (concTime));
                list.set(numbercheck, newMonthlyEvent);
                System.out.print("\nI've automatically added this monthly task again:\n" + newMonthlyEvent.listFormat() + "\nNow you have " + list.size() + " tasks in the list.\n");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.writeToSaveFile(sb.toString());
            } else if (listDescription.charAt(0) == 'D') {
                splitDescriptionandDate = listDescription.split("\\|by: ");
                splitDate = splitDescriptionandDate[1].split(" ");
                LocalDate newDate = LocalDate.parse(splitDate[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).plusMonths(1).plusDays(0);
                String hourMinSec = splitDate[1];
                String concTime = newDate + " " + hourMinSec;
                getDescription = splitDescriptionandDate[0].split("\\|");
                String description = getDescription[getDescription.length - 1];
                Deadline newMonthlyDeadline = new Deadline(description, (concTime));
                list.set(numbercheck, newMonthlyDeadline);
                System.out.print("\nI've automatically added this monthly task again:\n" + newMonthlyDeadline.listFormat() + "\nNow you have " + list.size() + " tasks in the list.\n");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.writeToSaveFile(sb.toString());
            }
        } else if (listDescription.contains("yearly")) {
            if (listDescription.charAt(0) == 'E') {
                splitDescriptionandDate = listDescription.split("\\|at: ");
                splitDate = splitDescriptionandDate[1].split(" ");
                LocalDate newDate = LocalDate.parse(splitDate[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(1).plusMonths(0).plusDays(0);
                String hourMinSec = splitDate[1];
                String concTime = newDate + " " + hourMinSec;
                getDescription = splitDescriptionandDate[0].split("\\|");
                String description = getDescription[getDescription.length - 1];
                Event newYearlyEvent = new Event(description, (concTime));
                list.set(numbercheck, newYearlyEvent);
                System.out.print("\nI've automatically added this yearly task again:\n" + newYearlyEvent.listFormat() + "\nNow you have " + list.size() + " tasks in the list.\n");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.writeToSaveFile(sb.toString());
            } else if (listDescription.charAt(0) == 'D') {
                splitDescriptionandDate = listDescription.split("\\|by: ");
                splitDate = splitDescriptionandDate[1].split(" ");
                LocalDate newDate = LocalDate.parse(splitDate[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(1).plusMonths(0).plusDays(0);
                String hourMinSec = splitDate[1];
                String concTime = newDate + " " + hourMinSec;
                getDescription = splitDescriptionandDate[0].split("\\|");
                String description = getDescription[getDescription.length - 1];
                Deadline newYearlyDeadline = new Deadline(description, (concTime));
                list.set(numbercheck, newYearlyDeadline);
                System.out.print("\nI've automatically added this yearly task again:\n" + newYearlyDeadline.listFormat() + "\nNow you have " + list.size() + " tasks in the list.\n");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.writeToSaveFile(sb.toString());
            }
        }
    }
}