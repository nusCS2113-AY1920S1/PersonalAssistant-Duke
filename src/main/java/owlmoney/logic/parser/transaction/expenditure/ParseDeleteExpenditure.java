package owlmoney.logic.parser.transaction.expenditure;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.DeleteExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for deleting an expenditure.
 */
public class ParseDeleteExpenditure extends ParseExpenditure {

    private static final String DELETE = "/delete";

    /**
     * Creates an instance of ParseDeleteExpenditure.
     *
     * @param data Raw user input data.
     * @throws ParserException If there are redundant parameters or if first parameter is invalid.
     */
    public ParseDeleteExpenditure(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(AMOUNT, DELETE);
        checkRedundantParameter(CATEGORY, DELETE);
        checkRedundantParameter(DESCRIPTION, DELETE);
        checkRedundantParameter(DATE, DELETE);
        checkRedundantParameter(NUM, DELETE);
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
     * Returns the command to execute the deletion of an expenditure.
     *
     * @return Returns DeletionExpenditureCommand to be executed.
     */
    public Command getCommand() {
        DeleteExpenditureCommand newDeleteExpenditureCommand =
                new DeleteExpenditureCommand(Integer.parseInt(expendituresParameters.get(TRANSNO)),
                        expendituresParameters.get(FROM), this.type);
        return newDeleteExpenditureCommand;
    }
}
