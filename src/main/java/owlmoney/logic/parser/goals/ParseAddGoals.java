package owlmoney.logic.parser.goals;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.goals.AddGoalsCommand;
import owlmoney.logic.parser.exception.ParserException;

import java.util.Date;
import java.util.Iterator;

/**
 * Represents the parsing of inputs for adding a new goal.
 */
public class ParseAddGoals extends ParseGoals {

    private static final String ADD = "/add";
    private Date by;

    /**
     * Creates instance of ParseAddGoals Class.
     *
     * @param data raw data of user input.
     * @throws ParserException If there is a redundant parameter or first parameter is of invalid type.
     */
    public ParseAddGoals(String data) throws ParserException {
        super(data);
        checkRedundantParameter(NEW_NAME, ADD);
        //check wrong parameter e.g. if user accidentally keys in /date instead of by
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are nay invalid or missing input.
     */
    @Override
    public void checkParameter() throws ParserException {
        Iterator<String> goalsIterator = goalsParameters.keySet().iterator();

        checkOptionalParameter(goalsParameters.get(BY), goalsParameters.get(IN));

        while (goalsIterator.hasNext()) {
            String key = goalsIterator.next();
            String value = goalsParameters.get(key);
            if (NAME.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding new goals");
            } else if (NAME.equals(key)) {
                checkName(NAME, value);
            }
            if (AMOUNT.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding new goals");
            } else if (AMOUNT.equals(key)) {
                checkAmount(value);
            }
            if (BY.equals(key) && (!value.isBlank())) {
                by = checkDate(value);
               // countNumOfDays(,by);
            }

            if (IN.equals(key) && (!value.isBlank())) {
                checkInt(IN, value);
                by = convertDaysToDate(Integer.parseInt(value));
            }

            if (FROM.equals(key) && (!value.isBlank())) {
                checkName(FROM, value);
            }
        }
    }

    /**
     * Checks if only one of /by or /in is provided for Goals deadline.
     *
     * @param by Date of goals deadline.
     * @param in Days of goals deadline.
     * @throws ParserException If both /by and /in provided, or none provided.
     */
    void checkOptionalParameter(String by, String in) throws ParserException {
        if (by.isBlank() && in.isBlank()) {
            throw new ParserException("/by and /in cannot be both empty when adding new goals");
        } else if (!by.isBlank() && !in.isBlank()) {
            throw new ParserException("/by and /in cannot be specified concurrently when adding new goals");
        }
    }

    /**
     * Returns command to execute the adding of a new goal.
     *
     * @return AddGoalsCommand to be executed.
     */
    @Override
    public Command getCommand() {
        AddGoalsCommand newAddGoalsCommand = new AddGoalsCommand(goalsParameters.get(NAME),
                Double.parseDouble(goalsParameters.get(AMOUNT)), by, goalsParameters.get(FROM));
        return newAddGoalsCommand;
    }
}
