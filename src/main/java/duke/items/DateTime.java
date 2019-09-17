package duke.items;

//TODO: Import an existing datetime class or write a better one.
/**
 * Stores date and time information by field - day, month, year, hour, minute.
 * Allows printing of a fancy formatted date.
 * Uses a boolean variable to indicate if the date object has been properly initialised.
 */

public class DateTime {
    private boolean valid;
    private String dateAndTime;
    private int day;
    private int month;
    private int year;
    private int hour;
    private String minute;

    /**
     * DateTime constructor. Converts input string into attributes of the date and time.
     */
    public DateTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
        String[] details = dateAndTime.split("[ /]");

        try {
            day = Integer.parseInt(details[0]);
            month = Integer.parseInt(details[1]);
            year = Integer.parseInt(details[2]);
            hour = Integer.parseInt(details[3].substring(0, 2));
            minute = details[3].substring(2);
            valid = true;
        } catch (NumberFormatException e) {
            System.out.println("Sorry, that date input is not recognised.");
            System.out.println("The date input is still saved and can be accessed.");
            valid = false;
        }
    }

    public boolean isValid() {
        return valid;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    private String getDayString() {
        if (day > 31) {
            System.out.println("Day is invalid.");
            return null;
        } else if (day > 10 && day < 14) {
            return Integer.toString(day) + "th";
        } else if (day % 10 == 1) {
            return Integer.toString(day) + "st";
        } else if (day % 10 == 2) {
            return Integer.toString(day) + "nd";
        } else if (day % 10 == 3) {
            return Integer.toString(day) + "rd";
        } else {
            return Integer.toString(day) + "th";
        }
    }

    private String getMonthString() {
        switch (month) {
        case 1:
            return "January";
        case 2:
            return "February";
        case 3:
            return "March";
        case 4:
            return "April";
        case 5:
            return "May";
        case 6:
            return "June";
        case 7:
            return "July";
        case 8:
            return "August";
        case 9:
            return "September";
        case 10:
            return "October";
        case 11:
            return "November";
        case 12:
            return "December";
        default:
            System.out.println("Month is invalid.");
            return null;
        }
    }

    private String getTimeString() {
        if (hour > 12 && hour <= 24) {
            return Integer.toString(hour - 12) + ":" + minute + " pm";
        } else if (hour < 12) {
            return Integer.toString(hour) + ":" + minute + " am";
        } else if (hour == 12) {
            return "12:" + minute + " pm";
        } else {
            System.out.println("Time is invalid.");
            return null;
        }
    }

    /**
     * Returns the date in a friendlier format.
     */
    public String returnFormattedDate() {
        if (valid) {
            return getDayString() + " of " + getMonthString() + " " + year + ", " + getTimeString();
        } else {
            return dateAndTime;
        }
    }

}
