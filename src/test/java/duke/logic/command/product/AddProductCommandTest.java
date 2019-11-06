package duke.logic.command.product;

import duke.model.Model;
import duke.model.ModelStub;
import duke.model.exceptions.DuplicateEntityException;
import duke.testutil.ProductDescriptorBuilder;
import org.junit.jupiter.api.Test;

import static duke.logic.parser.product.ProductParserUtil.createProductDescriptor;
import static duke.testutil.Assert.assertThrows;
import static duke.testutil.TypicalProducts.EGG_TART;

public class AddProductCommandTest {

    Model modelStub = new ModelStub();
    @Test
    public void constructor_nullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddProductCommand(null));
    }

    @Test
    public void addProduct_productAlreadyExist_throwsDuplicateEntityException() {
        //assertThrows(DuplicateEntityException.class,
        //        () -> new AddProductCommand(new ProductDescriptorBuilder(EGG_TART).build()).execute(modelStub));
    }
}
