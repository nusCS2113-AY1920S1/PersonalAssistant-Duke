import exception.DukeException;

/**
 * Represents a date object that can reformat itself.
 */
public class Date {

    /**
     * Pieces together the date and time segments and returns the date in a more readable format.
     * @param dateString user input directly from CLI
     * @return date in the form 23rd of January 2019, 3.00pm
     */
    public String convertDate(String dateString) throws DukeException {
        String[] dateTime = dateString.split(" ");
        String[] date = dateTime[0].split("/");
        if (date.length == 3) {
            try {
                int day = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int year = Integer.parseInt(date[2]);
                if (month > 7) {
                    if (month % 2 == 1 && day == 31) {
                        throw new DukeException(DukeException.ErrorType.INVALID_DATE_TIME);
                    }
                } else {
                    if (month % 2 == 0 && day == 31) {
                        throw new DukeException(DukeException.ErrorType.INVALID_DATE_TIME);
                    }
                    if (month == 2) {
                        if (isLeapYear(year) && day > 29) {
                            throw new DukeException(DukeException.ErrorType.INVALID_DATE_TIME);
                        }
                        if (!isLeapYear(year) && day > 28) {
                            throw new DukeException(DukeException.ErrorType.INVALID_DATE_TIME);
                        }
                    }
                }
                if (dateTime.length == 1) { //user only input date
                    return getDay(day) + " of " + getMonth(month) + " "
                            + date[2];
                } else if (dateTime.length == 2) { //user input date and time
                    return getDay(day) + " of " + getMonth(month) + " "
                            + date[2] + ", " + getTime(dateTime[1]);
                } else {
                    return "null";
                }
            } catch (DukeException e) {
                throw new DukeException(DukeException.ErrorType.INVALID_DATE_TIME);
            }
        } else {
            return "null";
        }
    }

    /**
     * Checks the date input and assign an appropriate suffix ie "st", "nd" etc to the date.
     * @param integerDay user input DD e.g. 23
     * @return DD + date suffix e.g. 23rd
     */
    public String getDay(int integerDay) throws DukeException {
        String suffix = "";
        if (integerDay < 1 || integerDay > 31) {
            throw new DukeException(DukeException.ErrorType.INVALID_DATE_TIME);
        }
        if (integerDay == 11 || integerDay == 12 || integerDay == 13) {
            suffix = "th";
        } else {
            int lastDigit = integerDay % 10;
            switch (lastDigit) {
            case 1:
                suffix = "st";
                break;
            case 2:
                suffix = "nd";
                break;
            case 3:
                suffix = "rd";
                break;
            default : suffix = "th";
            }
        }
        return integerDay + suffix;
    }

    /**
     * Obtains the month from an integer input.
     * @param month user input MM; leading 0s are removed during integer parsing in convertDate
     *              method
     * @return the name of the month in english
     */
    public String getMonth(int month) throws DukeException {
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
            throw new DukeException(DukeException.ErrorType.INVALID_DATE_TIME);
        }
    }

    /**
     * Converts the date from 24h format to English form h.mm(am/pm).
     * @param time time segment from user input
     * @return formatted time e.g. 3.45pm
     */
    public String getTime(String time) throws DukeException{

        String hour = null;
        String minutes = null;
        String indicator = null;

        if ((time.charAt(0) == '0') || (Integer.parseInt(time.substring(0,2)) < 12)) {
            if ((time.charAt(0) == '0') && (time.charAt(1) == '0')) {
                hour = "12";
                minutes = time.substring(2);
            } else {
                hour = String.valueOf(Integer.parseInt(time.substring(0,2)));
                minutes = time.substring(2);
            }
            indicator = "am";
        } else {
            if (Integer.parseInt(time.substring(0,2)) == 12) {
                hour = "12";
                minutes = time.substring(2);
            } else {
                hour = Integer.toString(Integer.parseInt(time.substring(0, 2)) - 12);
                minutes = time.substring(2);
            }
            indicator = "pm";
        }
        int intHour = Integer.parseInt(hour);
        int intMin = Integer.parseInt(minutes);
        if (intHour < 0 || intHour > 12 || intMin < 0 || intMin > 60) {
            throw new DukeException(DukeException.ErrorType.INVALID_DATE_TIME);
        }
        return hour + "." + minutes + indicator;
    }
    /**
     * Converts the numbers to time duration.
     * @param time time segment from user input
     * @return formatted duration e.g. 2hr45min
     */
    public String getDuration(String time) {
        int hour = 0;
        int minutes = 0;
        int number;
        try {
            number = Integer.parseInt(time);
        } catch (NumberFormatException ex) {
            return null;
        }
        if (number % 100 >= 60) {
            return null;
        }
        minutes = number % 100;
        hour = number / 100;
        return hour + "hr" + minutes + "min";
    }

    private boolean isLeapYear(int y) {
        return (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
    }
}
