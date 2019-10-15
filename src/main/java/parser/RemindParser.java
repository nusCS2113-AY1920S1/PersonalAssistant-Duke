package parser;

import command.Command;
import command.RemindCommand;
import exception.DukeException;

public class RemindParser extends IndexParser {

    public RemindParser(String userInput, String command) {
        super(userInput, command);
        this.checkType = Flag.IN.getFlag();
    }

    @Override
    public Command parse() throws DukeException {
        super.extract();
        int days = extractDays(taskFeatures);

        return new RemindCommand(indexOfTask, days);
    }

    private int extractDays(String taskFeatures) throws DukeException {
        int days;
        String substring = taskFeatures.split(checkType, 2)[1].trim();
        String daysString = substring.split("\\s+", 2)[0].trim();
        try {
            days = Integer.parseInt(daysString);
        } catch (NumberFormatException e) {
            throw new DukeException(DukeException.unknownUserCommand());
        }

        return days;
    }
}
