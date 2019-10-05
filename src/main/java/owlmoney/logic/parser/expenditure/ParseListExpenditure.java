package owlmoney.logic.parser.expenditure;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.expenditure.ListExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseListExpenditure extends ParseExpenditure{
    public ParseListExpenditure(String data) {
        super(data);
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
        }
    }

    //current name is just a place holder. This is to create the command and execute it
    //might need to restructure in future
    public Command getCommand() {
        ListExpenditureCommand newListExpenditureCommand =
                new ListExpenditureCommand(expendituresParameters.get(FROM));
        return newListExpenditureCommand;
    }
}
