package owlmoney.logic.parser.transaction.deposit;

import java.util.Date;
import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.AddDepositCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for adding a deposit.
 */
public class ParseAddDeposit extends ParseDeposit {
    private static final String ADD = "/add";
    private Date date;

    /**
     * Constructor which creates an instance of ParseAddDeposit.
     *
     * @param data Raw user input data.
     * @throws ParserException If there are redundant parameters or first parameter is invalid.
     */
    public ParseAddDeposit(String data) throws ParserException {
        super(data);
        checkRedundantParameter(TRANSNO, ADD);
        checkRedundantParameter(FROM, ADD);
        checkRedundantParameter(NUM, ADD);
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
            if (!(TRANSNO.equals(key) || NUM.equals(key) || FROM.equals(key)) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding a new deposit");
            }
            if (AMOUNT.equals(key)) {
                checkAmount(value);
            }
            if (DATE.equals(key)) {
                date = checkDate(value);
            }
            if (TO.equals(key)) {
                checkName(value, TO);
            }
            if (DESCRIPTION.equals(key)) {
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
        AddDepositCommand newAddDepositCommand = new AddDepositCommand(depositParameters.get(TO),
                Double.parseDouble(depositParameters.get(AMOUNT)), date,
                (depositParameters.get(DESCRIPTION)));
        return newAddDepositCommand;
    }
}
