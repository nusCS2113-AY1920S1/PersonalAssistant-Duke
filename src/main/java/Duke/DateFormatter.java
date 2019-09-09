package Duke;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateFormatter {
    private String date;
    private String time;
    private final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    /**
     *
     * @param message the deadline or event time to be processed to give date and time params
     */
    public DateFormatter(String message){
        String[] splitStr = message.split(" ");
        if(splitStr.length == 2) {
            date = formatDate(splitStr[0]);
            time = formatTime(splitStr[1]);
        }

        if(date == null || time == null){
            date = message;
            time = null;
        }
    }


    private String formatDate(String date){
        String[] splitDate = date.split("/");
        if(splitDate.length == 3) {
            String day = dayFormat(splitDate[0]);
            String month = monthFormat(splitDate[1]);
            String year = splitDate[2];
            if (month == null || day == null || !isValidDate(splitDate[0], splitDate[1], splitDate[2]))
                return null;

            return day + month + year + ", ";
        } else {
            return null;
        }
    }

    /**
     *
     * @param day the date to be appended with necessary suffix
     * @return String of the day with its suffix
     */
    private String dayFormat(String day){
        if(day.equals("1") || day.equals("21") || day.equals("31")){
            day = day + "st of ";
        } else if(day.equals("2") || day.equals("22")){
            return day + "nd of ";
        } else if(day.equals("3") || day.equals("23")){
            return day + "rd of ";
        } else if (Integer.parseInt(day) > 31){
            return null;
        }

        return day + "th of ";
    }

    /**
     *
     * @param month the month to be converted
     * @return the String format of month
     */
    private String monthFormat(String month){
        switch (month){
            case "1":
                return "January ";
            case "2":
                return"February ";
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
     *
     * @param day to be converted to int to check if day is in range for a particular month
     * @param month for reference to check day is in range for the month
     * @param year to check for leap year exceptions
     * @return {@code true} date can be found in the calendar
     *         {@code false} otherwise
     */
    private boolean isValidDate(String day, String month, String year){
        int yr = Integer.parseInt(year);
        int mth = Integer.parseInt(month);
        int dy = Integer.parseInt(day);

        if(mth == 2){
            return (isLeap(yr) && dy <= 29) || (!isLeap(yr) && dy <= 28);
        } else if(mth == 4|| mth == 6|| mth == 9 || mth == 11){
            return dy <= 30;
        } else {
            return dy <= 31;
        }
    }

    /**
     *
     * @param year to check whether its a leap year
     * @return {@code true} if it is a leap year
     *         {@code false} otherwise
     */
    private boolean isLeap(int year){
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }

    /**
     *
     * @param time 24-Hour clock
     * @return String of 12-Hour clock with correct suffix
     */
    private String formatTime(String time){
        String hour = time.substring(0,2);
        String min = time.substring(2,4);
        String suffix = (Integer.parseInt(hour) < 12) ? "am" : "pm";

        if(Integer.parseInt(hour) > 23 || Integer.parseInt(min) > 59)
            return null;

        if(Integer.parseInt(hour) == 0)
            hour = "12";
        else if(Integer.parseInt(hour) > 12)
            hour = Integer.toString(Integer.parseInt(hour) - 12);

        if(min.equals("00"))
            return hour + suffix;
        else
            return hour + "." + min + suffix;
    }

    public String getDateTime(){
        if(this.time == null){
            return this.date;
        }

        return this.date + this.time;
    }

    public boolean isValidDateTime() {
        return date != null && time != null;
    }
}
