/**
 * Represents a date object that can reformat itself.
 * @author Zhang Yue Han
 */
public class Date {

    /**
     * Pieces together the date and time segments and returns the date in a more readable format.
     * @param dateString user input directly from CLI
     * @return date in the form 23rd of January 2019, 3.00pm
     */
    public String convertDate(String dateString) {
        String[] dateTime = dateString.split(" ");
        String[] date = dateTime[0].split("/");
        if (date.length == 3) {
            if (dateTime.length == 1) { //user only input date
                return getDay(date[0]) + " of " + getMonth(Integer.parseInt(date[1])) + " "
                        + date[2];
            } else if (dateTime.length == 2) { //user input date and time
                return getDay(date[0]) + " of " + getMonth(Integer.parseInt(date[1])) + " "
                        + date[2] + ", " + getTime(dateTime[1]);
            } else {
                return "null";
            }
        } else {
            return "null";
        }
    }

    /**
     * Checks the date input and assign an appropriate suffix ie "st", "nd" etc to the date.
     * @param day user input DD e.g. 23
     * @return DD + date suffix e.g. 23rd
     */
    public String getDay(String day) {
        int integerDay = Integer.parseInt(day);
        String suffix = "";
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
        return day + suffix;
    }

    /**
     * Obtains the month from an integer input.
     * @param month user input MM; leading 0s are removed during integer parsing in convertDate
     *              method
     * @return the name of the month in english
     */
    public String getMonth(int month) {
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
            return "null";
        }
    }

    /**
     * Converts the date from 24h format to English form h.mm(am/pm).
     * @param time time segment from user input
     * @return formatted time e.g. 3.45pm
     */
    public String getTime(String time) {

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
        return hour + "." + minutes + indicator;
    }

}
