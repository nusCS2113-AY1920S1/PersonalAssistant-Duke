package duke.logic.command.product;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.product.Product;

public class AddProductCommand extends ProductCommand {

    public static final String COMMAND_WORD = "add";
    public static String MESSAGE_SUCCESS = "New product: %s added";
    public static String MESSAGE_NEW_INGREDIENTS_CREATED = MESSAGE_SUCCESS + ", new ingredients created";
    private final Product toAdd;


    public AddProductCommand(Product toAdd) {
        this.toAdd = toAdd;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.addProduct(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName()),
                CommandResult.DisplayedPage.PRODUCT);
    }
}
