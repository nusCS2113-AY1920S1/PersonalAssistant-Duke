package owlmoney.logic.parser.transaction.expenditure;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.ListRecurringExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for listing recurring expenditures.
 */
public class ParseListRecurringExpenditure extends ParseRecurringExpenditure {
    private static final String LIST = "/list";

    /**
     * Creates an instance of ParseListRecurringExpenditure.
     *
     * @param data Raw user input data.
     * @throws ParserException If there are redundant parameters.
     */
    public ParseListRecurringExpenditure(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(TRANSACTION_NUMBER_PARAMETER, LIST);
        checkRedundantParameter(AMOUNT_PARAMETER, LIST);
        checkRedundantParameter(DESCRIPTION_PARAMETER, LIST);
        checkRedundantParameter(CATEGORY_PARAMETER, LIST);
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If the user input is invalid.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if (FROM_PARAMETER.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when listing recurring expenditures");
            } else if (FROM_PARAMETER.equals(key)) {
                checkName(value);
            }
        }
    }

    /**
     * Returns the command to execute the listing of recurring expenditures.
     *
     * @return ListRecurringExpendituresCommand to be executed.
     */
    public Command getCommand() {
        ListRecurringExpenditureCommand newListRecurringExpenditureCommand =
                new ListRecurringExpenditureCommand(expendituresParameters.get(FROM_PARAMETER), this.type);
        return newListRecurringExpenditureCommand;
    }
}
