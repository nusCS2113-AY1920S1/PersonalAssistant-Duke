//@@author jessteoxizhi

package gazeeebo.commands.tasks;

import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.Timebound;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarView {
    private final int TOTAL_NUM_DAYS = 31;
    private final int NUM_OF_MONTHS = 12;
    private final int DAYS_IN_WEEK = 7;
    private final int LEAP_YEAR_EVERY = 4;
    private final int NO_LEAP_YEAR = 100;
    private final int LEAP_YEAR = 400;
    private final int DET_CURRENT_MONTH = 14;

    /**
     * Returns the day of the month.
     *
     * @param month month of the date
     * @param day   date
     * @param year  year
     * @return int d The day of the start of the month in integer
     */
    public int startDay(final int month, final int day, final int year) {
        int y = year - (DET_CURRENT_MONTH - month) / NUM_OF_MONTHS;
        int x = y + y / LEAP_YEAR_EVERY - y / NO_LEAP_YEAR + y / LEAP_YEAR;
        int m = month + NUM_OF_MONTHS * ((DET_CURRENT_MONTH - month) / NUM_OF_MONTHS) - 2;
        int d = (day + x + (TOTAL_NUM_DAYS * m) / NUM_OF_MONTHS) % DAYS_IN_WEEK;
        return d;
    }

    /**
     * Check if the year is a leap year or not.
     *
     * @param year year
     * @return true if it is a leap year, false otherwise
     */
    public boolean isLeapYear(int year) {
        if ((year % LEAP_YEAR_EVERY == 0) && (year % NO_LEAP_YEAR != 0)) {
            return true;
        }
        return year % LEAP_YEAR == 0;
    }

    /**
     * Prints out the calendar in the command line,
     * if there is a task on that day the day will be marked with an '*'.
     *
     * @param list list of tasks
     */
    public void monthlyView(final ArrayList<Task> list) {
        Calendar now = Calendar.getInstance();
        int month = (now.get(Calendar.MONTH) + 1);
        int year = now.get(Calendar.YEAR);
        int date = now.get(Calendar.DATE);
        boolean[] isBusy = new boolean[TOTAL_NUM_DAYS+1];
        for (Task task : list) {
            switch (task.getClass().getName()) {
                case "gazeeebo.tasks.Event":
                    Event event = (Event) task;
                    if (event.date.getMonthValue() == month) {
                        isBusy[event.date.getDayOfMonth()] = true;
                    }
                    break;
                case "gazeeebo.tasks.Deadline":
                    Deadline deadline = (Deadline) task;
                    if (deadline.by.getMonthValue() == month) {
                        isBusy[deadline.by.getDayOfMonth()] = true;
                    }
                    break;
                case "gazeeebo.tasks.Timebound":
                    LocalDate startDate = ((Timebound) task).dateStart;
                    LocalDate endDate = ((Timebound) task).dateEnd;
                    if (endDate.getMonthValue()
                            == month && startDate.getMonthValue() == month) {
                        for (int i = startDate.getDayOfMonth();
                             i <= endDate.getDayOfMonth(); i++) {
                            isBusy[i] = true;
                        }
                    } else if (endDate.getMonthValue() == month) {
                        assert (startDate.getDayOfMonth() != month);
                        for (int i = 1; i <= endDate.getDayOfMonth(); i++) {
                            isBusy[i] = true;
                        }
                    } else if (startDate.getMonthValue() == month) {
                        assert (endDate.getDayOfMonth() != month);
                        for (int i = startDate.getDayOfMonth(); i <= TOTAL_NUM_DAYS; i++) {
                            isBusy[i] = true;
                        }
                    }
                    break;
                default:
            }
        }
        String[] months = {
                "",
                "January", "February", "March",
                "April", "May", "June",
                "July", "August", "September",
                "October", "November", "December"
        };

        int[] days = {
                0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        };

        if (month == 2 && isLeapYear(year)) {
            days[month] = 29;
        }

        System.out.println("          " + months[month] + " " + year);
        System.out.println("  S    M    Tu   W    Th   F    S");

        int d = startDay(month, 1, year);

        for (int i = 0; i < d; i++) {
            System.out.print("     ");
        }
        for (int i = 1; i <= days[month]; i++) {
            if (i != date && isBusy[i]) {
                System.out.printf("%4s ", i + "*");
            } else if (i == date && !isBusy[i]) {
                System.out.printf("%5s", "|" + i + "|");
            } else if (i == date && isBusy[i]) {
                System.out.printf("%5s", "|" + i + "*" + "|");
            } else {
                System.out.printf("%4s ", i);
            }
            if (((i + d) % DAYS_IN_WEEK == 0) || (i == days[month])) {
                System.out.println();
            }
        }
    }

    /**
     * Prints out the annual calendar in the command line and
     * if there is a task on that day the day will be marked
     * with an '*'.
     *
     * @param list list of tasks
     */
    public void annualView(final ArrayList<Task> list) {
        Calendar now = Calendar.getInstance();
        int month = (now.get(Calendar.MONTH) + 1);
        int year = now.get(Calendar.YEAR);
        int date = now.get(Calendar.DATE);
        boolean[][] isBusy = new boolean[NUM_OF_MONTHS + 1][TOTAL_NUM_DAYS + 1];
        for (Task task : list) {
            switch (task.getClass().getName()) {
                case "gazeeebo.tasks.Event":
                    Event event = (Event) task;
                    isBusy[event.date.getMonthValue()]
                            [event.date.getDayOfMonth()] = true;
                    break;
                case "gazeeebo.tasks.Deadline":
                    Deadline deadline = (Deadline) task;
                    isBusy[deadline.by.getMonthValue()]
                            [deadline.by.getDayOfMonth()] = true;
                    break;
                case "gazeeebo.tasks.Timebound":
                    LocalDate startDate = ((Timebound) task).dateStart;
                    LocalDate endDate = ((Timebound) task).dateEnd;
                    if (startDate.getMonthValue() == endDate.getMonthValue()) {
                        for (int i = startDate.getDayOfMonth();
                             i <= endDate.getDayOfMonth(); i++) {
                            isBusy[startDate.getMonthValue()][i] = true;
                        }
                    } else {
                        for (int i = startDate.getDayOfMonth(); i <= TOTAL_NUM_DAYS; i++) {
                            isBusy[startDate.getMonthValue()][i] = true;
                        }
                        for (int i = 1; i <= endDate.getDayOfMonth(); i++) {
                            isBusy[endDate.getMonthValue()][i] = true;
                        }
                        if (endDate.getMonthValue()
                                - startDate.getMonthValue() > 1) {
                            for (int i = startDate.getMonthValue() + 1;
                                 i < endDate.getMonthValue(); i++) {
                                for (int k = 1; k <= TOTAL_NUM_DAYS; k++) {
                                    isBusy[i][k] = true;
                                }
                            }
                        }
                    }
                    break;
                default:
            }
        }
        String[] months = {
                "",
                "January", "February", "March",
                "April", "May", "June",
                "July", "August", "September",
                "October", "November", "December"
        };

        int[] days = {
                0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        };

        if (month == 2 && isLeapYear(year)) {
            days[month] = 29;
        }

        for (int j = 1; j < 13; j++) {
            System.out.println("          " + months[j] + " " + year);
            System.out.println("  S    M    Tu   W    Th   F    S");

            int d = startDay(j, 1, year);

            for (int i = 0; i < d; i++) {
                System.out.print("     ");
            }
            for (int i = 1; i <= days[j]; i++) {
                if (i != date && isBusy[j][i]) {
                    System.out.printf("%4s ", i + "*");
                } else if (i == date && j == month && !isBusy[j][i]) {
                    System.out.printf("%5s", "|" + i + "|");
                } else if (i == date && j == month && isBusy[j][i]) {
                    System.out.printf("%5s", "|" + i + "*" + "|");
                } else {
                    System.out.printf("%4s ", i);
                }
                if (((i + d) % DAYS_IN_WEEK == 0) || (i == days[month])) {
                    System.out.println();
                }
            }
            System.out.println();
        }
    }
}
