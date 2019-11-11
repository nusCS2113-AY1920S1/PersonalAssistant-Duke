package owlmoney.logic.parser.goals;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.goals.EditGoalsCommand;
import owlmoney.logic.parser.exception.ParserException;

import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

import static owlmoney.commons.log.LogsCenter.getLogger;

/**
 * Represents the parsing of inputs for editing a goal.
 */
public class ParseEditGoals extends ParseGoals {

    private Date by;
    private boolean markDone;
    private static final Logger logger = getLogger(ParseEditGoals.class);

    /**
     * Creates an instance of ParseEditGoals class.
     *
     * @param data raw data of the input.
     * @throws ParserException If valid parameters.
     */
    public ParseEditGoals(String data) throws ParserException {
        super(data);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are any invalid user input.
     */
    @Override
    public void checkParameter() throws ParserException {
        Iterator<String> goalIterator = goalsParameters.keySet().iterator();
        checkOptionalParameter(goalsParameters.get(BY_PARAMETER), goalsParameters.get(IN_PARAMETER));

        int changeCounter = 0;
        int markDoneCounter = 0;
        while (goalIterator.hasNext()) {
            String key = goalIterator.next();
            String value = goalsParameters.get(key);
            if (NAME_PARAMETER.equals(key) && (value == null || value.isBlank())) {
                logger.warning("Name provided was empty");
                throw new ParserException("/name cannot be empty.");
            } else if (NAME_PARAMETER.equals(key)) {
                checkGoalsName(NAME_PARAMETER, value);
            }
            if (AMOUNT_PARAMETER.equals(key) && !(value == null || value.isBlank())) {
                checkGoalsAmount(value);
                changeCounter++;
            }
            if (NEW_NAME_PARAMETER.equals(key) && !(value == null || value.isBlank())) {
                checkName(NEW_NAME_PARAMETER, value);
                changeCounter++;
            }
            if (BY_PARAMETER.equals(key) && !(value == null || value.isBlank())) {
                by = checkDate(value);
                changeCounter++;
            }
            if (FROM_PARAMETER.equals(key) && !(value == null || value.isBlank())) {
                checkName(FROM_PARAMETER, value);
                changeCounter++;
            }
            if (IN_PARAMETER.equals(key) && !(value == null || value.isBlank())) {
                checkDay(IN_PARAMETER, value);
                by = convertDaysToDate(Integer.parseInt(value));
                changeCounter++;
            }
            if (MARK_DONE_PARAMETER.equals(key) && !(value == null || value.isBlank())) {
                checkInt(MARK_DONE_PARAMETER, value);
                markDone = true;
                markDoneCounter++;
            }
        }
        if (changeCounter != 0 && markDoneCounter != 0) {
            logger.warning("/mark cannot be accompanied by additional parameters");
            throw new ParserException("Cannot /mark and edit parameters of your goals!");
        }

        if (changeCounter == 0 && markDoneCounter == 0) {
            logger.warning("Did not provide correct parameters to change");
            throw new ParserException("Edit should have at least 1 differing parameter to change.");
        }
    }



    /**
     * Returns command to execute editing of goals.
     *
     * @return EditGoalsCommand to be executed.
     */
    @Override
    public Command getCommand() {
        EditGoalsCommand newEditGoalsCommand = new EditGoalsCommand(goalsParameters.get(NAME_PARAMETER),
                goalsParameters.get(AMOUNT_PARAMETER),
                by, goalsParameters.get(NEW_NAME_PARAMETER), goalsParameters.get(FROM_PARAMETER), markDone);
        return newEditGoalsCommand;
    }
}
