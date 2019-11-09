package owlmoney.logic.parser.saving;

import java.util.Iterator;

import owlmoney.logic.command.Command;

import owlmoney.logic.command.bank.AddSavingsCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for adding a saving.
 */
public class ParseAddSaving extends ParseSaving {

    private static final String ADD_COMMAND = "/add";

    /**
     * Creates an instance of ParseAddSaving.
     *
     * @param data Raw data of user input to be parsed.
     * @throws ParserException If there is a redundant parameter or first parameter is not a valid type.
     */
    public ParseAddSaving(String data) throws ParserException {
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
        Iterator<String> savingsIterator = savingsParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = savingsParameters.get(key);
            if (!NEW_NAME_PARAMETER.equals(key) && (value == null || value.isBlank())) {
                throw new ParserException(key + " cannot be empty when adding savings account");
            }
            if (NAME_PARAMETER.equals(key)) {
                checkName(NAME_PARAMETER, value);
            }
            if (INCOME_PARAMETER.equals(key)) {
                checkIncome(value);
            }
            if (AMOUNT_PARAMETER.equals(key)) {
                checkAmount(value);
            }
        }
    }

    /**
     * Returns the command to execute the adding of a new saving.
     *
     * @return AddSavingsCommand to be executed.
     */
    public Command getCommand() {
        AddSavingsCommand newAddSavingsCommand = new AddSavingsCommand(
                savingsParameters.get(NAME_PARAMETER),
                Double.parseDouble(savingsParameters.get(INCOME_PARAMETER)),
                Double.parseDouble(savingsParameters.get(AMOUNT_PARAMETER)));
        return newAddSavingsCommand;
    }

}
