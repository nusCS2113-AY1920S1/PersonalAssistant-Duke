package owlmoney.logic.parser.transaction.deposit;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.DeleteDepositCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for deleting a deposit.
 */
public class ParseDeleteDeposit extends ParseDeposit {
    private static final String DELETE = "/delete";

    /**
     * Constructor which creates an instance of ParseDeleteDeposit.
     *
     * @param data Raw user input date.
     * @throws ParserException If there are redundant parameters or if first parameter is invalid.
     */
    public ParseDeleteDeposit(String data) throws ParserException {
        super(data);
        checkRedundantParameter(TO, DELETE);
        checkRedundantParameter(NUM, DELETE);
        checkRedundantParameter(AMOUNT, DELETE);
        checkRedundantParameter(DESCRIPTION, DELETE);
        checkRedundantParameter(DATE, DELETE);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If parameter is missing or invalid.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if ((TRANSNO.equals(key) || FROM.equals(key)) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding a new deposit");
            } else if (TRANSNO.equals(key)) {
                checkInt(TRANSNO, value);
            } else if (FROM.equals(key)) {
                checkName(value, FROM);
            }
        }
    }

    /**
     * Returns the command to execute the deletion of a deposit.
     *
     * @return Returns DeletionDepositCommand to be executed.
     */
    public Command getCommand() {
        DeleteDepositCommand newDeleteDepositCommand = new DeleteDepositCommand((expendituresParameters.get(FROM)),
                Integer.parseInt(expendituresParameters.get(TRANSNO)));
        return newDeleteDepositCommand;
    }
}
