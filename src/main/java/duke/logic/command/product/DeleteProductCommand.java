package duke.logic.command.product;

import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class DeleteProductCommand extends ProductCommand {
    public static final String COMMAND_WORD = "remove";
    private static final String MESSAGE_DELETE_SUCCESS = "Product(s) removed.";
    private static final String MESSAGE_INDEX_OUT_OF_BOUND = "Index [%d] is out of bound.";

    private final Set<Index> indices;

    /** Creates a DeleteProductCommand
     *
     * @param indices of the products to delete
     * */

    public DeleteProductCommand(Set<Index> indices) {
        requireNonNull(indices);
        this.indices = indices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Product> toDelete = new ArrayList<>();
        for (Index index : indices) {
            if (index.getZeroBased() >= model.getFilteredProductList().size()) {
                throw new CommandException(String.format(MESSAGE_INDEX_OUT_OF_BOUND, index.getOneBased()));
            }
            toDelete.add(model.getFilteredProductList().get(index.getZeroBased()));
        }
        for (Product product : toDelete) {
            model.deleteProduct(product);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS, indices.size()),
                CommandResult.DisplayedPage.PRODUCT);

    }
}
