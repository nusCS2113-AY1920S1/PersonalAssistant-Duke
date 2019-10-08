package owlmoney.logic.parser.transaction.deposit;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.AddDepositCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseAddDeposit extends ParseDeposit{
    private static final String ADD = "/add";

    public ParseAddDeposit(String data) throws ParserException {
        super(data);
        checkRedundantParameter(TRANSNO, ADD);
        checkRedundantParameter(FROM, ADD);
        checkRedundantParameter(NUM, ADD);
        checkFirstParameter();
    }

    public void checkParameter() throws ParserException {
        // Getting an iterator
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if (!(TRANSNO.equals(key) || NUM.equals(key) || FROM.equals(key)) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding a new deposit");
            }
            if (AMOUNT.equals(key)) {
                checkIfDouble(value);
            }
        }
    }

    public Command getCommand() {
        AddDepositCommand newAddDepositCommand = new AddDepositCommand(expendituresParameters.get(TO),
                Double.parseDouble(expendituresParameters.get(AMOUNT)), (expendituresParameters.get(DATE)),
                (expendituresParameters.get(DESCRIPTION)));
        return newAddDepositCommand;
    }
}
