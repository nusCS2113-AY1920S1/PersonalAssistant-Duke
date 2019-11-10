package duke.view;


import duke.models.ToDo;
import duke.util.DateHandler;

import java.util.ArrayList;

/**
 * Class handles the Schedule specific print commands.
 */
public class CliViewSchedule extends CliView {

    /**
     * Will print out a formatted calender.
     *
     * @param daysInMonth days in the month
     * @param dayOfWeek   beginning day in the month
     */
    public void printMonth(final int daysInMonth,
                           final int dayOfWeek) {
        System.out.println("Su  Mo  Tu  We  Th  Fr  Sa");


        //print initial spaces
        String initialSpace = "";
        for (int i = 0; i < dayOfWeek - 1; i++) {
            initialSpace += "    ";
        }
        System.out.print(initialSpace);

        //print the days of the month starting from 1
        for (int i = 0, dayOfMonth = 1; dayOfMonth <= daysInMonth; i++) {
            for (int j = ((i == 0) ? dayOfWeek - 1 : 0); j < 7 && (dayOfMonth <= daysInMonth); j++) {
                System.out.printf("%-2d  ", dayOfMonth);
                dayOfMonth++;
            }
            System.out.println();
        }
    }

    /**
     * Prints out the month header.
     *
     * @param date The month and day
     * @param year The year to be printed
     */
    public void printMonthHeader(String date, int year) {
        message(date + " " + year);
    }

    /**
     * Prints out the menu for the month.
     */
    public void printMonthMenu() {
        bufferLine();
        message("Please enter a valid month: 1 - 12");
        message("Go back: back");
        message("View commands: help");
        message("List all days with scheduled classes: list");
        bufferLine();
    }

    /**
     * Method will generate a table header for the timetable.
     */
    public void tableHeader() {
        System.out.println(
            String.format(
                "%-10s %10s %-10s %10s %-10s %10s %-10s %10s %-10s",
                "Lesson", "|",
                "Location", "|",
                "Start Time", "|",
                "End Time", "|",
                "Status"
            )
        );
        System.out.println(
            String.format(
                "%s",
                "----------------------------------------------------"
                    + "-------------------------------------------------"));
    }

    /**
     * Method will print out all items in the day.
     */
    public void tableContents(ArrayList<ToDo> todo) {
        if (!todo.isEmpty()) {
            //Iterate through and print ever item that is listed in the day.
            for (ToDo i : todo) {
                System.out.println(
                    String.format("%-19s %-1s %-19s %-1s %-19s %-1s %-19s %-1s %-10s",
                        i.getClassName(), "|",
                        i.getLocation(), "|",
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
        String date = DateHandler.stringDate("dd MMM yyyy", day, month, 2019);
        message(date);
    }

    /**
     * Method will list all available commands in the table.
     */
    public void tableMenu() {
        bufferLine();
        message("Add Class: add n/[CLASS NAME] s/[START TIME] d/[END TIME] loc/[LOCATION]");
        message("Go back: back");
    }
}
