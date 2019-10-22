package owlmoney.logic.parser.transaction.deposit;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.DeleteDepositCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for deleting a deposit.
 */
public class ParseDeleteDeposit extends ParseDeposit {
    private static final String DELETE = "/delete";

    /**
     * Creates an instance of ParseDeleteDeposit.
     *
     * @param data Raw user input data.
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
        Iterator<String> savingsIterator = depositParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = depositParameters.get(key);
            if ((TRANSNO.equals(key) || FROM.equals(key)) && (value.isEmpty() || value.isBlank())) {
                throw new ParserException(key + " cannot be empty when adding a new deposit");
            }
            if (TRANSNO.equals(key)) {
                checkInt(TRANSNO, value);
            }
            if (FROM.equals(key)) {
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
        DeleteDepositCommand newDeleteDepositCommand = new DeleteDepositCommand((depositParameters.get(FROM)),
                Integer.parseInt(depositParameters.get(TRANSNO)));
        return newDeleteDepositCommand;
    }
}
