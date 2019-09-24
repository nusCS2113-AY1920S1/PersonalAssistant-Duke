package duke;

import duke.exception.DukeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateFormatter {
    private String date;
    private String time;

    /**
     * Creates DateFormatter with the specified DateTime String.
     * @param message the deadline or event time to be processed to give date and time params
     */
    public DateFormatter(String message) {
        String[] splitStr = message.split(" ");

        date = formatDate(splitStr[0]);
        if (splitStr.length == 2) {
            time = formatTime(splitStr[1]);
        }
    }


    private String formatDate(String date) {
        String[] splitDate = date.split("/");
        if (splitDate.length == 3) {
            String day = dayFormat(splitDate[0]);
            String month = monthFormat(splitDate[1]);
            String year = splitDate[2];
            if (month == null || day == null || !isValidDate(splitDate[0], splitDate[1], splitDate[2])) {
                return null;
            }
            return day + month + year;
        } else {
            return null;
        }
    }

    /**
     * Format day of month with suffix.
     * @param day the date to be appended with necessary suffix
     * @return String of the day with its suffix
     */
    private String dayFormat(String day) {
        if (day.equals("1") || day.equals("21") || day.equals("31") || day.equals("01")) {
            day = day + "st of ";
        } else if (day.equals("2") || day.equals("22") || day.equals("02")) {
            return day + "nd of ";
        } else if (day.equals("3") || day.equals("23") || day.equals("03")) {
            return day + "rd of ";
        } else if (Integer.parseInt(day) > 31) {
            return null;
        }

        return day + "th of ";
    }

    /**
     * Format date to give month of year.
     * @param month the month to be converted
     * @return the String format of month
     */
    private String monthFormat(String month) {
        // remove any leading 0
        month = month.replace('0',' ').strip();
        switch (month) {
        case "1":
            return "January ";
        case "2":
            return "February ";
        case "3":
            return "March ";
        case "4":
            return "April ";
        case "5":
            return "May ";
        case "6":
            return "June ";
        case "7":
            return "July ";
        case "8":
            return "August ";
        case "9":
            return "September ";
        case "10":
            return "October ";
        case "11":
            return "November ";
        case "12":
            return "December ";
        default:
            return null;
        }
    }

    /**
     * Checks if date given exists in calendar.
     * @param day to be converted to int to check if day is in range for a particular month
     * @param month for reference to check day is in range for the month
     * @param year to check for leap year exceptions
     * @return {@code true} date can be found in the calendar
     *         {@code false} otherwise
     */
    private boolean isValidDate(String day, String month, String year) {
        int yr = Integer.parseInt(year);
        int mth = Integer.parseInt(month);
        int dy = Integer.parseInt(day);

        if (mth == 2) {
            return (isLeap(yr) && dy <= 29) || (!isLeap(yr) && dy <= 28);
        } else if (mth == 4 || mth == 6 || mth == 9 || mth == 11) {
            return dy <= 30;
        } else {
            return dy <= 31;
        }
    }

    /**
     * Check if it is a leap year.
     * @param year to check whether its a leap year
     * @return {@code true} if it is a leap year
     *         {@code false} otherwise
     */
    private boolean isLeap(int year) {
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }

    /**
     * Format time to 12-Hour clock with suffix.
     * @param time 24-Hour clock
     * @return String of 12-Hour clock with correct suffix
     */
    private String formatTime(String time) {
        String hour = time.substring(0,2);
        String min = time.substring(2,4);
        String suffix = (Integer.parseInt(hour) < 12) ? "am" : "pm";

        if (Integer.parseInt(hour) > 23 || Integer.parseInt(min) > 59) {
            return null;
        }

        if (Integer.parseInt(hour) == 0) {
            hour = "12";
        } else if (Integer.parseInt(hour) > 12) {
            hour = Integer.toString(Integer.parseInt(hour) - 12);
        }

        if (min.equals("00")) {
            return ", " + hour + suffix;
        } else {
            return ", " + hour + "." + min + suffix;
        }
    }

    /**
     * Getter for the new Date and Time format.
     * @return String of new Date and Time format
     */
    public String getDateTime() {
        if (this.time == null) {
            return this.date;
        }

        return this.date + this.time;
    }

    /**
     * Check if Date input is valid to be registered as task.
     * @return {@code true} date input is valid
     *         {@code false} date input is invalid
     */
    public boolean isValidDateTime() {
        return date != null;
    }

    public LocalDateTime toLocalDateTime(String sDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getFormat(sDate));
        LocalDateTime parsedDate = LocalDateTime.parse(sDate, formatter);
        return parsedDate;
    }

    /**
     * Formats object of LocalDateTime class to return String that is commonly used i.e. dd/MM/yyyy HHmm
     * @param time LocalDateTime object
     * @return String that is formatted to dd/MM/yyyy HHmm
     */
    public String formatLocalDateTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        String formattedTime = time.format(formatter);
        return formattedTime;
    }

    private String getFormat(String date) {
        int padCount = 0;
        String format = "";
        String[] timeType = {"d","M","y","H","H","m","m"};
        for (int i = 0; i < date.length(); i += 1) {
            char c = date.charAt(i);
            if (Character.isDigit(c)) {
                format += timeType[padCount];
                if (padCount >= 3) { padCount += 1;}
            } else {
                format += c;
                padCount += 1;
            }
        }
        return format;
    }



    public LocalDateTime changeDate(LocalDateTime currDate, int value, String units) throws DukeException {
        switch (units) {
            case "minutes":
            case "min":
            case "m":
            case "minute":
                return currDate.plusMinutes(value);
            case "hours":
            case "hour":
            case "h":
                return currDate.plus(value, ChronoUnit.HOURS);
            case "days":
            case "day":
            case "d":
                return currDate.plusDays(value);
            case "weeks":
            case "week":
            case "w":
                return currDate.plusWeeks(value);
            case "months":
            case "month":
            case "M":
                return currDate.plusMonths(value);
            case "years":
            case "year":
            case "y":
                return currDate.plusYears(value);
            default:
                throw new DukeException("units of time are wrong.");
        }
    }

}