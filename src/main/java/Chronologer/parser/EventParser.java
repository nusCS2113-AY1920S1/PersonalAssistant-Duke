package chronologer.parser;

import chronologer.command.AddCommand;
import chronologer.command.Command;
import chronologer.exception.ChronologerException;

import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * Extract the components required to create an event class.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class EventParser extends DescriptionParser {

    public EventParser(String userInput, String command) {
        super(userInput, command);
        this.checkType = Flag.AT.getFlag();
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
            throw new ChronologerException(ChronologerException.emptyDateOrTime());
        }
        assert toDate != null;
        assert fromDate != null;
        return new AddCommand(command, taskDescription, fromDate, toDate);
    }

    private LocalDateTime extractFromDate(String dateTimeFromUser) throws ChronologerException {
        try {
            String fromDateString = dateTimeFromUser.split("-", 2)[0].trim();
            return DateTimeExtractor.extractDateTime(fromDateString, command);
        } catch (ParseException e) {
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }
    }

    private LocalDateTime extractToDate(String dateTimeFromUser) throws ChronologerException {
        try {
            String toDateString = dateTimeFromUser.split("-", 2)[1].trim();
            return DateTimeExtractor.extractDateTime(toDateString, command);
        } catch (ParseException e) {
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }
    }


}
