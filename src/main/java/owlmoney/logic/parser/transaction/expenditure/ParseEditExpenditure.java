package owlmoney.logic.parser.transaction.expenditure;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.EditExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseEditExpenditure extends ParseExpenditure {
    public ParseEditExpenditure(String data) throws ParserException {
        super(data);
        checkFirstParameter();
    }

    public void checkParameter() throws ParserException {
        // Getting an iterator
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();
        int changeCounter = 0;
        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if ((TRANSNO.equals(key) || FROM.equals(key)) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when editing an expenditure");
            } else if (CATEGORY.equals(key) && "deposit".equals(value)) {
                throw new ParserException(key + " cannot be deposit when editing an expenditure");
            } else if (!(TRANSNO.equals(key) || FROM.equals(key)) && (!value.isEmpty() || !value.isBlank())) {
                if (AMOUNT.equals(key)) {
                    checkIfDouble(value);
                }
                changeCounter++;
            }
            if(TRANSNO.equals(key)) {
                checkIfInt(TRANSNO, value);
            }
        }
        if (changeCounter == 0) {
            throw new ParserException("Edit should have at least 1 differing parameter to change.");
        }
    }

    public Command getCommand() {
        EditExpenditureCommand newEditExpenditureCommand = new EditExpenditureCommand(expendituresParameters.get(FROM)
                , expendituresParameters.get(AMOUNT)
                , expendituresParameters.get(DATE)
                , expendituresParameters.get(DESCRIPTION)
                , expendituresParameters.get(CATEGORY)
                , Integer.parseInt(expendituresParameters.get(TRANSNO)));
        return newEditExpenditureCommand;
    }
}
