package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.WeekCommand;
import chronologer.exception.ChronologerException;

public class WeekParser extends DescriptionParser {
    private static final int CURRENT_WEEK_INDICATOR = -1;

    WeekParser(String userInput, String command)  {
        super(userInput, command);
    }

    private int extractWeek(String taskFeatures) throws ChronologerException {
        if (taskFeatures.contains("current")) {
            return CURRENT_WEEK_INDICATOR;
        }
        int desiredWeek = Integer.parseInt(taskFeatures);
        if (desiredWeek >= 0 && desiredWeek <= 18) {
            return desiredWeek;
        } else {
            throw new ChronologerException(ChronologerException.invalidWeek());
        }
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        int choiceOfWeek = extractWeek(taskFeatures);
        return new WeekCommand(choiceOfWeek);
    }
}
