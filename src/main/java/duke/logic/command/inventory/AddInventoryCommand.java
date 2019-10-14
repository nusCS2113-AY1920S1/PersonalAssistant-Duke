package duke.logic.command.inventory;

import duke.logic.command.Command;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.commons.Ingredient;
import duke.model.commons.Item;

public class AddInventoryCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_SUCCESS = "New ingredient added: %s";
    private final Item<Ingredient> toAdd;

    public AddInventoryCommand(Item<Ingredient> toAdd) {
        this.toAdd = toAdd;
    }

    public CommandResult execute(Model model) throws CommandException {
        model.addInventory(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getItem().getName()), CommandResult.DisplayedPage.INVENTORY);
    }
}
