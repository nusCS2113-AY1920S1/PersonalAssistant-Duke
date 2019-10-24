package gazeeebo.commands.tasks;

import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.Timebound;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarView {
    /**
     * Returns the day of the start of the month
     *
     * @param month
     * @param day
     * @param year
     * @return int d The day of the start of the month in integer
     */
    public int StartDay(int month, int day, int year) {
        int y = year - (14 - month) / 12;
        int x = y + y / 4 - y / 100 + y / 400;
        int m = month + 12 * ((14 - month) / 12) - 2;
        int d = (day + x + (31 * m) / 12) % 7;
        return d;
    }

    /**
     * Check if the year is a leap year or not
     *
     * @param year
     * @return true if it is a leap year, false otherwise
     */
    public boolean isLeapYear(int year) {
        if ((year % 4 == 0) && (year % 100 != 0)) return true;
        if (year % 400 == 0) return true;
        return false;
    }

    /**
     * Prints out the calendar in the command line and if there is a task on that day the day will be marked with an '*'
     *
     * @param list
     */
    public void MonthlyView(ArrayList<Task> list) {
        Calendar now = Calendar.getInstance();
        int month = (now.get(Calendar.MONTH) + 1);    // month (Jan = 1, Dec = 12)
        int year = now.get(Calendar.YEAR);     // year
        int date = now.get(Calendar.DATE);     // date
        // months[i] = name of month i
        boolean[] isBusy = new boolean[32];
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
                    if (endDate.getMonthValue() == month && startDate.getMonthValue() == month) {
                        for (int i = startDate.getDayOfMonth(); i <= endDate.getDayOfMonth(); i++) {
                            isBusy[i] = true;
                        }
                    } else if (endDate.getMonthValue() == month) {
                        assert (startDate.getDayOfMonth() != month);
                        for (int i = 1; i <= endDate.getDayOfMonth(); i++) {
                            isBusy[i] = true;
                        }
                    } else if (startDate.getMonthValue() == month) {
                        assert (endDate.getDayOfMonth() != month);
                        for (int i = startDate.getDayOfMonth(); i <= 31; i++) {
                            isBusy[i] = true;
                        }
                    }
                    break;
            }
        }
        String[] months = {
                "",                               // leave empty so that months[1] = "January"
                "January", "February", "March",
                "April", "May", "June",
                "July", "August", "September",
                "October", "November", "December"
        };

        // days[i] = number of days in month i
        int[] days = {
                0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        };

        // check for leap year
        if (month == 2 && isLeapYear(year)) days[month] = 29;


        // print calendar header
        System.out.println("          " + months[month] + " " + year);
        System.out.println("  S    M    Tu   W    Th   F    S");

        // starting day
        int d = StartDay(month, 1, year);

        // print the calendar
        for (int i = 0; i < d; i++)
            System.out.print("     ");
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
            if (((i + d) % 7 == 0) || (i == days[month])) {
                System.out.println();
            }
        }
    }

    /**
     * Prints out the annual calendar in the command line and if there is a task on that day the day will be marked with an '*'
     *
     * @param list
     */
    public void AnnualView(ArrayList<Task> list) {
        Calendar now = Calendar.getInstance();
        int month = (now.get(Calendar.MONTH) + 1);    // month (Jan = 1, Dec = 12)
        int year = now.get(Calendar.YEAR);     // year
        int date = now.get(Calendar.DATE);     // date
        // months[i] = name of month i
        boolean[][] isBusy = new boolean[13][32];
        for (Task task : list) {
            switch (task.getClass().getName()) {
                case "gazeeebo.tasks.Event":
                    Event event = (Event) task;
                    isBusy[event.date.getMonthValue()][event.date.getDayOfMonth()] = true;
                    break;
                case "gazeeebo.tasks.Deadline":
                    Deadline deadline = (Deadline) task;
                    isBusy[deadline.by.getMonthValue()][deadline.by.getDayOfMonth()] = true;
                    break;
                case "gazeeebo.tasks.Timebound":
                    LocalDate startDate = ((Timebound) task).dateStart;
                    LocalDate endDate = ((Timebound) task).dateEnd;
                    if (startDate.getMonthValue() == endDate.getMonthValue()) {
                        for (int i = startDate.getDayOfMonth(); i <= endDate.getDayOfMonth(); i++) {
                            isBusy[startDate.getMonthValue()][i] = true;
                        }
                    } else {
                        for (int i = startDate.getDayOfMonth(); i <= 31; i++) {
                            isBusy[startDate.getMonthValue()][i] = true;
                        }
                        for (int i = 1; i <= endDate.getDayOfMonth(); i++) {
                            isBusy[endDate.getMonthValue()][i] = true;
                        }
                        if (endDate.getMonthValue() - startDate.getMonthValue() > 1) {
                            for (int i = startDate.getMonthValue()+1 ; i < endDate.getMonthValue(); i++) {
                                for (int k = 1; k <= 31; k++) {
                                    isBusy[i][k] = true;
                                }
                            }
                        }
                    }
                    break;
            }
        }
        String[] months = {
                "",                               // leave empty so that months[1] = "January"
                "January", "February", "March",
                "April", "May", "June",
                "July", "August", "September",
                "October", "November", "December"
        };

        // days[i] = number of days in month i
        int[] days = {
                0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        };

        // check for leap year
        if (month == 2 && isLeapYear(year)) days[month] = 29;

        for (int j = 1; j < 13; j++) {
            // print calendar header
            System.out.println("          " + months[j] + " " + year);
            System.out.println("  S    M    Tu   W    Th   F    S");

            // starting day
            int d = StartDay(j , 1, year);

            // print the calendar
            for (int i = 0; i < d; i++)
                System.out.print("     ");
            for (int i = 1; i <= days[j]; i++) {
                if (i != date && isBusy[j][i]) {
                    System.out.printf("%4s ", i + "*");
                } else if (i == date && j==month && !isBusy[j][i]) {
                    System.out.printf("%5s", "|" + i + "|");
                } else if (i == date && j == month && isBusy[j][i]) {
                    System.out.printf("%5s", "|" + i + "*" + "|");
                } else {
                    System.out.printf("%4s ", i);
                }
                if (((i + d) % 7 == 0) || (i == days[month])) {
                    System.out.println();
                }
            }
            System.out.println();
        }
    }
}