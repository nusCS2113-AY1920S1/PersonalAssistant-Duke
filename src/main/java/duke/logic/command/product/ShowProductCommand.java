package duke.logic.command.product;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.product.Product;

import java.util.List;

import static duke.commons.util.CollectionUtil.requireAllNonNull;

public class ShowProductCommand extends ProductCommand {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_SHOW_PRODUCT_SUCCESS = "Showing product %s";

    public final Index index;
    public final ProductDescriptor productDescriptor;

    public ShowProductCommand(Index index, ProductDescriptor productDescriptor) {
        requireAllNonNull(index, productDescriptor);

        this.index = index;
        this.productDescriptor = new ProductDescriptor(productDescriptor);
    }
    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public ShowProductCommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<Product> lastShownList = model.getFilteredProductList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Message.MESSAGE_INVALID_INDEX);
        }

        Product toShow = lastShownList.get(index.getZeroBased());
        return new ShowProductCommandResult(String.format(MESSAGE_SHOW_PRODUCT_SUCCESS, toShow.getProductName()),
                CommandResult.DisplayedPage.PRODUCT, index);
    }
}
