package owlmoney.logic.parser.investment;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bank.DeleteInvestmentCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseDeleteInvestment extends ParseInvestment {
    private static final String DELETE = "/delete";

    /**
     * Constructor which creates an instance of ParseDeleteSaving.
     *
     * @param data Raw user input data.
     * @throws ParserException If there are redundant parameters or if the first parameter is not valid.
     */
    public ParseDeleteInvestment(String data) throws ParserException {
        super(data);
        checkRedundantParameter(AMOUNT, DELETE);
        checkRedundantParameter(NEW_NAME, DELETE);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are any invalid user input.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> investmentIterator = investmentParameters.keySet().iterator();
        while (investmentIterator.hasNext()) {
            String key = investmentIterator.next();
            String value = investmentParameters.get(key);
            if (NAME.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when deleting savings account");
            } else if (NAME.equals(key)) {
                checkName(NAME,value);
            }
        }
    }

    /**
     * Returns the command to execute the deletion of investment account.
     *
     * @return DeleteSavingsCommand to be executed.
     */
    public Command getCommand() {
        DeleteInvestmentCommand newDeleteInvestmentCommand = new DeleteInvestmentCommand(investmentParameters.get(NAME));
        return newDeleteInvestmentCommand;
    }
}
