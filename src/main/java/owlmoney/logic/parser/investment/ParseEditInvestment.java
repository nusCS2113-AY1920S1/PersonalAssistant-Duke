package owlmoney.logic.parser.investment;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bank.EditInvestmentCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseEditInvestment extends ParseInvestment {
    /**
     * Creates an instance of ParseEditInvestment.
     *
     * @param data Raw user input date.
     * @throws ParserException If the first parameter is invalid.
     */
    public ParseEditInvestment(String data) throws ParserException {
        super(data);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are any invalid or missing inputs.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> investmentIterator = investmentParameters.keySet().iterator();
        int changeCounter = 0;
        while (investmentIterator.hasNext()) {
            String key = investmentIterator.next();
            String value = investmentParameters.get(key);
            if (NAME.equals(key) && (value.isEmpty() || value.isBlank())) {
                throw new ParserException("/name cannot be empty.");
            } else if (NAME.equals(key)) {
                checkName(NAME, value);
            }
            if (AMOUNT.equals(key) && !(value.isEmpty() || value.isBlank())) {
                checkAmount(value);
                changeCounter++;
            }
            if (NEW_NAME.equals(key) && !(value.isEmpty() || value.isBlank())) {
                checkName(NEW_NAME, value);
                changeCounter++;
            }
        }
        if (changeCounter == 0) {
            throw new ParserException("Edit should have at least 1 differing parameter to change.");
        }
    }

    /**
     * Returns the command to execute the editing of details for investment account.
     *
     * @return Returns EditInvestmentCommand to be executed.
     */
    public Command getCommand() {
        EditInvestmentCommand newEditInvestmentCommand = new EditInvestmentCommand(investmentParameters.get(NAME),
                investmentParameters.get(AMOUNT), investmentParameters.get(NEW_NAME));
        return newEditInvestmentCommand;
    }
}
