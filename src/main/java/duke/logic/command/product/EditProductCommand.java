package duke.logic.command.product;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.product.Product;

import java.util.List;

import static duke.commons.util.CollectionUtil.requireAllNonNull;

public class EditProductCommand extends ProductCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_EDIT_PRODUCT_SUCCESS = "Edited Products %s";

    public final Index index;
    public final ProductDescriptor productDescriptor;


    /**
     * Creates an EditProductCommand to modify the details of an {@code comProduct}.
     *  @param index                 of the product in the filtered product list
     * @param productDescriptor details to edit the product with
     */
    public EditProductCommand(Index index, ProductDescriptor productDescriptor) {
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
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<Product> lastShownList = model.getFilteredProductList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Message.MESSAGE_INVALID_INDEX);
        }

        Product toEdit = lastShownList.get(index.getZeroBased());

        Product editedProduct = ProductCommandUtil.createNewProduct(toEdit, productDescriptor);

        model.setProduct(toEdit, editedProduct);
        model.updateFilteredProductList(Model.PREDICATE_SHOW_ACTIVE_PRODUCTS);

        return new CommandResult(String.format(MESSAGE_EDIT_PRODUCT_SUCCESS, editedProduct.getProductName()),
                CommandResult.DisplayedPage.PRODUCT);
    }
}
