package owlmoney.logic.parser.goals;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.goals.DeleteGoalsCommand;
import owlmoney.logic.parser.exception.ParserException;

import javax.swing.text.html.parser.Parser;

import java.util.Iterator;

/**
 * Represents the parsing of inputs for deleting a goal.
 */
public class ParseDeleteGoals extends ParseGoals {

    private static final String DELETE = "/delete";

    /**
     * Creates an instance of ParseDeleteGoals Class.
     *
     * @param data raw data of the input.
     * @throws ParserException If redundant parameter is provided or first parameter is invalid.
     */
    public ParseDeleteGoals(String data) throws ParserException {
        super(data);
        checkRedundantParameter(AMOUNT, DELETE);
        checkRedundantParameter(NEW_NAME, DELETE);
        checkRedundantParameter(BY, DELETE);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are any invalid user input.
     */
    @Override
    public void checkParameter() throws ParserException {
        Iterator<String> goalsIterator = goalsParameters.keySet().iterator();
        while (goalsIterator.hasNext()) {
            String key = goalsIterator.next();
            String value = goalsParameters.get(key);
            if (NAME.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when deleting goals");
            } else if (NAME.equals(key)) {
                checkName(NAME, value);
            }
        }

    }

    /**
     * Returns the command to execute the deleting of a goal.
     *
     * @return DeleteGoalsCommand to be executed.
     */
    @Override
    public Command getCommand() {
        DeleteGoalsCommand newDeleteGoalsCommand = new DeleteGoalsCommand(goalsParameters.get(NAME));
        return newDeleteGoalsCommand;
    }
}
