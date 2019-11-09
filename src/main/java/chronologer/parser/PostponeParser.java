package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.PostponeCommand;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiMessageHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Extract the components required for the postpone command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.1
 */
public class PostponeParser extends IndexParser {

    private LocalDateTime newFromDate = null;
    private LocalDateTime newToDate = null;

    PostponeParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        String[] postponeCommandParts = extractPostponeCommandParts(taskFeatures);
        String dateString = extractDateString(postponeCommandParts);

        if (isEventDateFormat(dateString)) {
            extractEventDates(dateString);
            return new PostponeCommand(indexOfTask, newFromDate, newToDate);
        } else {
            extractDeadlineDates(dateString);
            return new PostponeCommand(indexOfTask, newFromDate);
        }
    }

    /**
     * Extract and split the user input into parts.
     *
     * @param taskFeatures The user input
     * @return The user input but split into multiple parts.
     */
    private String[] extractPostponeCommandParts(String taskFeatures) {
        return taskFeatures.split("\\s+", 2);
    }

    /**
     * Obtain the date part from the split user input.
     *
     * @param postponeCommandParts The user input for postpone command but split into multiple parts.
     * @return The part corresponding to the date components
     * @throws ChronologerException If the date component is empty.
     */
    private String extractDateString(String[] postponeCommandParts) throws ChronologerException {
        try {
            return postponeCommandParts[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            UiMessageHandler.outputMessage(ChronologerException.emptyDateOrTime());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.emptyDateOrTime());
        }
    }

    /**
     * Extract and convert the date components required to postpone events.
     *
     * @param dateString The date components from user input
     * @throws ChronologerException If there's an error converting the date components.
     */
    private void extractEventDates(String dateString) throws ChronologerException {
        String obtainStartDate = dateString.split("-", 2)[0].trim();
        String obtainEndDate = dateString.split("-", 2)[1].trim();
        try {
            newFromDate = DateTimeExtractor.extractDateTime(obtainStartDate, command);
            newToDate = DateTimeExtractor.extractDateTime(obtainEndDate, command);
        } catch (DateTimeParseException e) {
            UiMessageHandler.outputMessage(ChronologerException.wrongDateOrTime());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }
    }

    /**
     * Extract and convert the date components required to postpone deadlines.
     *
     * @param dateString The date components from user input
     * @throws ChronologerException If there's an error converting the date components.
     */
    private void extractDeadlineDates(String dateString) throws ChronologerException {
        try {
            newFromDate = DateTimeExtractor.extractDateTime(dateString, command);
        } catch (DateTimeParseException e) {
            UiMessageHandler.outputMessage(ChronologerException.wrongDateOrTime());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }
    }

    /**
     * Check if the date component follows the event date format.
     *
     * @param dateString The date component string.
     * @return True if the date components follows the event date format.
     */
    private boolean isEventDateFormat(String dateString) {
        return dateString.contains("-");
    }


}
