package wallet.logic.parser;

import wallet.logic.command.ViewCommand;

public class ViewCommandParser implements Parser<ViewCommand> {

    @Override
    public ViewCommand parse(String input) {
        if (!"".equals(input)) {
            String[] arguments = input.split(" ");
            return new ViewCommand(arguments);
        }
        return null;
    }
}
