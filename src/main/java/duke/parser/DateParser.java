package duke.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a date parser that breaks down user input into dates.
 */
public class DateParser {

    /**
     * Creates a date based on the date period.
     *
     * @param dateDesc The description of the task.
     * @param datePeriod The date/time of the task.
     * @return String of the date.
     */
    public static String add(String dateDesc, String datePeriod) {
        String[] startDate = dateDesc.split(" ");
        String addedDate;

        String dayInString = startDate[0].split("/")[0];
        String mthInString = startDate[0].split("/")[1];
        String yrsInString = startDate[0].split("/")[2];

        LocalDate localDate = LocalDate.of(Integer.parseInt(yrsInString),
                                            Integer.parseInt(mthInString),
                                            Integer.parseInt(dayInString));
        switch (datePeriod) {
        case "days" : localDate = localDate.plusDays(1);
            break;
        case "weeks" : localDate = localDate.plusWeeks(1);
            break;
        case "months": localDate = localDate.plusMonths(1);
            break;
        default:
            break;
        }

        addedDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        addedDate = addedDate.replaceAll("-","/");
        return addedDate;
    }
}