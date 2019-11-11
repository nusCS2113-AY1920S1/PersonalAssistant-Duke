//@@author matthewng1996

package wallet.logic.parser;

import wallet.logic.command.CurrencyCommand;

public class CurrencyParser implements Parser<CurrencyCommand> {

    @Override
    public CurrencyCommand parse(String input) {
        if (!"".equals(input)) {
            return new CurrencyCommand(input.toLowerCase());
        }
        return null;
    }
}
