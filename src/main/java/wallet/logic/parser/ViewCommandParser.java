package wallet.logic.parser;

import wallet.logic.command.ViewCommand;

public class ViewCommandParser implements Parser<ViewCommand> {

    @Override
    public ViewCommand parse(String input) {
        if (input != "") {
            return new ViewCommand(input);
        }
        return null;
    }
}
