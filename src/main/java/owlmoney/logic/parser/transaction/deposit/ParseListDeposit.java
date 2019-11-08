package owlmoney.logic.parser.transaction.deposit;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.ListDepositCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for listing deposits.
 */
public class ParseListDeposit extends ParseDeposit {
    private static final String LIST_COMMAND = "/list";

    /**
     * Creates an instance of ParseListDeposit.
     *
     * @param data Raw user input data.
     * @throws ParserException If there are redundant parameters.
     */
    public ParseListDeposit(String data) throws ParserException {
        super(data);
        checkRedundantParameter(TO_PARAMETER, LIST_COMMAND);
        checkRedundantParameter(AMOUNT_PARAMETER, LIST_COMMAND);
        checkRedundantParameter(DATE_PARAMETER, LIST_COMMAND);
        checkRedundantParameter(DESCRIPTION_PARAMETER, LIST_COMMAND);
        checkRedundantParameter(TRANSACTION_NUMBER_PARAMETER, LIST_COMMAND);
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If the user input is invalid.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> savingsIterator = depositParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = depositParameters.get(key);
            if (FROM_PARAMETER.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when listing deposits from a bank");
            } else if (FROM_PARAMETER.equals(key)) {
                checkName(value, FROM_PARAMETER);
            }
            if (NUM_PARAMETER.equals(key) && (value.isBlank() || value.isEmpty())) {
                depositParameters.put(key, "30");
            } else if (NUM_PARAMETER.equals(key)) {
                checkInt(NUM_PARAMETER, value);
            }
        }
    }

    /**
     * Returns the command to execute the listing of deposits.
     *
     * @return ListDepositCommand to be executed.
     */
    public Command getCommand() {
        ListDepositCommand newListDepositCommand = new ListDepositCommand(depositParameters.get(FROM_PARAMETER),
                Integer.parseInt(depositParameters.get(NUM_PARAMETER)));
        return newListDepositCommand;
    }
}
