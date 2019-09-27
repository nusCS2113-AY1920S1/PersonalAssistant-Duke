package owlmoney.logic.parser.saving;

import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;

import owlmoney.logic.parser.exception.ParserException;

public class ParseAddSaving extends ParseSaving {

    public ParseAddSaving(String data) {
        super(data);
    }

    public void checkParameter() throws ParserException {
        // Getting an iterator
        Iterator<String> savingsIterator = savingsParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = savingsParameters.get(key);
            if (NEW_NAME.equals(key)) {
                throw new ParserException("/newname parameter cannot be used for adding.");
            }
            if (value.isBlank() || value.isEmpty()) {
                throw new ParserException(key + " cannot be empty when adding savings account");
            }
            if (INCOME.equals(key) || AMOUNT.equals(key)) {
                checkIfDouble(value);
            }
        }
    }


}
