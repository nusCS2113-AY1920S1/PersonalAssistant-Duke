package wallet.logic.parser;

import wallet.logic.command.DeleteCommand;

import java.text.ParseException;

public class DeleteCommandParser implements Parser<DeleteCommand> {

    @Override
    public DeleteCommand parse(String input) throws ParseException {
        String[] arguments = input.split(" ", 2);

        switch (arguments[0]) {
        case "task":
            int index = Integer.parseInt(arguments[1]) - 1;
            return new DeleteCommand(arguments[0], index);

        default:
            break;
        }
        return null;
    }
}
