package owlmoney.logic.parser.transaction.deposit;

import java.util.Date;
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
     * @param data Raw user input date.
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
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();
        int changeCounter = 0;
        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if ((TRANSNO.equals(key) || FROM.equals(key)) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when editing a deposit");
            }
            if ((DESCRIPTION.equals(key) || AMOUNT.equals(key) || DATE.equals(key))
                    && (!value.isEmpty() || !value.isBlank())) {
                if (AMOUNT.equals(key)) {
                    checkAmount(value);
                } else if (DESCRIPTION.equals(key)) {
                    checkDescription(value);
                } else {
                    Date temp = checkDate(value);
                }
                changeCounter++;
            }
            if (TRANSNO.equals(key)) {
                checkInt(TRANSNO, value);
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
        EditDepositCommand newEditDepositCommand = new EditDepositCommand(expendituresParameters.get(FROM),
                expendituresParameters.get(AMOUNT), expendituresParameters.get(DATE),
                expendituresParameters.get(DESCRIPTION), Integer.parseInt(expendituresParameters.get(TRANSNO)));
        return newEditDepositCommand;
    }
}
