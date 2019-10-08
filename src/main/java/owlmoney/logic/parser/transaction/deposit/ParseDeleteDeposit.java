package owlmoney.logic.parser.transaction.deposit;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.DeleteDepositCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseDeleteDeposit extends ParseDeposit {
    private static final String DELETE = "/delete";

    public ParseDeleteDeposit(String data) throws ParserException {
        super(data);
        checkRedundantParameter(TO, DELETE);
        checkRedundantParameter(NUM, DELETE);
        checkRedundantParameter(AMOUNT, DELETE);
        checkRedundantParameter(DESCRIPTION, DELETE);
        checkRedundantParameter(DATE, DELETE);
        checkFirstParameter();
    }

    public void checkParameter() throws ParserException {
        // Getting an iterator
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if ((TRANSNO.equals(key) || FROM.equals(key)) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding a new deposit");
            }
            if (TRANSNO.equals(key)) {
                checkIfInt(TRANSNO, value);
            }
        }
    }

    public Command getCommand() {
        DeleteDepositCommand newDeleteDepositCommand = new DeleteDepositCommand((expendituresParameters.get(FROM)),
                Integer.parseInt(expendituresParameters.get(TRANSNO)));
        return newDeleteDepositCommand;
    }
}
