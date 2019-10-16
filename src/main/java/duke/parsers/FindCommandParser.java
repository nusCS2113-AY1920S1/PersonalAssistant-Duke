package duke.parsers;

import duke.commands.FindCommand;

public class FindCommandParser implements ParserInterface<FindCommand> {

    @Override
    public FindCommand parse(String UserInput) {
        String name = UserInput.split(" /date ", 2)[0];
        if (UserInput.split(" /date ").length > 1) {
            String date = UserInput.split(" /date ")[1];
            return new FindCommand(name, date);
        } else {
            return new FindCommand(name);
        }
    }
}
