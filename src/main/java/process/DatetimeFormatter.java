package process;

import process.DukeException;

/**
 * Represents a datetime formatter
 */
public class DatetimeFormatter {
    /**
     * Validates that the datetime is valid
     * @param str1 the datetime in the form of a String to be checked
     * @return str1 back if it is a valid datetime
     * @throws DukeException if str1 is not a valid datetime
     */
    public static String check(String str1) throws DukeException {
        try {
            str1 = str1.trim();
            String[] datetime = str1.split(" ");
            String[] ddmmyyyy = datetime[0].split("/");
            String day = ddmmyyyy[0];
            String month = ddmmyyyy[1];
            String year = ddmmyyyy[2];
            String time = datetime[1];
            int[] daysinmonth = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            int month_i = Integer.parseInt(month);
            int year_i = Integer.parseInt(year);
            Integer day_i = Integer.parseInt(day);
            String min = time.substring(2);
            String hr = time.substring(0, 2);
            Integer min_i = Integer.parseInt(min);
            Integer hr_i = Integer.parseInt(hr);
            if (hr_i > 23 || min_i > 59 || month_i > 12 || year_i < 0 || day_i > daysinmonth[month_i - 1])
                throw new DukeException("datetime");
            return str1;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("datetime");
        } catch (NumberFormatException e) {
            throw new DukeException("datetime");
        }
    }

    public static String checkDateOnly(String str1) throws DukeException {
        try {
            str1 = str1.trim();
            String[] ddmmyyyy = str1.split("/");
            String day = ddmmyyyy[0];
            String month = ddmmyyyy[1];
            String year = ddmmyyyy[2];
            int[] daysinmonth = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            int month_i = Integer.parseInt(month);
            int year_i = Integer.parseInt(year);
            Integer day_i = Integer.parseInt(day);
            if (month_i > 12 || year_i < 0 || day_i > daysinmonth[month_i - 1])
                throw new DukeException("datetime");
            return str1;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("datetime");
        } catch (NumberFormatException e) {
            throw new DukeException("datetime");
        }
    }

    /**
     * Formats the datetime object to match the exact format required
     * @param str1 the datetime in the form of a String to be formatted
     * @return the datetime in the exact format required
     */
    public static String view(String str1) {
        try {
            str1 = str1.trim();
            String[] datetime = str1.split(" ");
            String day = datetime[0];
            String[] stndrd = {"st", "nd", "rd"};
            Integer day_i = Integer.parseInt(day);
            String day_x;
            if (day_i >= 10 && day_i <= 20 || day_i % 10 > 3 || day_i % 10 == 0) {
                day_x = day_i.toString() + "th";
            } else {
                day_x = day_i.toString() + stndrd[day_i % 10 - 1];
            }
            str1 = day_x + str1.substring(2);
            int min_start = str1.indexOf(':');
            String min = "" + str1.charAt(min_start + 1) + str1.charAt(min_start + 2);
            String ampm = str1.substring(str1.length() - 2).toLowerCase();
            if (Integer.parseInt(min) == 0) {
                str1 = str1.substring(0, min_start) + ampm;
            }
            else {
                str1 = str1.substring(0, str1.length() - 3) + ampm;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return str1;
    }

    public static String viewDateOnly(String str1) {
        try {
            str1 = str1.trim();
            String[] datetime = str1.split(" ");
            String day = datetime[0];
            String[] stndrd = {"st", "nd", "rd"};
            Integer day_i = Integer.parseInt(day);
            String day_x;
            if (day_i >= 10 && day_i <= 20 || day_i % 10 > 3 || day_i % 10 == 0) {
                day_x = day_i.toString() + "th";
            } else {
                day_x = day_i.toString() + stndrd[day_i % 10 - 1];
            }
            str1 = day_x + str1.substring(2);
            int min_start = str1.indexOf(':');
            String min = "" + str1.charAt(min_start + 1) + str1.charAt(min_start + 2);
            String ampm = str1.substring(str1.length() - 2).toLowerCase();
            if (Integer.parseInt(min) == 0) {
                str1 = str1.substring(0, min_start) + ampm;
            }
            else {
                str1 = str1.substring(0, str1.length() - 3) + ampm;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return str1;
    }

}