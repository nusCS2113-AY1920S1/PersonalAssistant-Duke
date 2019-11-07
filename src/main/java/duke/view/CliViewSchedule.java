package duke.view;


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
        message("Go back: 13");
        bufferLine();
    }


}
