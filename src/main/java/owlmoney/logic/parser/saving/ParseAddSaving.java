package owlmoney.logic.parser.saving;

import java.util.Iterator;

import owlmoney.logic.command.Command;

import owlmoney.logic.command.bank.AddSavingsCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseAddSaving extends ParseSaving {

    private static final String ADD = "/add";

    public ParseAddSaving(String data) throws ParserException {
        super(data);
        checkRedundantParameter(NEW_NAME, ADD);
        checkFirstParameter();
    }

    public void checkParameter() throws ParserException {
        // Getting an iterator
        Iterator<String> savingsIterator = savingsParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = savingsParameters.get(key);
            if (!NEW_NAME.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding savings account");
            }
            if(NAME.equals(key)) {
                checkName(key, value);
            }
            if (INCOME.equals(key)) {
                checkIncome(value);
            }
            if (AMOUNT.equals(key)) {
                checkAmount(value);
            }
        }
    }

    public Command getCommand() {
        AddSavingsCommand newAddSavingsCommand = new AddSavingsCommand(savingsParameters.get(NAME),
                Double.parseDouble(savingsParameters.get(INCOME)),
                Double.parseDouble(savingsParameters.get(AMOUNT)));
        return newAddSavingsCommand;
    }

}
