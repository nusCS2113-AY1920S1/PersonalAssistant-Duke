package duke.logic.command.product;

import duke.model.Model;
import duke.model.ModelManager;
import org.junit.jupiter.api.Test;

import static duke.testutil.Assert.assertThrows;

public class AddProductCommandTest {

    Model modelStub = new ModelManager();

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
