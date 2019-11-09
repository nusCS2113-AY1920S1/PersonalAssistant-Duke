package owlmoney.logic.parser.transaction.deposit;

import java.util.Date;
import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.AddDepositCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for adding a deposit.
 */
public class ParseAddDeposit extends ParseDeposit {
    private static final String ADD_COMMAND = "/add";
    private Date date;

    /**
     * Creates an instance of ParseAddDeposit.
     *
     * @param data Raw user input data.
     * @throws ParserException If there are redundant parameters or first parameter is invalid.
     */
    public ParseAddDeposit(String data) throws ParserException {
        super(data);
        checkRedundantParameter(TRANSACTION_NUMBER_PARAMETER, ADD_COMMAND);
        checkRedundantParameter(FROM_PARAMETER, ADD_COMMAND);
        checkRedundantParameter(NUM_PARAMETER, ADD_COMMAND);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are missing or invalid parameters.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> savingsIterator = depositParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = depositParameters.get(key);
            if (!(TRANSACTION_NUMBER_PARAMETER.equals(key) || NUM_PARAMETER.equals(key) || FROM_PARAMETER.equals(key))
                    && (value == null || value.isBlank())) {
                throw new ParserException(key + " cannot be empty when adding a new deposit");
            }
            if (AMOUNT_PARAMETER.equals(key)) {
                checkAmount(value);
            }
            if (DATE_PARAMETER.equals(key)) {
                date = checkDate(value);
            }
            if (TO_PARAMETER.equals(key)) {
                checkName(value, TO_PARAMETER);
            }
            if (DESCRIPTION_PARAMETER.equals(key)) {
                checkDescription(value);
            }
        }
    }

    /**
     * Returns the command to add a new deposit.
     *
     * @return Returns AddDepositCommand to be executed.
     */
    public Command getCommand() {
        AddDepositCommand newAddDepositCommand = new AddDepositCommand(depositParameters.get(TO_PARAMETER),
                Double.parseDouble(depositParameters.get(AMOUNT_PARAMETER)), date,
                (depositParameters.get(DESCRIPTION_PARAMETER)));
        return newAddDepositCommand;
    }
}
