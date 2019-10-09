package owlmoney.logic.parser.saving;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bank.EditSavingsCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseEditSaving extends ParseSaving {

    public ParseEditSaving(String data) throws ParserException {
        super(data);
        checkFirstParameter();
    }

    public void checkParameter() throws ParserException {
        // Getting an iterator
        Iterator<String> savingsIterator = savingsParameters.keySet().iterator();
        int changeCounter = 0;
        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = savingsParameters.get(key);
            if (NAME.equals(key) && (value.isEmpty() || value.isBlank())) {
                throw new ParserException("/name cannot be empty.");
            } else if (!value.isEmpty() && !value.isBlank()){
                changeCounter++;
                if (INCOME.equals(key) || AMOUNT.equals(key)) {
                    checkIncome(value);
                } else if (NAME.equals(key) || NEW_NAME.equals(key)) {
                    checkName(key, value);
                }
            }
        }
        if (changeCounter == 0) {
            throw new ParserException("Edit should have at least 1 differing parameter to change.");
        }
    }

    public Command getCommand() {
        EditSavingsCommand newEditSavingsCommand = new EditSavingsCommand(savingsParameters.get(NAME),
                savingsParameters.get(INCOME), savingsParameters.get(AMOUNT), savingsParameters.get(NEW_NAME));
        return newEditSavingsCommand;
    }

}
