package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.WeekCommand;
import chronologer.exception.ChronologerException;

/**
 * Extracts the week the user wants.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class WeekParser extends DescriptionParser {
    private static final int CURRENT_WEEK_INDICATOR = -1;
    private static final int FIRST_WEEK = 0;
    private static final int FINAL_WEEK = 18;

    WeekParser(String userInput, String command)  {
        super(userInput, command);
    }

    /**
     * Processes and determines the week the user wants.
     *
     * @param taskFeatures contains the integer the user provides.
     */
    private int extractWeek(String taskFeatures) throws ChronologerException {
        if (taskFeatures.contains("current")) {
            return CURRENT_WEEK_INDICATOR;
        }
        int desiredWeek = Integer.parseInt(taskFeatures);
        if (desiredWeek >= FIRST_WEEK && desiredWeek <= FINAL_WEEK) {
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
