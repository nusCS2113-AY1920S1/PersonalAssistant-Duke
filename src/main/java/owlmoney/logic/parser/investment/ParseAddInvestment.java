package owlmoney.logic.parser.investment;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bank.AddInvestmentCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for adding a new investment account.
 */
public class ParseAddInvestment extends ParseInvestment {
    private static final String ADD_COMMAND = "/add";

    /**
     * Creates an instance of ParseAddInvestment.
     *
     * @param data Raw data of user input to be parsed.
     * @throws ParserException If there is a redundant parameter or first parameter is not a valid type.
     */
    public ParseAddInvestment(String data) throws ParserException {
        super(data);
        checkRedundantParameter(NEW_NAME_PARAMETER, ADD_COMMAND);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are any invalid or missing input.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> investmentIterator = investmentParameters.keySet().iterator();

        while (investmentIterator.hasNext()) {
            String key = investmentIterator.next();
            String value = investmentParameters.get(key);
            if (!NEW_NAME_PARAMETER.equals(key) && (value == null || value.isBlank())) {
                throw new ParserException(key + " cannot be empty when adding investment account");
            }
            if (NAME_PARAMETER.equals(key)) {
                checkName(NAME_PARAMETER, value);
            }
            if (AMOUNT_PARAMETER.equals(key)) {
                checkAmount(value);
            }
        }
    }

    /**
     * Returns the command to execute the adding of a new investment account.
     *
     * @return AddInvestmentCommand to be executed.
     */
    public Command getCommand() {
        AddInvestmentCommand newAddInvestmentCommand =
                new AddInvestmentCommand(investmentParameters.get(NAME_PARAMETER),
                Double.parseDouble(investmentParameters.get(AMOUNT_PARAMETER)));
        return newAddInvestmentCommand;
    }
}
