package owlmoney.logic.parser.transaction.expenditure;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.DeleteRecurringExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * FILL IN JAVADOCS HERE #################################################################################
 */
public class ParseDeleteRecurringExpenditure extends ParseRecurringExpenditure {
    private static final String DELETE = "/delete";

    /**
     * Constructor which creates an instance of ParseDeleteRecurringExpenditure.
     *
     * @param data Raw user input data.
     * @throws ParserException If there are redundant parameters or if first parameter is invalid.
     */
    public ParseDeleteRecurringExpenditure(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(AMOUNT, DELETE);
        checkRedundantParameter(CATEGORY, DELETE);
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

    /**
     * Returns the command to execute the deletion of a recurring expenditure.
     *
     * @return Returns DeletionRecurringExpenditureCommand to be executed.
     */
    public Command getCommand() {
        DeleteRecurringExpenditureCommand newDeleteERecurringxpenditureCommand =
                new DeleteRecurringExpenditureCommand(Integer.parseInt(expendituresParameters.get(TRANSNO)),
                        expendituresParameters.get(FROM), this.type);
        return newDeleteERecurringxpenditureCommand;
    }
}
