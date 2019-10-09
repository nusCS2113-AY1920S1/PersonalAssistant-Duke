package owlmoney.logic.parser.transaction.expenditure;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.ListExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseListExpenditure extends ParseExpenditure {
    private static final String LIST = "/list";

    public ParseListExpenditure(String data) throws ParserException {
        super(data);
        checkRedundantParameter(TRANSNO,LIST);
        checkRedundantParameter(AMOUNT,LIST);
        checkRedundantParameter(DATE,LIST);
        checkRedundantParameter(DESCRIPTION,LIST);
        checkRedundantParameter(CATEGORY,LIST);
    }

    public void checkParameter() throws ParserException {
        // Getting an iterator
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if (FROM.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding a new expenditure");
            }
            if (NUM.equals(key) && (value.isBlank() || value.isEmpty())) {
                expendituresParameters.put(NUM, "30");
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
        ListExpenditureCommand newListExpenditureCommand = new ListExpenditureCommand(expendituresParameters.get(FROM)
                , Integer.parseInt(expendituresParameters.get(NUM)));
        return newListExpenditureCommand;
    }
}
