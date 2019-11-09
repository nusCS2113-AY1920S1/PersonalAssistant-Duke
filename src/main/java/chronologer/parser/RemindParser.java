package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.RemindCommand;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiTemporary;

/**
 * Extract the components required for the remind command from the user input.
 *
 * @author Fauzan
 * @version v1.1
 */
public class RemindParser extends IndexParser {

    RemindParser(String userInput, String command) {
        super(userInput, command);
        this.checkType = Flag.IN.getFlag();
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        int days = extractDays(taskFeatures);
        return new RemindCommand(indexOfTask, days);
    }

    /**
     * Extract the reminder days component from user input.
     *
     * @param taskFeatures The user input
     * @return The days left until reminder.
     * @throws ChronologerException If the reminder format is invalid.
     */
    private int extractDays(String taskFeatures) throws ChronologerException {
        int days;
        String substring = taskFeatures.split(checkType, 2)[1].trim();
        String daysString = substring.split("\\s+", 2)[0].trim();
        try {
            days = Integer.parseInt(daysString);
        } catch (NumberFormatException e) {
            UiTemporary.printOutput(ChronologerException.unknownUserCommand());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.unknownUserCommand());
        }

        return days;
    }
}
