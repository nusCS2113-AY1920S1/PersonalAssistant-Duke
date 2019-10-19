package duke.logic.parsers;

import duke.logic.commands.FindCommand;

public class FindCommandParser implements ParserInterface<FindCommand> {

    @Override
    public FindCommand parse(String userInput) {
        String name = userInput.split(" /date ", 2)[0];
        if (userInput.split(" /date ").length > 1) {
            String date = userInput.split(" /date ")[1];
            return new FindCommand(name, date);
        } else {
            return new FindCommand(name);
        }
    }
}
