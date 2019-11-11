package duke.parser;

import duke.dukeexception.DukeException;
import duke.enums.Numbers;


/**
 * Represents a date parser that breaks down user input into dates.
 */
public class DateParser {
    private static final int TWELVE = 12;
    private static final int SEVEN = 7;
    private static final int EIGHT = 8;
    private static final int TEN = 10;
    private static final int NINE = 9;
    private static final int ELEVEN = 11;
    private static final int TWENTY_NINE = 29;
    private static final int THIRTY = 30;
    private static final int FIFTY_NINE = 59;
    //    /**
    //     * Creates a date based on the date period.
    //     *
    //     * @param dateDesc The description of the task.
    //     * @param datePeriod The date/time of the task.
    //     * @return String of the date.
    //     */
    //    public static String add(String dateDesc, String datePeriod) {
    //        String[] startDate = dateDesc.split(" ");
    //        String addedDate;
    //
    //        String dayInString = startDate[Numbers.ZERO.value].split("/")[Numbers.ZERO.value];
    //        String mthInString = startDate[Numbers.ZERO.value].split("/")[Numbers.ONE.value];
    //        String yrsInString = startDate[Numbers.ZERO.value].split("/")[Numbers.TWO.value];
    //
    //        LocalDate localDate = LocalDate.of(Integer.parseInt(yrsInString),
    //                                            Integer.parseInt(mthInString),
    //                                            Integer.parseInt(dayInString));
    //        switch (datePeriod) {
    //        case "days" : localDate = localDate.plusDays(Numbers.ONE.value);
    //            break;
    //        case "weeks" : localDate = localDate.plusWeeks(Numbers.ONE.value);
    //            break;
    //        case "months": localDate = localDate.plusMonths(Numbers.ONE.value);
    //            break;
    //        default:
    //            break;
    //        }
    //
    //        addedDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    //        addedDate = addedDate.replaceAll("-","/");
    //        return addedDate;
    //    }

    /**
     * Checks the date/time if its valid.
     *
     * @param dateDesc The description of the task.
     * @throws Exception when date is invalid.
     */
    protected void isValidDateTime(String dateDesc) throws Exception {
        String[] dateArray = dateDesc.split("/");

        if (dateArray.length != Numbers.THREE.value) {
            throw new DukeException("Invalid date format");
        }
        String[] timeArray = dateArray[Numbers.TWO.value].split(" ");

        if (timeArray.length != Numbers.TWO.value) {
            throw new DukeException("Invalid date format");
        }

        if (dateDesc.contains("-")) {
            throw new DukeException("Invalid date format");
        }
        int day;
        int month;
        int year;
        int hours;
        int minutes;
        try {
            day = Integer.parseInt(dateArray[Numbers.ZERO.value]);
        } catch (Exception e) {
            throw new DukeException("Invalid day");
        }
        try {
            month = Integer.parseInt(dateArray[Numbers.ONE.value]);
        } catch (Exception e) {
            throw new DukeException("Invalid month");
        }
        try {
            year = Integer.parseInt(timeArray[Numbers.ZERO.value]);
        } catch (Exception e) {
            throw new DukeException("Invalid year");
        }
        try {
            hours = Integer.parseInt(timeArray[Numbers.ONE.value].substring(Numbers.ZERO.value,Numbers.TWO.value));
        } catch (Exception e) {
            throw new DukeException("Invalid hours");
        }
        try {
            minutes = Integer.parseInt(timeArray[Numbers.ONE.value].substring(Numbers.TWO.value,Numbers.FOUR.value));
        } catch (Exception e) {
            throw new DukeException("Invalid minutes");
        }

        if (year < Numbers.ONE.value) {
            throw new DukeException("Invalid year, and year 0 does not exist");
        }
        if (month < Numbers.ONE.value || month > TWELVE) {
            throw new DukeException("Invalid month");
        }
        if (month == Numbers.ONE.value || month == Numbers.THREE.value || month == Numbers.FIVE.value
                || month == SEVEN || month == EIGHT || month == TEN || month == TWELVE) {
            if (day < Numbers.ONE.value || day > Numbers.THIRTY_ONE.value) {
                throw new DukeException("Invalid day");
            }
        }
        if (month == Numbers.FOUR.value || month == Numbers.SIX.value || month == NINE || month == ELEVEN) {
            if (day < Numbers.ONE.value || day > THIRTY) {
                throw new DukeException("Invalid day");
            }
        }
        if (month == Numbers.TWO.value) {
            if (day < Numbers.ONE.value || day > TWENTY_NINE) {
                throw new DukeException("Invalid day");
            }
        }
        if (hours < Numbers.ZERO.value || hours > Numbers.TWENTY_THREE.value) {
            throw new DukeException("Invalid hours");
        }
        if (minutes < Numbers.ZERO.value || minutes > FIFTY_NINE) {
            throw new DukeException("Invalid minutes");
        }
    }

    private int convertDay(String dayStr) throws Exception {
        int day;
        try {
            day = Integer.parseInt(dayStr);
        } catch (Exception e) {
            throw new DukeException("Invalid day");
        }
        return day;
    }

    private int convertMonth(String monthStr) throws Exception {
        int month;
        try {
            month = Integer.parseInt(monthStr);
        } catch (Exception e) {
            throw new DukeException("Invalid month");
        }
        return month;
    }

    private int convertYear(String yearStr) throws Exception {
        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (Exception e) {
            throw new DukeException("Invalid year");
        }
        return year;
    }

    /**
     * Checks the date if its valid.
     *
     * @param dateDesc The description of the task.
     * @throws Exception when date is invalid.
     */
    protected void isValidDate(String dateDesc) throws Exception {
        String[] dateArray = dateDesc.split("/");

        if (dateArray.length != Numbers.THREE.value) {
            throw new DukeException("Invalid date format");
        }
        if (dateDesc.contains("-")) {
            throw new DukeException("Invalid date format");
        }
        int day;
        int month;
        int year;
        day = convertDay(dateArray[Numbers.ZERO.value]);
        month = convertMonth(dateArray[Numbers.ONE.value]);
        year = convertYear(dateArray[Numbers.TWO.value]);


        if (year < Numbers.ONE.value) {
            throw new DukeException("Invalid year, and year 0 does not exist");
        } else if (month < Numbers.ONE.value || month > TWELVE) {
            throw new DukeException("Invalid month");
        } else if (month == Numbers.ONE.value || month == Numbers.THREE.value || month == Numbers.FIVE.value
                || month == SEVEN || month == EIGHT || month == TEN || month == TWELVE) {
            if (day < Numbers.ONE.value || day > Numbers.THIRTY_ONE.value) {
                throw new DukeException("Invalid day");
            }
        } else if (month == Numbers.FOUR.value || month == Numbers.SIX.value || month == NINE || month == ELEVEN) {
            if (day < Numbers.ONE.value || day > THIRTY) {
                throw new DukeException("Invalid day");
            }
        } else if (month == Numbers.TWO.value) {
            if (day < Numbers.ONE.value || day > TWENTY_NINE) {
                throw new DukeException("Invalid day");
            }
        }
    }
}