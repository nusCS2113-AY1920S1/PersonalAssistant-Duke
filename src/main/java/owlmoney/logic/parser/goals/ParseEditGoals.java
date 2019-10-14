package owlmoney.logic.parser.goals;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.goals.EditGoalsCommand;
import owlmoney.logic.parser.exception.ParserException;

import javax.swing.text.html.parser.Parser;
import java.util.Iterator;

public class ParseEditGoals extends ParseGoals{

    public ParseEditGoals(String data) throws ParserException {
        super(data);
        checkFirstParameter();
    }

    @Override
    public void checkParameter() throws ParserException {
        Iterator<String> goalsIterator = goalsParameters.keySet().iterator();
        int changeCounter = 0;
        while (goalsIterator.hasNext()) {
            String key = goalsIterator.next();
            String value = goalsParameters.get(key);
            if (NAME.equals(key) && (value.isEmpty() || value.isBlank())) {
                throw new ParserException(("/name cannot be empty"));
            } else if (NAME.equals(key)) {
                checkName(NAME, value);
            }
            if (AMOUNT.equals(key) && !(value.isEmpty() || value.isBlank())) {
                checkAmount((value));
                changeCounter++;
            }
            if (NEW_NAME.equals(key) && !(value.isEmpty() || value.isBlank())) {
                checkName(NEW_NAME, value);
                changeCounter++;
            }
            if (BY.equals(key) && !(value.isEmpty() || value.isBlank())) {
                checkDate(value);
                changeCounter++;
            }
        }
        if (changeCounter == 0) {
            throw new ParserException("Edit should have at least 1 differing parameter to change.");
        }
    }

    @Override
    public Command getCommand() {
        EditGoalsCommand newEditGoalsCommand = new EditGoalsCommand(goalsParameters.get(NAME),
                Double.parseDouble(goalsParameters.get(AMOUNT)),
                goalsParameters.get(BY), goalsParameters.get(NEW_NAME));
        return newEditGoalsCommand;
    }
}
