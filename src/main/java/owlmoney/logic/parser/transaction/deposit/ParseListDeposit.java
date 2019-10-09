package owlmoney.logic.parser.transaction.deposit;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.ListDepositCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseListDeposit extends ParseDeposit {
    private static final String LIST = "/list";

    public ParseListDeposit(String data) throws ParserException {
        super(data);
        checkRedundantParameter(TO, LIST);
        checkRedundantParameter(AMOUNT, LIST);
        checkRedundantParameter(DATE, LIST);
        checkRedundantParameter(DESCRIPTION, LIST);
        checkRedundantParameter(TRANSNO, LIST);
    }

    public void checkParameter() throws ParserException {
        // Getting an iterator
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if (FROM.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when listing deposits from a bank");
            }
            if(NUM.equals(key) && (value.isBlank() || value.isEmpty())) {
                expendituresParameters.put(key, "30");
            }
            if (NUM.equals(key)) {
                checkInt(NUM, expendituresParameters.get(NUM));
                if(Integer.parseInt(expendituresParameters.get(NUM)) <= 0) {
                    throw new ParserException("/num must be at least 1");
                }
            }
        }
    }

    public Command getCommand() {
        ListDepositCommand newListDepositCommand = new ListDepositCommand(expendituresParameters.get(FROM)
                , Integer.parseInt(expendituresParameters.get(NUM)));
        return newListDepositCommand;
    }
}
