package duke.view;


import duke.models.ToDo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Class handles the Schedule specific print commands.
 */
public class CliViewSchedule extends CliView {

    /**
     * Will print out a formatted calender.
     *
     * @param numberOfDays days in the month
     * @param startDay     beginning day in the month
     */
    public void printMonth(final int numberOfDays,
                           final int startDay) {
        final int numberOfDaysInAWeek = 7;
        int weekdayIndex = 0;
        System.out.println("Su  Mo  Tu  We  Th  Fr  Sa");

        for (int day = 1; day < startDay; day++) {
            System.out.print("    ");
            weekdayIndex++;
        }

        for (int day = 1; day <= numberOfDays; day++) {
            System.out.printf("%1$2d", day);
            weekdayIndex++;
            if (weekdayIndex == numberOfDaysInAWeek) {
                weekdayIndex = 0;
                System.out.println();
            } else {
                System.out.print("  ");
            }
        }
        System.out.println();
    }

    /**
     * Prints out the month header.
     *
     * @param date The month and day
     * @param year The year to be printed
     */
    public void printMonthHeader(String date, int year) {
        System.out.println(date + " " + year);
    }

    /**
     * Prints out the menu for the month.
     */
    public void printMonthMenu() {
        bufferLine();
        message("Please enter a valid month: 1 - 12");
        message("Go back: back");
        message("View commands: help");
        bufferLine();
    }

    /**
     * Method will generate a table header for the timetable.
     */
    public void tableHeader() {
        System.out.println(
            String.format(
                "%10s %10s %10s %10s %10s %10s %10s",
                "Lesson", "|",
                "Start Time", "|",
                "End Time", "|",
                "Status"
            )
        );
        System.out.println(
            String.format(
                "%s",
                "--------------------------------------------------------------------------------------------"));
    }

    /**
     * Method will print out all items in the day.
     */
    public void tableContents(ArrayList<ToDo> todo) {
        if (!todo.isEmpty()) {
            //Iterate through and print ever item that is listed in the day.
            for (ToDo i : todo) {
                System.out.println(
                    String.format("%10s %10s %10s %10s %10s %10s %10s",
                        i.getClassName(), "|",
                        i.getStartTime(), "|",
                        i.getEndTime(), "|",
                        i.getStatus()
                    )
                );
            }
        } else {
            System.out.println(
                String.format("%50s", "No data")
            );
        }
    }

    /**
     * Method will print out the day and month selected.
     *
     * @param day   The day selected
     * @param month The month selected
     */
    public void tableDate(final int day, final int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        String date = df.format(cal.getTime());
        message(date);
    }
}
