package chronologer.parser;

import chronologer.command.AddCommand;
import chronologer.command.Command;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiTemporary;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Extract the components required to create an event class.
 *
 * @author Tan Yi Xiang
 * @version v1.1
 */
public class EventParser extends DescriptionParser {

    /**
     * Creates new parser for Event.
     *
     * @param userInput input from user
     * @param command   command type
     */
    EventParser(String userInput, String command) {
        super(userInput, command);
        this.checkType = Flag.AT.getFlag();
        this.hasModCode = userInput.contains("/m");
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        LocalDateTime fromDate;
        LocalDateTime toDate;

        String dateTimeFromUser = extractDateTimeString(taskFeatures);
        fromDate = extractFromDate(dateTimeFromUser);
        toDate = extractToDate(dateTimeFromUser);

        assert toDate != null;
        assert fromDate != null;

        if (hasModCode) {
            String modCode = extractModCode(taskFeatures);
            return new AddCommand(command, taskDescription, fromDate, toDate, modCode);
        }
        return new AddCommand(command, taskDescription, fromDate, toDate);
    }

    /**
     * Extract and converts from date component in user input.
     *
     * @param dateTimeFromUser The user input.
     * @return The converted from date.
     * @throws ChronologerException If there's error parsing the from date component.
     */
    LocalDateTime extractFromDate(String dateTimeFromUser) throws ChronologerException {
        try {
            String fromDateString = dateTimeFromUser.split("-", 2)[0].trim();
            return DateTimeExtractor.extractDateTime(fromDateString, command);
        } catch (DateTimeParseException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            UiTemporary.printOutput(ChronologerException.wrongDateOrTime());
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }
    }

    /**
     * Extract and converts To Date component in user input.
     *
     * @param dateTimeFromUser The user input.
     * @return The converted To Date.
     * @throws ChronologerException If there's error parsing the To Date component.
     */
    LocalDateTime extractToDate(String dateTimeFromUser) throws ChronologerException {
        try {
            String toDateString = dateTimeFromUser.split("-", 2)[1].trim();
            return DateTimeExtractor.extractDateTime(toDateString, command);
        } catch (DateTimeParseException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            UiTemporary.printOutput(ChronologerException.wrongDateOrTime());
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }
    }

    /**
     * Extract the date components from user input.
     *
     * @param taskFeatures The user input.
     * @return The date time strings.
     * @throws ChronologerException If the date components are empty.
     */
    String extractDateTimeString(String taskFeatures) throws ChronologerException {
        try {
            return taskFeatures.split(checkType, 2)[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            UiTemporary.printOutput(ChronologerException.emptyDateOrTime());
            throw new ChronologerException(ChronologerException.emptyDateOrTime());
        }
    }

}
