package owlmoney.logic.parser.goals;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.goals.AddGoalsCommand;
import owlmoney.logic.parser.exception.ParserException;

import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

import static owlmoney.commons.log.LogsCenter.getLogger;

/**
 * Represents the parsing of inputs for adding a new goal.
 */
public class ParseAddGoals extends ParseGoals {

    private static final String ADD_COMMAND = "/add";
    private Date by;
    private static final Logger logger = getLogger(ParseAddGoals.class);

    /**
     * Creates instance of ParseAddGoals Class.
     *
     * @param data raw data of user input.
     * @throws ParserException If there is a redundant parameter or first parameter is of invalid type.
     */
    public ParseAddGoals(String data) throws ParserException {
        super(data);
        checkRedundantParameter(NEW_NAME_PARAMETER, ADD_COMMAND);
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

        checkOptionalParameter(goalsParameters.get(BY_PARAMETER), goalsParameters.get(IN_PARAMETER));

        while (goalsIterator.hasNext()) {
            String key = goalsIterator.next();
            String value = goalsParameters.get(key);
            if (NAME_PARAMETER.equals(key) && (value == null || value.isBlank())) {
                logger.warning("/name parameter cannot be empty when adding goals");
                throw new ParserException(key + " cannot be empty when adding new goals");
            } else if (NAME_PARAMETER.equals(key)) {
                checkGoalsName(NAME_PARAMETER, value);
            }
            if (AMOUNT_PARAMETER.equals(key) && (value == null || value.isBlank())) {
                logger.warning("/amount parameter cannot be empty when adding goals");
                throw new ParserException(key + " cannot be empty when adding new goals");
            } else if (AMOUNT_PARAMETER.equals(key)) {
                checkGoalsAmount(value);
            }
            if (BY_PARAMETER.equals(key) && !(value == null || value.isBlank())) {
                by = checkDate(value);
            }

            if (IN_PARAMETER.equals(key) && !(value == null || value.isBlank())) {
                checkDay(IN_PARAMETER, value);
                by = convertDaysToDate(Integer.parseInt(value));
            }

            if (FROM_PARAMETER.equals(key) && !(value == null || value.isBlank())) {
                checkName(FROM_PARAMETER, value);
            }
        }
    }

    /**
     * Returns command to execute the adding of a new goal.
     *
     * @return AddGoalsCommand to be executed.
     */
    @Override
    public Command getCommand() {
        AddGoalsCommand newAddGoalsCommand = new AddGoalsCommand(goalsParameters.get(NAME_PARAMETER),
                Double.parseDouble(goalsParameters.get(AMOUNT_PARAMETER)), by, goalsParameters.get(FROM_PARAMETER));
        return newAddGoalsCommand;
    }
}
