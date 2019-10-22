package owlmoney.logic.parser.transaction.expenditure;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.AddRecurringExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for adding a recurring expenditure.
 */
public class ParseAddRecurringExpenditure extends ParseRecurringExpenditure {

    static final String ADD = "/add";

    /**
     * Constructor which creates an instance of ParseAddRecurringExpenditure.
     *
     * @param data Raw user input data.
     * @param type Represents type of recurring expenditure to be added.
     * @throws ParserException If there are redundant parameters or first parameter is invalid.
     */
    public ParseAddRecurringExpenditure(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(TRANSNO, ADD);
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
            if (CATEGORY.equals(key) && "deposit".equals(value)) {
                throw new ParserException(key + " cannot be deposit when adding a new recurring expenditure");
            } else if (CATEGORY.equals(key) && (value.isBlank() || value.isEmpty())) {
                expendituresParameters.put(CATEGORY, "Miscellaneous");
            } else if (CATEGORY.equals(key)) {
                checkDescription(value, key);
            }
            if (AMOUNT.equals(key) && value.isBlank()) {
                throw new ParserException(key + " cannot be deposit when adding a new recurring expenditure");
            } else if (AMOUNT.equals(key)) {
                checkAmount(value);
            }
            if (DESCRIPTION.equals(key) && value.isBlank()) {
                throw new ParserException(key + " cannot be deposit when adding a new recurring expenditure");
            } else if (DESCRIPTION.equals(key)) {
                checkDescription(value, key);
            }
            if (FROM.equals(key) && value.isBlank()) {
                throw new ParserException(key + " cannot be deposit when adding a new recurring expenditure");
            } else if (FROM.equals(key)) {
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
        calendar.clear();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.MONTH, 1);
        AddRecurringExpenditureCommand newAddRecurringExpenditureCommand = new AddRecurringExpenditureCommand(
                expendituresParameters.get(FROM), Double.parseDouble(expendituresParameters.get(AMOUNT)),
                calendar.getTime(), (expendituresParameters.get(DESCRIPTION)),
                (expendituresParameters.get(CATEGORY)), this.type);
        return newAddRecurringExpenditureCommand;
    }
}
