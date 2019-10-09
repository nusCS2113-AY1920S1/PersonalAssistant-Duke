package owlmoney.logic.parser.transaction.deposit;

import java.util.Date;
import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.EditDepositCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseEditDeposit extends ParseDeposit {
    private static final String EDIT = "/edit";

    public ParseEditDeposit(String data) throws ParserException {
        super(data);
        checkRedundantParameter(TO, EDIT);
        checkRedundantParameter(NUM, EDIT);
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
                throw new ParserException(key + " cannot be empty when editing a deposit");
            }
            if ((DESCRIPTION.equals(key) || AMOUNT.equals(key) || DATE.equals(key))
                    && (!value.isEmpty() || !value.isBlank())) {
                if (AMOUNT.equals(key)) {
                    checkAmount(value);
                }
                if (DESCRIPTION.equals(key)) {
                    checkDescription(value);
                }
                if (DATE.equals(key)) {
                    Date temp = checkDate(value);
                }
                changeCounter++;
            }
            if(TRANSNO.equals(key)) {
                checkInt(TRANSNO, value);
            }
        }
        if (changeCounter == 0) {
            throw new ParserException("Edit should have at least 1 differing parameter to change.");
        }
    }

    public Command getCommand() {
        EditDepositCommand newEditDepositCommand = new EditDepositCommand(expendituresParameters.get(FROM)
                , expendituresParameters.get(AMOUNT)
                , expendituresParameters.get(DATE)
                , expendituresParameters.get(DESCRIPTION)
                , Integer.parseInt(expendituresParameters.get(TRANSNO)));
        return newEditDepositCommand;
    }
}
