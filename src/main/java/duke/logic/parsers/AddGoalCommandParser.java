package duke.logic.parsers;

import duke.logic.commands.AddGoalCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static duke.commons.constants.DateConstants.DATE_FORMAT;

/**
 * Parser class to handle setting of goals.
 */
public class AddGoalCommandParser implements ParserInterface<AddGoalCommand> {
    private SimpleDateFormat dateFormat = DATE_FORMAT;

    /**
     * Parse user input and return AddGoalCommand.
     * @param userInputStr String input by user
     * @return <code>AddGoalCommand</code> Command object encapsulating the goal object
     */
    @Override
    public AddGoalCommand parse(String userInputStr) {
        HashMap<String, String> argumentsMap = ArgumentSplitter.splitForwardSlashArguments(userInputStr);
        //validate startdate argument
        if (argumentsMap.containsKey("startdate")) {
            String dateStr = argumentsMap.get("startdate");
            try {
                dateFormat.parse(dateStr);
            } catch (ParseException e) {
                return new AddGoalCommand(true, "Unable to parse " + dateStr + " as a date. "
                        + "Please follow DD/MM/YYYY format.");
            }
        }

        //validate enddate argument
        if (argumentsMap.containsKey("enddate")) {
            String dateStr = argumentsMap.get("enddate");
            try {
                dateFormat.parse(dateStr);
            } catch (ParseException e) {
                return new AddGoalCommand(true, "Unable to parse " + dateStr + " as a date. "
                        + "Please follow DD/MM/YYYY format.");
            }
        }

        //validate weight argument
        if (argumentsMap.containsKey("weight")) {
            String floatStr = argumentsMap.get("weight");
            try {
                Double.parseDouble(floatStr);
            } catch (NumberFormatException e) {
                return new AddGoalCommand(true, "Unable to parse " + floatStr + " as a number. "
                        + "Please enter a valid integer.");
            }
        }

        //validate activity argument
        if (argumentsMap.containsKey("activity")) {
            int parsedValue;
            String intStr = argumentsMap.get("activity");
            try {
                parsedValue = Integer.parseInt(intStr);
            } catch (NumberFormatException e) {
                return new AddGoalCommand(true, "Unable to parse " + intStr + " as a number. "
                        + "Please enter a valid integer.");
            }
            if (parsedValue > 5 || parsedValue < 1) {
                return new AddGoalCommand(true, "Integer after /activity must belong"
                        + "to the range 1-5");
            }
        }

        List<String> validArguments = Arrays.asList("startdate", "enddate", "weight", "activity");

        if (argumentsMap.keySet().containsAll(validArguments)) {
            return new AddGoalCommand(argumentsMap);
        } else if (argumentsMap.size() < validArguments.size()) {
            return new AddGoalCommand(true, "Some arguments are missing"
                    + ". Type `help goal` to get command syntax.");
        } else if (argumentsMap.size() != 0) {
            return new AddGoalCommand(true, "Invalid arguments present"
                    + ". Type `help goal` to get command syntax.");
        } else {
            return new AddGoalCommand();
        }
    }
}
