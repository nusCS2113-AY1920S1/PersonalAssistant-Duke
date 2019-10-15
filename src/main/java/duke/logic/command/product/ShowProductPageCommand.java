package duke.logic.command.product;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

public class ShowProductPageCommand extends ProductCommand {

    public static final String MESSAGE_SUCCESS = "Products are listed";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.getFilteredProductList();
        return new CommandResult(MESSAGE_SUCCESS, CommandResult.DisplayedPage.PRODUCT);
    }
}
