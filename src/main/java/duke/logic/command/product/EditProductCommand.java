package duke.logic.command.product;

import duke.commons.core.LogsCenter;
import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.product.EditProductCommandParser;
import duke.model.Model;
import duke.model.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static duke.commons.util.CollectionUtil.requireAllNonNull;

public class EditProductCommand extends ProductCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_EDIT_PRODUCT_SUCCESS = "Edited Products %s";

    public final Index index;
    public final EditProductDescriptor editProductDescriptor;
    private Product toEdit;
    private static final Logger logger = LogsCenter.getLogger(EditProductCommandParser.class);


    /**
     * Creates an EditProductCommand to modify the details of an {@code comProduct}.
     *
     * @param index                 of the product in the filtered product list
     * @param editProductDescriptor details to edit the product with
     */
    public EditProductCommand(Index index, EditProductDescriptor editProductDescriptor) {
        requireAllNonNull(index, editProductDescriptor);

        this.index = index;
        this.editProductDescriptor = new EditProductDescriptor(editProductDescriptor);
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
            logger.warning("edit product index out of bound");
            throw new CommandException(Message.MESSAGE_INVALID_INDEX);
        }

        toEdit = lastShownList.get(index.getZeroBased());

        Product editedProduct = createEditedProduct(toEdit, editProductDescriptor);

        model.setProduct(toEdit, editedProduct);
        model.updateFilteredProductList(Model.PREDICATE_SHOW_ALL_PRODUCTS);

        return new CommandResult(String.format(MESSAGE_EDIT_PRODUCT_SUCCESS, editedProduct.getName()),
                CommandResult.DisplayedPage.PRODUCT);
    }

    private static Product createEditedProduct(Product toEdit, EditProductDescriptor editProductDescriptor) {
        assert toEdit != null;

        String newName = editProductDescriptor.getProductName().orElse(toEdit.getName());
        String newPrice = editProductDescriptor.getRetailPrice().orElse(String.valueOf(toEdit.getPrice()));

        String newCost = editProductDescriptor.getIngredientCost().orElse(String.valueOf(toEdit.getCost()));
        Product.Status newStatus = editProductDescriptor.getStatus().orElse(toEdit.getStatus());
        return new Product(newName, newPrice, newCost, newStatus);
    }

    /** Stores the details to edit the product with. Each non-empty field value will replace the previous
     * field value of the product.
     */
    public static class EditProductDescriptor {
        private String productName;
        //private IngredientList ingredientList;
        private String ingredientCost;
        private String retailPrice;
        private Product.Status status;

        public EditProductDescriptor() {
        }

        private EditProductDescriptor(EditProductDescriptor toCopy) {
            setProductName(toCopy.productName);
            setIngredientCost(toCopy.ingredientCost);
            setRetailPrice(toCopy.retailPrice);
            System.out.println(toCopy.retailPrice);
            setStatus(toCopy.status);
        }

        public void setProductName(String newProductName) {
            this.productName = newProductName;
        }

        public void setIngredientCost(String newIngredientCost) {
            this.ingredientCost = newIngredientCost;
        }

        public void setRetailPrice(String newRetailPrice) {
            this.retailPrice = newRetailPrice;
        }

        public void setStatus(Product.Status newStatus) {
            this.status = newStatus;
        }

        public Optional<String> getProductName() {
            return Optional.ofNullable(productName);
        }

        public Optional<String> getIngredientCost() {
            return Optional.ofNullable(ingredientCost);
        }

        public Optional<String> getRetailPrice() {
            return Optional.ofNullable(retailPrice);
        }

        public Optional<Product.Status> getStatus() {
            return Optional.ofNullable(status);
        }

    }
}
