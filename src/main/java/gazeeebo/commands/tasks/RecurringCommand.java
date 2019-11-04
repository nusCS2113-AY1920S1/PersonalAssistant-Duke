
package gazeeebo.commands.tasks;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Automatically replaces the task and adds the new task with the new date.
 */
public class RecurringCommand {
    /**
     * Number of dates to add for weekly task.
     */
    private static final int ADD_WEEKLY_DATE = 7;

    /**
     * Replace the task to the next week/month/year base on the
     * word weekly/monthly/yearly when the it is marked as done[D].
     *
     * @param list            task lists
     * @param numbercheck     the index of the list
     * @param listDescription description of the index of the list
     * @param storage         the object that deals with storing data.
     * @throws IOException catch the error if read fails.
     */

    public void addRecurring(final ArrayList<Task> list,
                             final int numbercheck,
                             final String listDescription,
                             final Storage storage) throws IOException {
        String[] splitDescriptionandDate;
        String[] splitDate;
        String[] getDescription;
        if (listDescription.contains("weekly")) {
            if (listDescription.charAt(0) == 'E') {
                splitDescriptionandDate = listDescription.split("\\|at: ");
                splitDate = splitDescriptionandDate[1].split(" ");
                LocalDate newDate =
                        LocalDate.parse(splitDate[0],
                                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).
                        plusMonths(0).plusDays(ADD_WEEKLY_DATE);
                String hourMinSec = splitDate[1];
                String concTime = newDate + " " + hourMinSec;
                getDescription = splitDescriptionandDate[0].split("\\|");
                String description = getDescription[getDescription.length - 1];
                Event newWeeklyEvent = new Event(description, (concTime));
                list.set(numbercheck, newWeeklyEvent);
                System.out.print("\nI've automatically added "
                        + "this weekly task again:\n"
                        + newWeeklyEvent.listFormat()
                        + "\nNow you have " + list.size()
                        + " tasks in the list.\n");
            } else if (listDescription.charAt(0) == 'D') {
                splitDescriptionandDate = listDescription.split("\\|by: ");
                splitDate = splitDescriptionandDate[1].split(" ");
                LocalDate newDate
                        = LocalDate.parse(splitDate[0],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).
                        plusMonths(0).plusDays(ADD_WEEKLY_DATE);
                String hourMinSec = splitDate[1];
                String concTime = newDate + " " + hourMinSec;
                getDescription = splitDescriptionandDate[0].split("\\|");
                String description = getDescription[getDescription.length - 1];
                Deadline newWeeklyDeadline
                        = new Deadline(description, (concTime));
                list.set(numbercheck, newWeeklyDeadline);
                System.out.print("\nI've automatically added "
                        + "this weekly task again:\n"
                        + newWeeklyDeadline.listFormat() + "\nNow you have "
                        + list.size() + " tasks in the list.\n");
            }
        } else if (listDescription.contains("monthly")) {
            if (listDescription.charAt(0) == 'E') {
                splitDescriptionandDate = listDescription.split("\\|at: ");
                splitDate = splitDescriptionandDate[1].split(" ");
                LocalDate newDate =
                        LocalDate.parse(splitDate[0],
                                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).plusMonths(1).plusDays(0);
                String hourMinSec = splitDate[1];
                String concTime = newDate + " " + hourMinSec;
                getDescription = splitDescriptionandDate[0].split("\\|");
                String description = getDescription[getDescription.length - 1];
                Event newMonthlyEvent = new Event(description, (concTime));
                list.set(numbercheck, newMonthlyEvent);
                System.out.print("\nI've automatically added "
                        + "this monthly task again:\n"
                        + newMonthlyEvent.listFormat() + "\nNow you have "
                        + list.size() + " tasks in the list.\n");
            } else if (listDescription.charAt(0) == 'D') {
                splitDescriptionandDate = listDescription.split("\\|by: ");
                splitDate = splitDescriptionandDate[1].split(" ");
                LocalDate newDate =
                        LocalDate.parse(splitDate[0],
                                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(0).plusMonths(1).plusDays(0);
                String hourMinSec = splitDate[1];
                String concTime = newDate + " " + hourMinSec;
                getDescription = splitDescriptionandDate[0].split("\\|");
                String description = getDescription[getDescription.length - 1];
                Deadline newMonthlyDeadline
                        = new Deadline(description, (concTime));
                list.set(numbercheck, newMonthlyDeadline);
                System.out.print("\nI've automatically added "
                        + "this monthly task again:\n"
                        + newMonthlyDeadline.listFormat()
                        + "\nNow you have " + list.size()
                        + " tasks in the list.\n");
            }
        } else if (listDescription.contains("yearly")) {
            if (listDescription.charAt(0) == 'E') {
                splitDescriptionandDate = listDescription.split("\\|at: ");
                splitDate = splitDescriptionandDate[1].split(" ");
                LocalDate newDate =
                        LocalDate.parse(splitDate[0],
                                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(1).plusMonths(0).plusDays(0);
                String hourMinSec = splitDate[1];
                String concTime = newDate + " " + hourMinSec;
                getDescription = splitDescriptionandDate[0].split("\\|");
                String description = getDescription[getDescription.length - 1];
                Event newYearlyEvent = new Event(description, (concTime));
                list.set(numbercheck, newYearlyEvent);
                System.out.print("\nI've automatically added "
                        + "this yearly task again:\n"
                        + newYearlyEvent.listFormat() + "\nNow you have "
                        + list.size() + " tasks in the list.\n");
            } else if (listDescription.charAt(0) == 'D') {
                splitDescriptionandDate = listDescription.split("\\|by: ");
                splitDate = splitDescriptionandDate[1].split(" ");
                LocalDate newDate =
                        LocalDate.parse(splitDate[0],
                                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                newDate = newDate.plusYears(1).plusMonths(0).plusDays(0);
                String hourMinSec = splitDate[1];
                String concTime = newDate + " " + hourMinSec;
                getDescription = splitDescriptionandDate[0].split("\\|");
                String description = getDescription[getDescription.length - 1];
                Deadline newYearlyDeadline
                        = new Deadline(description, (concTime));
                list.set(numbercheck, newYearlyDeadline);
                System.out.print("\nI've automatically added "
                        + "this yearly task again:\n"
                        + newYearlyDeadline.listFormat()
                        + "\nNow you have " + list.size()
                        + " tasks in the list.\n");
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Task task : list) {
            sb.append(task.toString() + "\n");
        }
        storage.writeToSaveFile(sb.toString());
    }
}