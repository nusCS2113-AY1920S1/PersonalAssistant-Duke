package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.RemindCommand;
import chronologer.exception.ChronologerException;

/**
 * Extract the components required for the remind command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class RemindParser extends IndexParser {

    public RemindParser(String userInput, String command) {
        super(userInput, command);
        this.checkType = Flag.IN.getFlag();
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        int days = extractDays(taskFeatures);

        return new RemindCommand(indexOfTask, days);
    }

    private int extractDays(String taskFeatures) throws ChronologerException {
        int days;
        String substring = taskFeatures.split(checkType, 2)[1].trim();
        String daysString = substring.split("\\s+", 2)[0].trim();
        try {
            days = Integer.parseInt(daysString);
        } catch (NumberFormatException e) {
            throw new ChronologerException(ChronologerException.unknownUserCommand());
        }

        return days;
    }
}
