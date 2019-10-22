package owlmoney.logic.parser.transaction.deposit;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.EditDepositCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for editing a deposit.
 */
public class ParseEditDeposit extends ParseDeposit {
    private static final String EDIT = "/edit";

    /**
     * Constructor which creates an instance of ParseEditDeposit.
     *
     * @param data Raw user input data.
     * @throws ParserException If there are redundant parameters or first parameter is invalid.
     */
    public ParseEditDeposit(String data) throws ParserException {
        super(data);
        checkRedundantParameter(TO, EDIT);
        checkRedundantParameter(NUM, EDIT);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If user input is invalid.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> savingsIterator = depositParameters.keySet().iterator();
        int changeCounter = 0;
        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = depositParameters.get(key);
            if (TRANSNO.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when editing a deposit");
            } else if (TRANSNO.equals(key)) {
                checkInt(TRANSNO, value);
            }
            if (FROM.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when editing a deposit");
            } else if (FROM.equals(key)) {
                checkName(value, FROM);
            }
            if (DESCRIPTION.equals(key) && !(value.isBlank() || value.isEmpty())) {
                checkDescription(value);
                changeCounter++;
            }
            if (AMOUNT.equals(key) && !(value.isBlank() || value.isEmpty())) {
                checkAmount(value);
                changeCounter++;
            }
            if (DATE.equals(key) && !(value.isBlank() || value.isEmpty())) {
                checkDate(value);
                changeCounter++;
            }
        }
        if (changeCounter == 0) {
            throw new ParserException("Edit should have at least 1 differing parameter to change.");
        }
    }

    /**
     * Returns the command to execute the editing of a deposit.
     *
     * @return EditDepositCommand to be executed.
     */
    public Command getCommand() {
        EditDepositCommand newEditDepositCommand = new EditDepositCommand(depositParameters.get(FROM),
                depositParameters.get(AMOUNT), depositParameters.get(DATE),
                depositParameters.get(DESCRIPTION), Integer.parseInt(depositParameters.get(TRANSNO)));
        return newEditDepositCommand;
    }
}
