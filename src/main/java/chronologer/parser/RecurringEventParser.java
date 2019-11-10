package chronologer.parser;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import chronologer.command.AddRecurringCommand;
import chronologer.command.Command;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiMessageHandler;
import javafx.util.Pair;

//@@author hanskw4267
/**
 * Processes input for recurring events.
 * 
 * @author Hans kurnia
 * @version 1.0
 */
public class RecurringEventParser extends EventParser {

    /**
     * Creates a new parser to handle recurring events.
     * 
     * @param userInput input from user
     * @param command   command-type of input
     */
    public RecurringEventParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        LocalDateTime fromDate;
        LocalDateTime toDate;
        String modCode = "";
        modCode = extractModCode(taskFeatures);
        String tillDate = extractTillDate(taskFeatures);
        String dateTimeFromUser = formatDateTime(tillDate, taskFeatures);
        fromDate = super.extractFromDate(dateTimeFromUser);
        toDate = super.extractToDate(dateTimeFromUser);
        assert toDate != null;
        assert fromDate != null;
        DayOfWeek day = extractDay(taskFeatures);
        Pair<LocalDateTime, LocalDateTime> orderedDates = super.checkDateOrder(fromDate, toDate);
        fromDate = orderedDates.getKey();
        toDate = orderedDates.getValue();
        return new AddRecurringCommand(command, taskDescription, fromDate, toDate, modCode, day);
    }

    private DayOfWeek extractDay(String taskFeatures) throws ChronologerException {
        try {
            String dateTimeFromUser = taskFeatures.split(checkType, 2)[1].trim();
            String dayFromUser = dateTimeFromUser.split(" ", 2)[0].trim().toUpperCase();
            return LastDay.getDay(dayFromUser);
        } catch (ArrayIndexOutOfBoundsException | PatternSyntaxException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.emptyDateOrTime());
        }
    }

    private String extractTillDate(String taskFeatures) throws ChronologerException {
        Pattern patt = Pattern.compile("/till\\s+\\d{2}/\\d{2}/\\d{4}");
        Matcher matcher = patt.matcher(taskFeatures);
        if (matcher.find()) {
            return matcher.group().split("\\s")[1]; // you can get it from desired index as well
        } else {
            logger.writeLog(ChronologerException.emptyDateOrTime(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.emptyDateOrTime());
        }
    }

    private String formatDateTime(String date, String taskFeatures) throws ChronologerException {
        try {
            String timeFromUser = taskFeatures.split(checkType, 2)[1].trim().split(" ")[1];
            String startDate = date + " " + timeFromUser.split("-")[0];
            String endDate = date + " " + timeFromUser.split("-")[1];
            return startDate + "-" + endDate;
        } catch (ArrayIndexOutOfBoundsException | PatternSyntaxException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.emptyDateOrTime());
        }
    }
}