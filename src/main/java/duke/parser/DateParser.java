package duke.parser;

import duke.enums.Numbers;

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

        String dayInString = startDate[Numbers.ZERO.value].split("/")[Numbers.ZERO.value];
        String mthInString = startDate[Numbers.ZERO.value].split("/")[Numbers.ONE.value];
        String yrsInString = startDate[Numbers.ZERO.value].split("/")[Numbers.TWO.value];

        LocalDate localDate = LocalDate.of(Integer.parseInt(yrsInString),
                                            Integer.parseInt(mthInString),
                                            Integer.parseInt(dayInString));
        switch (datePeriod) {
        case "days" : localDate = localDate.plusDays(Numbers.ONE.value);
            break;
        case "weeks" : localDate = localDate.plusWeeks(Numbers.ONE.value);
            break;
        case "months": localDate = localDate.plusMonths(Numbers.ONE.value);
            break;
        default:
            break;
        }

        addedDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        addedDate = addedDate.replaceAll("-","/");
        return addedDate;
    }
}