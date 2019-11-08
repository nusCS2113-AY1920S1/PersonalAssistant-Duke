package duke.logic.command.product;

import duke.logic.command.CommandResult;
import duke.logic.message.ProductMessageUtils;
import duke.model.Model;
import duke.model.ModelManager;
import duke.model.product.Product;
import duke.testutil.CommandResultBuilder;
import duke.testutil.ProductBuilder;
import duke.testutil.ProductDescriptorBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static duke.testutil.BakingHomeBuilder.getTypicalBakingHome;
import static duke.testutil.CommandResultBuilder.PAGE_PRODUCT;
import static duke.testutil.CommandTestUtil.assertCommandFailure;
import static duke.testutil.CommandTestUtil.assertCommandSuccess;

/**
 * Contains integration tests (interaction with the Model) for {@code AddProductCommand}.
 */
public class AddProductCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBakingHome());
    }

    @Test
    public void execute_addProduct_success() {
        Product validProduct = new ProductBuilder().build();

        Model expectedModel = new ModelManager(model.getBakingHome());
        expectedModel.addProduct(validProduct);
        expectedModel.commit(ProductMessageUtils.MESSAGE_COMMIT_ADD_PRODUCT);
        CommandResult expectedCommandResult =
                new CommandResultBuilder()
                        .withMessage(String.format(ProductMessageUtils.MESSAGE_ADD_PRODUCT_SUCCESS,validProduct.getProductName()))
                        .withPage(PAGE_PRODUCT)
                        .build();

        assertCommandSuccess(new AddProductCommand(new ProductDescriptorBuilder(validProduct).build()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicateProduct_throwsCommandException() {
        Product productInList = model.getBakingHome().getProductList().get(0);
        assertCommandFailure(new AddProductCommand(new ProductDescriptorBuilder(productInList).build()),
                model, String.format(ProductMessageUtils.MESSAGE_DUPLICATE_PRODUCT,
                        productInList.getProductName()));
    }
}
