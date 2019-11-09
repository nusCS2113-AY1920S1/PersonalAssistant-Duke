package chronologer.parser;

import chronologer.command.AddCommand;
import chronologer.command.Command;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiMessageHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Extract the components to add a deadline .
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class DeadlineParser extends DescriptionParser {

    /**
     * Creates new parser for deadline.
     *
     * @param userInput input from user
     * @param command   command type
     */
    DeadlineParser(String userInput, String command) {
        super(userInput, command);
        this.checkType = Flag.BY.getFlag();
        this.hasModCode = userInput.contains("/m");
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        LocalDateTime startDate = extractStartDate(taskFeatures);
        if (hasModCode) {
            String modCode = extractModCode(taskFeatures);
            return new AddCommand(command, taskDescription, startDate, null, modCode);
        }
        return new AddCommand(command, taskDescription, startDate, null);
    }

    /**
     * Extract and converts start date component in user input.
     *
     * @param taskFeatures The user input.
     * @return The converted start date.
     * @throws ChronologerException If there's error parsing the start date component or if isn't any.
     */
    private LocalDateTime extractStartDate(String taskFeatures) throws ChronologerException {
        String dateTimeFromUser;
        LocalDateTime startDate;
        try {
            dateTimeFromUser = taskFeatures.split(checkType, 2)[1].trim();
            startDate = DateTimeExtractor.extractDateTime(dateTimeFromUser, command);
        } catch (ArrayIndexOutOfBoundsException e) {
            UiMessageHandler.outputMessage(ChronologerException.emptyDateOrTime());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.emptyDateOrTime());
        } catch (DateTimeParseException e) {
            UiMessageHandler.outputMessage(ChronologerException.wrongDateOrTime());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }
        return startDate;
    }
}
