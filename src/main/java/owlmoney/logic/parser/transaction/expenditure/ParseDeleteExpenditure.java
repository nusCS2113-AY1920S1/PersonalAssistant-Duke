package owlmoney.logic.parser.transaction.expenditure;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.DeleteExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseDeleteExpenditure extends ParseExpenditure {

    private static final String DELETE = "/delete";

    public ParseDeleteExpenditure(String data) throws ParserException {
        super(data);
        checkRedundantParameter(AMOUNT, DELETE);
        checkRedundantParameter(CATEGORY, DELETE);
        checkRedundantParameter(DESCRIPTION, DELETE);
        checkRedundantParameter(DATE, DELETE);
        checkRedundantParameter(NUM, DELETE);
        checkFirstParameter();
    }

    public void checkParameter() throws ParserException {
        // Getting an iterator
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if (TRANSNO.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding a new expenditure");
            }
            if (FROM.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding a new expenditure");
            }
            if (TRANSNO.equals(key)) {
                checkInt(TRANSNO, value);
            }
            if (FROM.equals(key)) {
                checkName(value);
            }
        }
    }

    public Command getCommand() {
        DeleteExpenditureCommand newDeleteExpenditureCommand =
                new DeleteExpenditureCommand(Integer.parseInt(expendituresParameters.get(TRANSNO)),
                        expendituresParameters.get(FROM));
        return newDeleteExpenditureCommand;
    }
}
