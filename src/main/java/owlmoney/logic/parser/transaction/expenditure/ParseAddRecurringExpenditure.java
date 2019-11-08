package owlmoney.logic.parser.transaction.expenditure;

import java.util.Calendar;
import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.AddRecurringExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for adding a recurring expenditure.
 */
public class ParseAddRecurringExpenditure extends ParseRecurringExpenditure {

    private static final String ADD_COMMAND = "/add";
    private static final String RESERVED_CATEGORY = "deposit";

    /**
     * Creates an instance of ParseAddRecurringExpenditure.
     *
     * @param data Raw user input data.
     * @param type Represents type of recurring expenditure to be added.
     * @throws ParserException If there are redundant parameters or first parameter is invalid.
     */
    public ParseAddRecurringExpenditure(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(TRANSACTION_NUMBER_PARAMETER, ADD_COMMAND);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are missing or invalid parameters.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();
        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if (CATEGORY_PARAMETER.equals(key) && RESERVED_CATEGORY.equals(value)) {
                throw new ParserException(key + " cannot be deposit when adding a new recurring expenditure");
            } else if (CATEGORY_PARAMETER.equals(key) && (value.isBlank() || value.isEmpty())) {
                expendituresParameters.put(CATEGORY_PARAMETER, "Miscellaneous");
            } else if (CATEGORY_PARAMETER.equals(key)) {
                checkCategory(value);
            }
            if (AMOUNT_PARAMETER.equals(key) && value.isBlank()) {
                throw new ParserException(key + " cannot be deposit when adding a new recurring expenditure");
            } else if (AMOUNT_PARAMETER.equals(key)) {
                checkAmount(value);
            }
            if (DESCRIPTION_PARAMETER.equals(key) && value.isBlank()) {
                throw new ParserException(key + " cannot be deposit when adding a new recurring expenditure");
            } else if (DESCRIPTION_PARAMETER.equals(key)) {
                checkDescription(value, key);
            }
            if (FROM_PARAMETER.equals(key) && value.isBlank()) {
                throw new ParserException(key + " cannot be deposit when adding a new recurring expenditure");
            } else if (FROM_PARAMETER.equals(key)) {
                checkName(value);
            }
        }
    }

    /**
     * Returns the command to add a new recurring expenditure.
     *
     * @return Returns AddRecurringExpenditureCommand to be executed.
     */
    public Command getCommand() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.MONTH, 1);
        AddRecurringExpenditureCommand newAddRecurringExpenditureCommand = new AddRecurringExpenditureCommand(
                expendituresParameters.get(FROM_PARAMETER),
                Double.parseDouble(expendituresParameters.get(AMOUNT_PARAMETER)),
                calendar.getTime(),
                (expendituresParameters.get(DESCRIPTION_PARAMETER)),
                (expendituresParameters.get(CATEGORY_PARAMETER)), this.type);
        return newAddRecurringExpenditureCommand;
    }
}
