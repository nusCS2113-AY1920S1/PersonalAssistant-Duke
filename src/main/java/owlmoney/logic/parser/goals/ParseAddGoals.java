package owlmoney.logic.parser.goals;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.goals.AddGoalsCommand;
import owlmoney.logic.parser.exception.ParserException;

import java.util.Date;
import java.util.Iterator;

public class ParseAddGoals extends ParseGoals {

    private static final String ADD = "/add";
    private Date date;

    public ParseAddGoals(String data) throws ParserException {
        super(data);
        checkRedundantParameter(NEW_NAME, ADD);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter
     *
     * @throws ParserException If there are nay invalid or missing input
     */
    @Override
    public void checkParameter() throws ParserException {
        Iterator<String> goalsIterator = goalsParameters.keySet().iterator();

        while (goalsIterator.hasNext()) {
            String key = goalsIterator.next();
            String value = goalsParameters.get(key);
            if (NAME.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding new goals");
            }
            if (AMOUNT.equals(key)) {
                checkAmount(value, AMOUNT);
            }
            if(NAME.equals(key)) {
                checkName(NAME, value);
            }
            if(BY.equals(key)) {
                date = checkDate(value);
            }
        }
    }

    /**
     * Returns command to execute the adding of a new goal
     *
     * @return AddGoalsCommand to be executed.
     */
    @Override
    public Command getCommand() {
        AddGoalsCommand newAddGoalsCommand = new AddGoalsCommand(goalsParameters.get(NAME),
                Double.parseDouble(goalsParameters.get(AMOUNT)), date);
        return newAddGoalsCommand;
    }
}
