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

    public static final String MESSAGE_EDIT_PRODUCT_SUCCESS = "Edited Products";

    public final Index index;
    public final EditProductDescriptor editProductDescriptor;
    private Product toEdit;
    private static final Logger logger = LogsCenter.getLogger(EditProductCommandParser.class);


    /**
     * Creates an EditProductCommand to modify the details of an {@code Product}.
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
        Double newPrice = editProductDescriptor.getRetailPrice().orElse(toEdit.getPrice());
        Double newCost = editProductDescriptor.getIngredientCost().orElse(toEdit.getCost());
        Product.Status newStatus = editProductDescriptor.getStatus().orElse(toEdit.getStatus());
        return new Product(newName, newPrice, newCost, newStatus);
    }

    /** Stores the details to edit the product with. Each non-empty field value will replace the previous
     * field value of the product.
     */
    public static class EditProductDescriptor {
        private String productName;
        //private IngredientList ingredientList;
        private double ingredientCost;
        private double retailPrice;
        private Product.Status status;

        public EditProductDescriptor() {
        }

        private EditProductDescriptor(EditProductDescriptor toCopy) {

        }

        public void setProductName(String newProductName) {
            this.productName = newProductName;
        }

        public void setIngredientCost(Double newIngredientCost) {
            this.ingredientCost = newIngredientCost;
        }

        public void setRetailPrice(Double newRetailPrice) {
            this.retailPrice = newRetailPrice;
        }

        public void setStatus(Product.Status newStatus) {
            this.status = newStatus;
        }

        public Optional<String> getProductName() {
            return Optional.ofNullable(productName);
        }

        public Optional<Double> getIngredientCost() {
            return Optional.ofNullable(ingredientCost);
        }

        public Optional<Double> getRetailPrice() {
            return Optional.ofNullable(retailPrice);
        }

        public Optional<Product.Status> getStatus() {
            return Optional.ofNullable(status);
        }

    }
}
