//@@author matthewng1996

package wallet.logic.command;

import wallet.model.Wallet;
import wallet.thread.CurrencyThread;

public class CurrencyCommand extends Command {
    public static final String COMMAND_WORD = "currency";

    public static final String MESSAGE_NO_CURRENCY = "There is no such currency found for the conversion to ";
    public static final String MESSAGE_SUCCESSFUL_CONVERSION = "Your currency is converted to the country of ";
    public static final String MESSAGE_ADDING_CURRENCY =
            "You can add or modify your own currency conversion in /currency.txt";
    public static final String MESSAGE_USAGE = "Error in format for command."
            + "\nExample: " + COMMAND_WORD + " singapore"
            + "\nExample: " + COMMAND_WORD + " south korea";

    public String type;

    public CurrencyCommand(String type) {
        this.type = type;
    }

    @Override
    public boolean execute(Wallet wallet) {
        if (type != null) {
            CurrencyThread currencyThread = new CurrencyThread(wallet, type);
        } else {
            System.out.println(MESSAGE_ADDING_CURRENCY);
            System.out.println(MESSAGE_USAGE);
        }
        return false;
    }
}