package owlmoney.logic.parser.saving;

import java.util.Iterator;

import owlmoney.logic.parser.exception.ParserException;

public class ParseEditSaving extends ParseSaving {

    public ParseEditSaving(String data) {
        super(data);
    }

    public void checkParameter() throws ParserException {
        // Getting an iterator
        Iterator<String> savingsIterator = savingsParameters.keySet().iterator();
        int changeCounter = 0;
        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = savingsParameters.get(key);
            if("/name".equals(key)) {
                if (value.isEmpty() || value.isBlank()) {
                    throw new ParserException("/name cannot be empty.");
                }
            } else {
                if (!value.isEmpty() && !value.isBlank()) {
                    changeCounter++;
                    if (INCOME.equals(key) || AMOUNT.equals(key)) {
                        checkIfDouble(key, value);
                    }
                }
            }
        }
        if(changeCounter == 0) {
            throw new ParserException("Edit should have at least 1 differing parameter to change.");
        }
    }

}
