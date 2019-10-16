package duke.parsers;

import duke.commands.AddItemCommand;
import duke.tasks.Item;

public class AddItemCommandParser implements ParserInterface<AddItemCommand> {

    public AddItemCommand parse(String UserInput) {
        String name = UserInput.split("/", 2)[0].trim();
        String info = "/" + UserInput.split("/", 2)[1];
        return new AddItemCommand(new Item(name, info));
    }
}
