package owlmoney.logic.parser.transfer;

import java.util.Iterator;
import java.util.Date;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transfer.TransferCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseTransferSaving extends ParseTransfer {

    private Date date;

    /**
     * Creates an instance of ParseEditSaving.
     *
     * @param data Raw user input date.
     * @throws ParserException If the first parameter is invalid.
     */
    public ParseTransferSaving(String data) throws ParserException {
        super(data);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are any invalid or missing inputs.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> savingsIterator = transferParameters.keySet().iterator();
        int changeCounter = 0;
        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = transferParameters.get(key);
            if (FROM.equals(key)) {
                checkName(FROM, value);
            }
            if (TO.equals(key)) {
                checkName(TO, value);
            }
            if (AMOUNT.equals(key)) {
                checkAmount(value);
            }
            if (DATE.equals(key)) {
                date = checkDate(value);
            }
        }
    }

    /**
     * Returns the command to execute the editing of a saving.
     *
     * @return Returns EditSavingsCommand to be executed.
     */
    public Command getCommand() {
        TransferCommand newTransferSavingsCommand = new TransferCommand(transferParameters.get(FROM),
                transferParameters.get(TO), Double.parseDouble(transferParameters.get(AMOUNT)), date);
        return newTransferSavingsCommand;
    }
}
