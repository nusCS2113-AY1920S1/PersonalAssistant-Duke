package owlmoney.logic.parser.goals;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.goals.AddGoalsCommand;
import owlmoney.logic.parser.exception.ParserException;

import java.util.Iterator;

public class ParseAddGoals extends ParseGoals {
    public ParseAddGoals(String data) throws ParserException {
        super(data);
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
                throw new ParserException(key + "cannot be empty when adding new goals");
            }
            if (AMOUNT.equals(key)) {
                checkAmount(value);
            }
            if(NAME.equals(key)) {
                checkName(NAME, value);
            }
            if(BY.equals(key)) {
                checkDate(value);
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
                Double.parseDouble(goalsParameters.get(AMOUNT)), DATE);
        return newAddGoalsCommand;
    }
}
