package duke.parsers;

import duke.commands.AddItemCommand;
import duke.tasks.Item;

public class AddItemCommandParser implements ParserInterface<AddItemCommand> {

    public AddItemCommand parse(String userInput) {
        String name = userInput.split("/", 2)[0].trim();
        String info = "/" + userInput.split("/", 2)[1];
        return new AddItemCommand(new Item(name, info));
    }
}
