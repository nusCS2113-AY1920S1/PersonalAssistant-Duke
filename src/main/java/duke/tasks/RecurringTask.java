package duke.tasks;

public class RecurringTask extends Task {
    private String date;
    private String time = "";
    private String frequency;
    private String oldDate;

    /**
     * Constructor for a duke.tasks.RecurringTask task, which consists of the description, time, date
     * and how often to repeat itself.
     *
     * @param description refers to the description of the task
     * @param date refers to the date on which the task has to be performed
     * @param time refers to the time at which the task has to be performed
     * @param frequency refers to how often the task recurs. This task can only recur
     *                  either daily, weekly, monthly or yearly.
     */
    public RecurringTask(String description, String date, String time, String frequency) {
        super(description);
        this.date = date;
        this.time = time;
        this.frequency = frequency;
    }

    /**
     * Returns a String representation of the duke.tasks.RecurringTask object, displaying its type
     * (duke.tasks.RecurringTask), description and the attributes associated with it.
     *
     * @return a string representation of the duke.tasks.RecurringTask object
     */
    @Override
    public String toString() {
        String msg =  "[R]" + super.toString() + " (" + frequency + " on: " + date;
        if (!this.time.equals("")) {
            msg += " at " + this.time;
        }
        msg += ")";
        return msg;
    }

    /**
     * Returns a String representation of the duke.tasks.RecurringTask object, displaying its type
     * (duke.tasks.RecurringTask), description and the old(previous date) attributes associated with it.
     *
     * @return a string representation of the duke.tasks.RecurringTask object
     */
    public String toOldString() {
        String msg =  "[R][v]" + super.toString().substring(3) + " (" + frequency + " on: " + this.oldDate;
        if (!this.time.equals("")) {
            msg += " at " + this.time;
        }
        msg += ")";
        return msg;
    }

    /**
     * When a RecurringTask is set as done, the next date for the task is generated
     * based on the pre-defined frequency and the task date auto-updates itself.
     * Since this is a recurring task, it can never be marked as done as once the task
     * is done for one date, the task for the next date automatically replaces the previous
     * one.
     */
    @Override
    public void setDone() {
        this.oldDate = this.date;
        if (frequency.equals("daily")) {
            this.date = getNextDayDate(this.date);
        } else if (frequency.equals("weekly")) {
            String temp = this.date;
            for (int i = 0; i < 7; i++) {
                temp = getNextDayDate(temp);
            }
            this.date = temp;
        } else if (frequency.equals("monthly")) {
            String[] datesString = date.split("/");
            int day;
            int month;
            int year;
            day = Integer.parseInt(datesString[0]);
            month = Integer.parseInt(datesString[1]);
            year = Integer.parseInt(datesString[2]);

            month++; //move to next month
            if (month > 12) {
                month = 1;
                year++;
            } else if (month == 2 && day == 29) {
                //change day to 28 if not leap year
                if (!isLeapYear(year)) {
                    day = 28;
                }

            }
            this.date =  day + "/" + month + "/" + year;
        } else if (frequency.equals("yearly")) {
            String[] datesString = date.split("/");
            int day;
            int month;
            int year;
            day = Integer.parseInt(datesString[0]);
            month = Integer.parseInt(datesString[1]);
            year = Integer.parseInt(datesString[2]);
            year++;
            if (day == 29 && month == 2) {
                if (!isLeapYear(year)) {
                    day = 28;
                }
            }
            this.date = day + "/" + month + "/" + year;
        }
    }

    private static String getNextDayDate(String date) {
        String[] datesString = date.split("/");
        int day;
        int month;
        int year;
        day = Integer.parseInt(datesString[0]);
        month = Integer.parseInt(datesString[1]);
        year = Integer.parseInt(datesString[2]);

        if (day == 31 && month == 12) {
            //overflow to next year
            day = 1;
            month = 1;
            year++;
        } else if (month == 2) {
            //special case feb -> check leap year
            if (isLeapYear(year)) {
                day++;
                if (day > 29) {
                    day = 1;
                    month++;
                }
            } else {
                day++;
                if (day > 28) {
                    day = 1;
                    month++;
                }
            }
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10) {
            //months with 31 days
            day++;
            if (day > 31) {
                day = 1;
                month++;
            }
        } else if (month == 2 || month == 4 || month == 6 || month == 9 || month == 11) {
            day++;
            if (day > 30) {
                day = 1;
                month++;
            }
        }
        return day + "/" + month + "/" + year;
    }

    private static boolean isLeapYear(int y) {
        if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a date is a valid calendar date.
     *
     * @param d calendar day of the month
     * @param m calendar month of the year (1-12)
     * @param y calendar year
     * @return a boolean indicating whether the date input is valid or not
     */
    public static boolean isDateVaid(int d, int m, int y) {
        if (d < 1 || d > 31 || m < 1 || m > 12 || y < 0) {
            return false;
        }

        if (m == 2) {
            if (isLeapYear(y)) {
                if (d > 29) {
                    return false;
                }
            } else {
                if (d > 28) {
                    return false;
                }
            }
        } else if (m == 4 || m == 6 || m == 9 || m == 11) {
            if (d > 30) {
                return false;
            }
        }
        return true;
    }

}
