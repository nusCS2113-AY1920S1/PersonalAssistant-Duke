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
 * @version v1.0
 */
public class EventParser extends DescriptionParser {
    
    /**
     * creates new parser for Event.
     * 
     * @param userInput  input from user
     * @param command    command type
     */
    public EventParser(String userInput, String command) {
        super(userInput, command);
        this.checkType = Flag.AT.getFlag();
        if (userInput.contains("/m")) {
            this.hasModCode = true;
        } else {
            this.hasModCode = false;
        }
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        LocalDateTime fromDate;
        LocalDateTime toDate;
        try {
            String dateTimeFromUser = taskFeatures.split(checkType, 2)[1].trim();
            fromDate = extractFromDate(dateTimeFromUser);
            toDate = extractToDate(dateTimeFromUser);
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            UiTemporary.printOutput(ChronologerException.emptyDateOrTime());
            throw new ChronologerException(ChronologerException.emptyDateOrTime());
        }
        if (hasModCode) {
            String modCode = extractModCode(taskFeatures);
            return new AddCommand(command, taskDescription, fromDate, toDate, modCode);
        }
        assert toDate != null;
        assert fromDate != null;
        return new AddCommand(command, taskDescription, fromDate, toDate);
    }

    protected LocalDateTime extractFromDate(String dateTimeFromUser) throws ChronologerException {
        try {
            String fromDateString = dateTimeFromUser.split("-", 2)[0].trim();
            return DateTimeExtractor.extractDateTime(fromDateString, command);
        } catch (DateTimeParseException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            UiTemporary.printOutput(ChronologerException.wrongDateOrTime());
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }
    }

    protected LocalDateTime extractToDate(String dateTimeFromUser) throws ChronologerException {
        try {
            String toDateString = dateTimeFromUser.split("-", 2)[1].trim();
            return DateTimeExtractor.extractDateTime(toDateString, command);
        } catch (DateTimeParseException e) {
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            UiTemporary.printOutput(ChronologerException.wrongDateOrTime());
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }
    }

}
