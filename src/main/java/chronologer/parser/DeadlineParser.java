package chronologer.parser;

import chronologer.command.AddCommand;
import chronologer.command.Command;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiTemporary;

import java.text.ParseException;
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
     * creates new parser for deadline.
     * 
     * @param userInput  input from user
     * @param command    command type
     */
    public DeadlineParser(String userInput, String command) {
        super(userInput, command);
        this.checkType = Flag.BY.getFlag();
        if (userInput.contains("/m")) {
            this.hasModCode = true;
        } else {
            this.hasModCode = false;
        }
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

    private LocalDateTime extractStartDate(String taskFeatures) throws ChronologerException {
        String dateTimeFromUser;
        LocalDateTime startDate;
        try {
            dateTimeFromUser = taskFeatures.split(checkType, 2)[1].trim();
            startDate = DateTimeExtractor.extractDateTime(dateTimeFromUser, command);
        } catch (ArrayIndexOutOfBoundsException e) {
            UiTemporary.printOutput(ChronologerException.emptyDateOrTime());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.emptyDateOrTime());
        } catch (DateTimeParseException e) {
            UiTemporary.printOutput(ChronologerException.wrongDateOrTime());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.wrongDateOrTime());
        }
        return startDate;
    }
}
