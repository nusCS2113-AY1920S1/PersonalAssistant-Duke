package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.PostponeCommand;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiTemporary;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Extract the components required for the postpone command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class PostponeParser extends IndexParser {

    private LocalDateTime newFromDate = null;
    private LocalDateTime newToDate = null;

    public PostponeParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        String[] postponeCommandParts = taskFeatures.split("\\s+", 2);
        String dateString;
        try {
            dateString = postponeCommandParts[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            UiTemporary.printOutput(ChronologerException.emptyDateOrTime());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.emptyDateOrTime());
        }
        if (dateString.contains("-")) {
            extractEventDates(dateString);
            return new PostponeCommand(indexOfTask, newFromDate, newToDate);
        } else {
            extractDeadlineDates(dateString);
            return new PostponeCommand(indexOfTask, newFromDate);
        }
    }


    private void extractEventDates(String dateString) throws ChronologerException {
        String obtainStartDate = dateString.split("-", 2)[0].trim();
        String obtainEndDate = dateString.split("-", 2)[1].trim();
        try {
            newFromDate = DateTimeExtractor.extractDateTime(obtainStartDate, command);
            newToDate = DateTimeExtractor.extractDateTime(obtainEndDate, command);
        } catch (DateTimeParseException e) {
            UiTemporary.printOutput(ChronologerException.wrongDateOrTime());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }
    }

    private void extractDeadlineDates(String dateString) throws ChronologerException {
        try {
            newFromDate = DateTimeExtractor.extractDateTime(dateString, command);
        } catch (DateTimeParseException e) {
            UiTemporary.printOutput(ChronologerException.wrongDateOrTime());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }
    }


}
