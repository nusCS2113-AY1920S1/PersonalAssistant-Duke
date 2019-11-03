package duke.model.product;

import duke.logic.message.ProductMessageUtils;
import duke.logic.parser.exceptions.ParseException;
import duke.testutil.ProductBuilder;
import org.junit.jupiter.api.Test;

import static duke.testutil.Assert.assertThrows;
import static duke.testutil.TypicalProducts.CHEESE_CAKE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    @Test
    public void createNewProduct_emptyName_throwsUnsupportedOperationException() {
        Product product = new ProductBuilder().withName("").build();
        assertThrows(ParseException.class, ProductMessageUtils.MESSAGE_MISSING_PRODUCT_NAME,
                () -> product.setProductName(""));
    }

    @Test
    public void equal() {
        // same object -> returns true
        assertTrue(CHEESE_CAKE.equals(CHEESE_CAKE));

        // null -> returns false
        assertFalse(CHEESE_CAKE.equals(null));

    }
}
