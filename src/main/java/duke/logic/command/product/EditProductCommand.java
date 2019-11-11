package duke.logic.command.product;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.ProductMessageUtils;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;
import duke.model.exceptions.DuplicateEntityException;
import duke.model.product.Product;

import java.util.List;

import static duke.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A command to edit a product from Product List.
 */
public class EditProductCommand extends ProductCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_EDIT_PRODUCT_SUCCESS = "Edited Products %s";

    public final Index index;
    public final ProductDescriptor productDescriptor;

    public static final String AUTO_COMPLETE_INDICATOR = ProductCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        CliSyntax.PREFIX_PRODUCT_NAME,
        CliSyntax.PREFIX_PRODUCT_INGREDIENT,
        CliSyntax.PREFIX_PRODUCT_INGREDIENT_COST,
        CliSyntax.PREFIX_PRODUCT_RETAIL_PRICE,
        CliSyntax.PREFIX_PRODUCT_STATUS
    };

    /**
     * Creates an EditProductCommand to modify the details of a {@code Product}.
     *  @param index of the product in the filteredProductList in {@code ModelManager}
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
        Product editedProduct = ProductCommandUtil.getEditedProductFromDescriptor(toEdit, productDescriptor);
        ProductCommandUtil.verifyNewIngredients(model, editedProduct);
        try {
            model.setProduct(toEdit, editedProduct);
        } catch (DuplicateEntityException e) {
            throw new CommandException(String.format(ProductMessageUtils.MESSAGE_DUPLICATE_PRODUCT,
                editedProduct.getProductName()));
        }
        model.commit(ProductMessageUtils.MESSAGE_COMMIT_EDIT_PRODUCT);
        return new CommandResult(String.format(MESSAGE_EDIT_PRODUCT_SUCCESS, editedProduct.getProductName()),
            CommandResult.DisplayedPage.PRODUCT);
    }
}
