package diyeats.logic.parsers;

import diyeats.logic.commands.AddGoalCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static diyeats.commons.constants.GoalCommandSyntax.ACTIVITY_ARG_STR;
import static diyeats.commons.constants.GoalCommandSyntax.END_DATE_ARG_STR;
import static diyeats.commons.constants.GoalCommandSyntax.START_DATE_ARG_STR;
import static diyeats.commons.constants.GoalCommandSyntax.WEIGHT_ARG_STR;

//@@author Fractalisk
/**
 * Parser class to handle setting of goals.
 */
public class AddGoalCommandParser implements ParserInterface<AddGoalCommand> {

    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Parse user input and return AddGoalCommand.
     * @param userInputStr String input by user
     * @return <code>AddGoalCommand</code> Command object encapsulating the goal object
     */
    @Override
    public AddGoalCommand parse(String userInputStr) {
        HashMap<String, String> argumentsMap = ArgumentSplitter.splitForwardSlashArguments(userInputStr);
        //validate startdate argument
        if (argumentsMap.containsKey(START_DATE_ARG_STR)) {
            String dateStr = argumentsMap.get(START_DATE_ARG_STR);
            try {
                startDate = LocalDate.parse(dateStr, dateFormat);
            } catch (DateTimeParseException e) {
                return new AddGoalCommand(true, "Unable to parse " + dateStr + " as a date. "
                        + "Please follow DD/MM/YYYY format.");
            }
        }

        //validate enddate argument
        if (argumentsMap.containsKey(END_DATE_ARG_STR)) {
            String dateStr = argumentsMap.get(END_DATE_ARG_STR);
            try {
                endDate = LocalDate.parse(dateStr, dateFormat);
            } catch (DateTimeParseException e) {
                return new AddGoalCommand(true, "Unable to parse " + dateStr + " as a date. "
                        + "Please follow DD/MM/YYYY format.");
            }
            if (argumentsMap.containsKey(START_DATE_ARG_STR) && startDate.isAfter(endDate)) {
                return new AddGoalCommand(true, "It appears startdate is after enddate."
                        + " Please enter a valid set of dates");
            }
        }

        //validate weight argument
        if (argumentsMap.containsKey(WEIGHT_ARG_STR)) {
            String floatStr = argumentsMap.get(WEIGHT_ARG_STR);
            try {
                Double.parseDouble(floatStr);
            } catch (NumberFormatException e) {
                return new AddGoalCommand(true, "Unable to parse " + floatStr + " as a number. "
                        + "Please enter a valid integer.");
            }
            if (Double.parseDouble(floatStr) <= 0) {
                return new AddGoalCommand(true, "Unable to accept a negative or zero number as "
                        + "weight. Please try entering a valid target weight.");
            }
        }

        //validate activity argument
        if (argumentsMap.containsKey(ACTIVITY_ARG_STR)) {
            int parsedValue;
            String intStr = argumentsMap.get(ACTIVITY_ARG_STR);
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

        List<String> validArguments = Arrays.asList(START_DATE_ARG_STR, END_DATE_ARG_STR,
                WEIGHT_ARG_STR, ACTIVITY_ARG_STR);

        if (argumentsMap.keySet().containsAll(validArguments)) {
            return new AddGoalCommand(argumentsMap);
        } else if (argumentsMap.size() < validArguments.size()) {
            return new AddGoalCommand(true, "Some arguments are missing"
                    + ". Type `help goal` to get command syntax.");
        } else if (argumentsMap.size() != 0) {
            return new AddGoalCommand(true, "Invalid arguments present"
                    + ". Type `help goal` to get command syntax.");
        } else {
            return new AddGoalCommand(true, "Invalid setgoal command, no arguments present.");
        }
    }
}
