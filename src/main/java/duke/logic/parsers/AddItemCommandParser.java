package duke.logic.parsers;

import duke.logic.commands.AddItemCommand;
import duke.model.Item;

public class AddItemCommandParser implements ParserInterface<AddItemCommand> {

    public AddItemCommand parse(String userInput) {
        String name = userInput.split("/", 2)[0].trim();
        String info = "/" + userInput.split("/", 2)[1];
        return new AddItemCommand(new Item(name, info));
    }
}
