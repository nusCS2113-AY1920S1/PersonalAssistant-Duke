package owlmoney.logic.parser.expenditure;

import java.util.Iterator;

import owlmoney.logic.command.expenditure.AddExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.model.profile.Profile;

public class ParseAddExpenditure extends ParseExpenditure {

    public ParseAddExpenditure(String data) {
        super(data);
    }

    public void checkParameter() throws ParserException {
        // Getting an iterator
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if ((value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding a new expenditure");
            }
            if (AMOUNT.equals(key)) {
                checkIfDouble(value);
            }
        }
    }

    //current name is just a place holder. This is to create the command and execute it
    //might need to restructure in future
    public void passToCommand(Profile profile) {
        AddExpenditureCommand newAddExpenditureCommand = new AddExpenditureCommand(expendituresParameters.get(ACCNAME),
                Double.parseDouble(expendituresParameters.get(AMOUNT)), (expendituresParameters.get(DATE)),
                (expendituresParameters.get(DESCRIPTION)), (expendituresParameters.get(CATEGORY)));
        newAddExpenditureCommand.execute(profile);
    }


}
