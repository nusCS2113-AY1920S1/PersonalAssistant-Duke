package owlmoney.logic.parser.goals;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.goals.DeleteGoalsCommand;
import owlmoney.logic.parser.exception.ParserException;

import javax.swing.text.html.parser.Parser;
import java.util.Iterator;

public class ParseDeleteGoals extends ParseGoals {

    private static final String DELETE = "/delete";

    public ParseDeleteGoals(String data) throws ParserException {
        super(data);
        checkRedundantParameter(AMOUNT, DELETE);
        checkRedundantParameter(NEW_NAME, DELETE);
        checkRedundantParameter(BY, DELETE);
        checkFirstParameter();
    }

    @Override
    public void checkParameter() throws ParserException {
        Iterator<String> goalsIterator = goalsParameters.keySet().iterator();
        while (goalsIterator.hasNext()) {
            String key = goalsIterator.next();
            String value = goalsParameters.get(key);
            if(NAME.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when deleting goals");
            } else if (NAME.equals(key)) {
                checkName(NAME, value);
            }
        }

    }

    @Override
    public Command getCommand() {
        DeleteGoalsCommand newDeleteGoalsCommand = new DeleteGoalsCommand(goalsParameters.get(NAME));
        return newDeleteGoalsCommand;
    }
}
